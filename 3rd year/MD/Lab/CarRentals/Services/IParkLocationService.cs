using CarRentals.Dto;

namespace CarRentals.Services
{
    public interface IParkLocationService
    {
        ParkLocationDto GetParkLocation();

        void SetParkLocation(ParkLocationDto parkLocationDto);
    }
}
