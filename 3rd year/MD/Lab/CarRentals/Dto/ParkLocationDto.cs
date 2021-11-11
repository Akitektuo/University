using CarRentals.Models;

namespace CarRentals.Dto
{
    public class ParkLocationDto
    {
        public decimal Latitude { get; set; }

        public decimal Longitude { get; set; }

        public ParkLocationDto() { }

        public ParkLocationDto(decimal latitude, decimal longitude)
        {
            Latitude = latitude;
            Longitude = longitude;
        }

        public ParkLocationDto(ParkLocation parkLocation)
        {
            Latitude = parkLocation.Latitude;
            Longitude = parkLocation.Longitude;
        }
    }
}
