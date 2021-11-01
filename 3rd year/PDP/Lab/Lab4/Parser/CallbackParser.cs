using Lab4.Sockets;
using System.Collections.Generic;
using System.Threading;

namespace Lab4.Parser
{
    class CallbackParser : BaseParser
    {
        protected override string PARSER_TYPE => "Callback";

        public CallbackParser(List<string> urls) : base(urls)
        {
        }

        protected override void Run()
        {
            ForEach((index, url) => Start(SocketWrapper.Create(url, index)));
        }

        private void Start(SocketWrapper socket)
        {
            socket.BeginConnect(HandleConnected);
            while (socket.Connected)
                Thread.Sleep(100);
        }

        private void HandleConnected(SocketWrapper socket)
        {
            LogConnected(socket);
            socket.BeginSend(HandleSent);
        }

        private void HandleSent(SocketWrapper socket, int numberOfSentBytes)
        {
            LogSent(socket, numberOfSentBytes);
            socket.BeginReceive(HandleReceived);
        }

        private void HandleReceived(SocketWrapper socket)
        {
            LogReceived(socket);
            socket.ShutdownAndClose();
        }
    }
}
