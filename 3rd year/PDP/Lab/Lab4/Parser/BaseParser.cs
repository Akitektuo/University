using Lab4.Sockets;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Lab4.Parser
{
    abstract class BaseParser
    {
        protected List<string> Urls { get; set; }

        protected abstract string PARSER_TYPE { get; }

        public BaseParser(List<string> urls)
        {
            Urls = urls;
            Run();
        }

        protected void ForEach(Action<int, string> action)
        {
            var count = 0;
            Urls.ForEach(url => action(count++, url));
        }

        protected List<T> Map<T>(Func<int, string, T> mapper)
        {
            var count = 0;
            return Urls.Select(url => mapper(count++, url)).ToList();
        }

        protected void LogConnected(SocketWrapper socket)
        {
            Console.WriteLine($"{PARSER_TYPE}-{socket.Id}: Socket connected to {socket.BaseUrl} ({socket.UrlPath})");
        }

        protected void LogSent(SocketWrapper socket, int numberOfSentBytes)
        {
            Console.WriteLine($"{PARSER_TYPE}-{socket.Id}: Sent {numberOfSentBytes} bytes to server.");
        }

        protected void LogReceived(SocketWrapper socket)
        {
            Console.WriteLine($"{PARSER_TYPE}-{socket.Id}: Received content length is: {socket.GetResponseContentLength}");
        }

        protected abstract void Run();
    }
}
