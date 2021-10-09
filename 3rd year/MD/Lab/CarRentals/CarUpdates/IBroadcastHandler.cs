using System.Net.WebSockets;
using System.Threading.Tasks;

namespace CarRentals.CarUpdates
{
    public interface IBroadcastHandler<T>
    {
        Task AddConnection(WebSocket webSocket);

        Task Broadcast(T obj);
    }
}
