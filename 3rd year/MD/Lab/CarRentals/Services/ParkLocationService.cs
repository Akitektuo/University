using CarRentals.Dto;
using CarRentals.Extensions;
using CarRentals.Models;
using Microsoft.AspNetCore.Http;

namespace CarRentals.Services
{
    public class ParkLocationService : IParkLocationService
    {
        private readonly CarRentalsContext context;
        private readonly IHttpContextAccessor httpContextAccessor;

        public ParkLocationService(
            CarRentalsContext context,
            IHttpContextAccessor httpContextAccessor)
        {
            this.context = context;
            this.httpContextAccessor = httpContextAccessor;
        }

        public ParkLocationDto GetParkLocation()
        {
            var parkLocation = context.ParkLocations.Find(httpContextAccessor.GetUserId());

            return new ParkLocationDto(parkLocation);
        }

        public void SetParkLocation(ParkLocationDto parkLocationDto)
        {
            var parkLocation = new ParkLocation
            {
                Latitude = parkLocationDto.Latitude,
                Longitude = parkLocationDto.Longitude,
                UserId = httpContextAccessor.GetUserId()
            };

            context.Update(parkLocation);
        }
    }
}
