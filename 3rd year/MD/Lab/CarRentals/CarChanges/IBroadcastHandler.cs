using CarRentals.CarChanges;
using System.Net.WebSockets;
using System.Threading.Tasks;

namespace CarRentals.CarUpdates
{
    public interface IBroadcastHandler
    {
        Task AddConnection(WebSocket webSocket);

        Task Broadcast<T>(ChangeType type, T payload);
    }
}
