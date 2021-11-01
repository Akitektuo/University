using Lab4.Sockets;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Lab4.Parser
{
    class TaskParser : BaseParser
    {
        protected override string PARSER_TYPE => "Task";

        public TaskParser(List<string> urls) : base(urls)
        {
        }

        protected override void Run()
        {
            var tasks = Map((index, url) => Task.Run(() =>
                Start(SocketWrapper.Create(url, index))));

            Task.WhenAll(tasks).Wait();
        }

        private Task Start(SocketWrapper socket)
        {
            socket.BeginConnectAsync().Wait();
            LogConnected(socket);

            var numberOfSentBytes = socket.BeginSendAsync().Result;
            LogSent(socket, numberOfSentBytes);

            socket.BeginReceiveAsync().Wait();
            LogReceived(socket);

            socket.ShutdownAndClose();
            return Task.CompletedTask;
        }
    }
}
