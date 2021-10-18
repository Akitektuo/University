using CarRentals.Extensions;
using CarRentals.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;

namespace CarRentals.Services
{
    public class CarService : ICarService
    {
        private readonly CarRentalsContext context;
        private readonly IHttpContextAccessor httpContextAccessor;

        public CarService(CarRentalsContext context, IHttpContextAccessor httpContextAccessor)
        {
            this.context = context;
            this.httpContextAccessor = httpContextAccessor;
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

        public List<Car> GetAvailable()
        {
            var userId = httpContextAccessor.GetUserId();

            return context.Cars.Where(car => car.UserId != userId).ToList();
        }

        public List<Car> GetRelated()
        {
            var userId = httpContextAccessor.GetUserId();

            return context.Cars.Where(car => car.UserId == userId).ToList();
        }

        public Car Update(Car carUpdate)
        {
            var initialCar = context.Cars
                .Where(car => car.Id == carUpdate.Id)
                .AsNoTracking()
                .FirstOrDefault();

            if (initialCar == null || initialCar.UserId != carUpdate.UserId)
                return null;

            context.Cars.Update(carUpdate);
            context.SaveChanges();

            return carUpdate;
        }

        public Car Delete(int id)
        {
            var car = context.Cars.Find(id);
            var userId = httpContextAccessor.GetUserId();

            if (car == null || car.UserId != userId)
                return null;
            
            context.Remove(car);
            context.SaveChanges();
            
            return car;
        }
    }
}
