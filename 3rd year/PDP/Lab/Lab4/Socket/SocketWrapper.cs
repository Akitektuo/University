using System;
using System.Text;
using System.Net.Sockets;
using System.Net;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Lab4.Sockets
{
    class SocketWrapper : Socket
    {
        public int Id { get; }

        public string BaseUrl { get; }

        public string UrlPath { get; }

        public IPEndPoint EndPoint { get; }

        public StringBuilder ResponseContent { get; } = new StringBuilder();

        private const int DEFAULT_HTTP_PORT = 80;
        private const int BUFFER_SIZE = 1024;
        private readonly Regex CONTENT_LENGTH_REGEX = new Regex(@"Content-Length: \d+");

        public static SocketWrapper Create(string url, int id)
        {
            var pathDelimiterIndex = url.IndexOf('/');
            var baseUrl = pathDelimiterIndex < 0 ? url : url.Substring(0, pathDelimiterIndex);
            var urlPath = pathDelimiterIndex < 0 ? "/" : url.Substring(pathDelimiterIndex);

            var ipHostInformation = Dns.GetHostEntry(baseUrl);
            var ipAddress = ipHostInformation.AddressList[0];

            return new SocketWrapper(baseUrl, urlPath, ipAddress, id);
        }

        private SocketWrapper(string baseUrl, string urlPath, IPAddress ipAddress, int id):
            base(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp)
        {
            Id = id;
            BaseUrl = baseUrl;
            UrlPath = urlPath;
            EndPoint = new IPEndPoint(ipAddress, DEFAULT_HTTP_PORT);
        }

        public void BeginConnect(Action<SocketWrapper> onConnected)
        {
            BeginConnect(EndPoint, asyncResult => {
                EndConnect(asyncResult);
                onConnected(this);
            }, null);
        }

        public void BeginSend(Action<SocketWrapper, int> onSent)
        {
            var stringToSend = $"GET {UrlPath} HTTP/1.1\r\n" +
                $"Host: {BaseUrl}\r\n" +
                "Content-Length: 0\r\n\r\n";
            var encodedString = Encoding.ASCII.GetBytes(stringToSend);

            BeginSend(encodedString, 0, encodedString.Length, SocketFlags.None, asyncResult => {
                var numberOfSentBytes = EndSend(asyncResult);
                onSent(this, numberOfSentBytes);
            }, null);
        }

        public void BeginReceive(Action<SocketWrapper> onReceived)
        {
            var buffer = new byte[BUFFER_SIZE];
            ResponseContent.Clear();

            BeginReceive(buffer, 0, BUFFER_SIZE, SocketFlags.None, asyncResult =>
                HandleReceiveResult(asyncResult, buffer, onReceived), null);
        }

        public Task BeginConnectAsync() => Task.Run(() =>
        {
            var taskCompletion = new TaskCompletionSource();

            BeginConnect(_ => taskCompletion.TrySetResult());

            return taskCompletion.Task;
        });

        public Task<int> BeginSendAsync() => Task.Run(() =>
        {
            var taskCompletion = new TaskCompletionSource<int>();

            BeginSend((_, numberOfSentBytes) =>
                taskCompletion.TrySetResult(numberOfSentBytes));

            return taskCompletion.Task;
        });

        public Task BeginReceiveAsync() => Task.Run(() =>
        {
            var taskCompletion = new TaskCompletionSource();

            BeginReceive(_ => taskCompletion.TrySetResult());

            return taskCompletion.Task;
        });

        public void ShutdownAndClose()
        {
            Shutdown(SocketShutdown.Both);
            Close();
        }

        public int GetResponseContentLength => int.Parse(CONTENT_LENGTH_REGEX
            .Match(ResponseContent.ToString())
            .Value
            .Substring(16));

        private void HandleReceiveResult(
            IAsyncResult asyncResult,
            byte[] buffer,
            Action<SocketWrapper> onReceived)
        {
            var numberOfReadBytes = EndReceive(asyncResult);
            ResponseContent.Append(Encoding.ASCII.GetString(buffer, 0, numberOfReadBytes));

            if (!IsContentFullyReceived)
            {
                BeginReceive(buffer, 0, BUFFER_SIZE, SocketFlags.None, asyncResult =>
                    HandleReceiveResult(asyncResult, buffer, onReceived), null);
                return;
            }

            onReceived(this);
        }

        private bool IsContentFullyReceived => ResponseContent.ToString().Contains("\r\n\r\n");
    }
}
