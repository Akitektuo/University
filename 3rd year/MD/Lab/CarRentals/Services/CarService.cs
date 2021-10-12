using CarRentals.Models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace CarRentals.Services
{
    public class CarService : ICarService
    {
        private CarRentalsContext context;

        public CarService(CarRentalsContext context)
        {
            this.context = context;
        }

        public Car Create(Car car)
        {
            try
            {
                context.Cars.Add(car);
                context.SaveChanges();
            }
            catch (Exception)
            {
                return null;
            }

            return car;
        }

        public List<Car> GetAll()
        {
            return context.Cars.ToList();
        }

        public Car Update(Car car)
        {
            if (!context.Cars.Any(car => car.Id == car.Id))
                return null;

            context.Cars.Update(car);
            context.SaveChanges();

            return car;
        }

        public Car Delete(int id)
        {
            var car = context.Cars.Find(id);

            if (car != null)
            {
                context.Remove(car);
                context.SaveChanges();
            }

            return car;
        }
    }
}
