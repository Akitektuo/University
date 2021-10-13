using CarRentals.CarChanges;
using System.Collections.Generic;
using System.Linq;
using System.Net.WebSockets;
using System.Threading.Tasks;

namespace CarRentals.CarUpdates
{
    public class BroadcastHandler : IBroadcastHandler
    {
        private const int CONNECTION_TIMEOUT_MILLISECONDS = 5000;

        private readonly List<SocketConnectionHandler> socketConnections = new();

        public Task AddConnection(WebSocket webSocket)
        {
            var handler = new SocketConnectionHandler(webSocket);

            socketConnections.Add(handler);

            return handler.WaitUntilClosed();
        }

        public Task Broadcast<T>(ChangeType type, T payload)
        {
            var change = new Change<T>
            {
                Type = type,
                Payload = payload
            };

            return Task.WhenAll(socketConnections.ToList().Select(socket =>
                HandleBroadcastForSocket(change, socket)));
        }

        private async Task HandleBroadcastForSocket<T>(T obj, SocketConnectionHandler socket)
        {
            await socket.Send(obj);

            if (!await socket.CheckClientConnection(CONNECTION_TIMEOUT_MILLISECONDS))
                socketConnections.Remove(socket);
        }
    }
}
