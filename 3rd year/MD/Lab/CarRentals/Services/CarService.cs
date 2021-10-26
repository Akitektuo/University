using CarRentals.CarChanges;
using CarRentals.Extensions;
using CarRentals.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

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
            car.UserId = httpContextAccessor.GetUserId();
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

        public List<Car> GetRelated(string searchKeyword, bool? isAutomatic, int from, int count)
        {
            var userId = httpContextAccessor.GetUserId();

            var query = context.Cars.Where(car => car.UserId == userId);

            if (!string.IsNullOrWhiteSpace(searchKeyword))
                query = query.Where(car =>
                    car.Brand.ToLower().Contains(searchKeyword.ToLower()) ||
                    car.Model.ToLower().Contains(searchKeyword.ToLower()));

            if (isAutomatic != null)
                query = query.Where(car => car.IsAutomatic == isAutomatic);

            if (count < 1)
                return query.ToList();

            return query.Skip(from)
                .Take(count)
                .ToList();
        }

        public Car Update(Car carUpdate)
        {
            var initialCar = context.Cars
                .Where(car => car.Id == carUpdate.Id)
                .AsNoTracking()
                .FirstOrDefault();

            if (carUpdate.UserId == Guid.Empty)
                carUpdate.UserId = httpContextAccessor.GetUserId();

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

        public List<IdMap> MapChanges(List<Change<Car>> carChanges)
        {
            var carIdMapping = new List<IdMap>();

            foreach (var carChange in carChanges)
            {
                var idMap = MapChange(carChange, carIdMapping);
                if (idMap != null)
                    carIdMapping.Add(idMap);
            }

            return carIdMapping;
        }

        private IdMap MapChange(Change<Car> carChange, List<IdMap> idMapping)
        {
            var car = carChange.Payload;

            return carChange.Type switch
            {
                ChangeType.Create => MapCreateChange(car),
                ChangeType.Update => MapUpdateChange(car, idMapping),
                ChangeType.Delete => MapDeleteChange(car),
                _ => null
            };
        }

        private IdMap MapCreateChange(Car car)
        {
            if (car.Id > 0)
                return null;

            var initialId = car.Id;
            car.Id = 0;
            var addedCar = Create(car);

            return new() {
                From = initialId,
                To = addedCar.Id
            };
        }

        private IdMap MapUpdateChange(Car car, List<IdMap> idMapping)
        {
            if (car.Id > 0)
            {
                Update(car);
                return null;
            }

            var actualId = idMapping.FirstOrDefault(mapping => mapping.From == car.Id);
            if (actualId == null)
                return null;

            car.Id = actualId.To;
            Update(car);
            return null;
        }

        private IdMap MapDeleteChange(Car car)
        {
            if (car.Id > 0)
                Delete(car.Id);

            return null;
        }
    }
}
