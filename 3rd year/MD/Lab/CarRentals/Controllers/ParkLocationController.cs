using CarRentals.Dto;
using CarRentals.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace CarRentals.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class ParkLocationController : ControllerBase
    {
        private readonly IParkLocationService parkLocationService;

        public ParkLocationController(IParkLocationService parkLocationService)
        {
            this.parkLocationService = parkLocationService;
        }

        [HttpGet]
        public IActionResult GetParkLocation()
        {
            var parkLocation = parkLocationService.GetParkLocation();

            if (parkLocation == null)
                return NotFound();

            return Ok(parkLocation);
        }

        [HttpPost]
        public IActionResult SetParkLocation(ParkLocationDto parkLocation)
        {
            parkLocationService.SetParkLocation(parkLocation);

            return Ok();
        }
    }
}
