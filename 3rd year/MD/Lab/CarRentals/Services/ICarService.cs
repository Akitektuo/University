using CarRentals.CarChanges;
using CarRentals.Models;
using System.Collections.Generic;

namespace CarRentals.Services
{
    public interface ICarService
    {
        List<Car> GetAll();

        List<Car> GetAvailable();

        List<Car> GetRelated(string searchKeyword, bool? isAutomatic, int from, int count);

        Car Create(Car car);

        Car Update(Car car);

        Car Delete(int id);

        List<IdMap> MapChanges(List<Change<Car>> carChanges);
    }
}
