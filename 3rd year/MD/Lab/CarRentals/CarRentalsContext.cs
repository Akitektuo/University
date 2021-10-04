using CarRentals.Models;
using Microsoft.EntityFrameworkCore;

namespace CarRentals
{
    public class CarRentalsContext : DbContext
    {
        public CarRentalsContext(DbContextOptions<CarRentalsContext> options) : base(options)
        {
        }

        public DbSet<Car> Cars { get; set; }
    }
}
