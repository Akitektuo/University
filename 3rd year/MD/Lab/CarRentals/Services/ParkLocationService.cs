using CarRentals.Dto;
using CarRentals.Extensions;
using CarRentals.Models;
using Microsoft.AspNetCore.Http;
using System.Linq;

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
            var userId = httpContextAccessor.GetUserId();
            var parkLocation = context.ParkLocations
                .FirstOrDefault(location => location.UserId == userId);

            if (parkLocation == null)
                return null;

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

            var exists = context.ParkLocations
                .Any(location => location.UserId == parkLocation.UserId);

            if (exists)
                context.Update(parkLocation);
            else
                context.Add(parkLocation);
            context.SaveChanges();
        }
    }
}
