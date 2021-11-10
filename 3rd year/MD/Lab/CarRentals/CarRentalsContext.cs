using CarRentals.Authentication;
using CarRentals.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace CarRentals
{
    public class CarRentalsContext : IdentityDbContext<ApplicationUser>
    {
        public CarRentalsContext(DbContextOptions<CarRentalsContext> options) : base(options)
        {
        }

        public DbSet<Car> Cars { get; set; }

        public DbSet<ParkLocation> ParkLocations { get; set; }
    }
}
