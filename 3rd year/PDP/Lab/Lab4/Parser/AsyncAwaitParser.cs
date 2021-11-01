using Lab4.Sockets;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Lab4.Parser
{
    class AsyncAwaitParser : BaseParser
    {
        protected override string PARSER_TYPE => "Async/Await";

        public AsyncAwaitParser(List<string> urls) : base(urls)
        {
        }

        protected override void Run()
        {
            var tasks = Map((index, url) => Task.Run(() =>
                Start(SocketWrapper.Create(url, index))));

            Task.WhenAll(tasks).Wait();
        }

        private async Task Start(SocketWrapper socket)
        {
            await socket.BeginConnectAsync();
            LogConnected(socket);

            var numberOfSentBytes = await socket.BeginSendAsync();
            LogSent(socket, numberOfSentBytes);

            await socket.BeginReceiveAsync();
            LogReceived(socket);

            socket.ShutdownAndClose();
        }
    }
}
