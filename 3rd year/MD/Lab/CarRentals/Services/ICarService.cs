using CarRentals.Models;
using System.Collections.Generic;

namespace CarRentals.Services
{
    public interface ICarService
    {
        List<Car> GetAll();

        Car Create(Car car);

        Car Update(Car car);

        Car Delete(int id);
    }
}
