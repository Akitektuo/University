﻿using CarRentals.CarChanges;
using CarRentals.CarUpdates;
using CarRentals.Models;
using CarRentals.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
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
            var createdCar = carService.Create(car);

            if (createdCar == null) return BadRequest();

            broadcastHandler.Broadcast(ChangeType.Create, createdCar);

            return Ok(createdCar);
        }

        [HttpPut]
        public IActionResult UpdateCar(Car car)
        {
            var updatedCar = carService.Update(car);

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
        public IActionResult GetRelatedCars(
            string searchKeyword,
            bool? isAutomatic,
            int from,
            int count)
        {
            return Ok(carService.GetRelated(searchKeyword, isAutomatic, from, count));
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

        [HttpPost("sync")]
        public IActionResult SyncCars(List<Change<Car>> changedCars)
        {
            return Ok(carService.MapChanges(changedCars));
        }
    }
}
