using CarRentals.CarUpdates;
using CarRentals.Models;
using CarRentals.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;

namespace CarRentals.Controllers
{
    [Authorize]
    [ApiController]
    [Route("api/[controller]")]
    public class CarsController : ControllerBase
    {
        private const int BAD_REQUEST_STATUS_CODE = 400;

        private readonly ICarService carService;
        private readonly IBroadcastHandler<Car> broadcastHandler;

        public CarsController(ICarService carService, IBroadcastHandler<Car> broadcastHandler)
        {
            this.carService = carService;
            this.broadcastHandler = broadcastHandler;
        }

        [HttpPost]
        public IActionResult CreateCar(Car car)
        {
            var createdCar = carService.Create(car);

            if (createdCar == null) return BadRequest();

            broadcastHandler.Broadcast(createdCar);

            return Ok(createdCar);
        }

        [HttpPut]
        public IActionResult UpdateCar(Car car)
        {
            var updatedCar = carService.Update(car);

            if (updatedCar == null) return NotFound();

            return Ok(updatedCar);
        }

        [HttpGet]
        public IActionResult GetAllCars()
        {
            return Ok(carService.GetAll());
        }

        [HttpGet("updates")]
        public async Task GetUpdatesAsync()
        {
            if (!HttpContext.WebSockets.IsWebSocketRequest)
            {
                HttpContext.Response.StatusCode = BAD_REQUEST_STATUS_CODE;
                return;
            }

            using var webSocket = await HttpContext.WebSockets.AcceptWebSocketAsync();

            await broadcastHandler.AddConnection(webSocket);
        }
    }
}
