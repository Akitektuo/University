﻿using CarRentals.CarChanges;
using CarRentals.CarUpdates;
using CarRentals.Extensions;
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
        private readonly IBroadcastHandler broadcastHandler;

        public CarsController(ICarService carService, IBroadcastHandler broadcastHandler)
        {
            this.carService = carService;
            this.broadcastHandler = broadcastHandler;
        }

        [HttpPost]
        public IActionResult CreateCar(Car car)
        {
            var createdCar = carService.Create(car.AttachUserId(this));

            if (createdCar == null) return BadRequest();

            broadcastHandler.Broadcast(ChangeType.Create, createdCar);

            return Ok(createdCar);
        }

        [HttpPut]
        public IActionResult UpdateCar(Car car)
        {
            var updatedCar = carService.Update(car.AttachUserId(this));

            if (updatedCar == null) return NotFound();

            broadcastHandler.Broadcast(ChangeType.Update, updatedCar);

            return Ok(updatedCar);
        }

        [HttpGet]
        public IActionResult GetAllCars()
        {
            return Ok(carService.GetAll());
        }

        [HttpGet("available")]
        public IActionResult GetAvailableCars()
        {
            return Ok(carService.GetAvailable());
        }

        [HttpGet("related")]
        public IActionResult GetRelatedCars()
        {
            return Ok(carService.GetRelated());
        }

        [HttpDelete("{id}")]
        public IActionResult DeleteCar(int id)
        {
            var deletedCar = carService.Delete(id);

            if (deletedCar == null) return NotFound();

            broadcastHandler.Broadcast(ChangeType.Delete, deletedCar);

            return Ok(deletedCar);
        }

        [HttpGet("changes")]
        public async Task GetUpdatesAsync()
        {
            if (!HttpContext.WebSockets.IsWebSocketRequest)
            {
                HttpContext.Response.StatusCode = BAD_REQUEST_STATUS_CODE;
                return;
            }

            using var webSocket = await HttpContext.WebSockets.AcceptWebSocketAsync("access_token");

            await broadcastHandler.AddConnection(webSocket);
        }
    }
}
