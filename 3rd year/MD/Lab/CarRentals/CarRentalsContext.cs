using CarRentals.Authentication;
using CarRentals.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using System.Data.Entity.SqlServer;

namespace CarRentals
{
    public class CarRentalsContext : IdentityDbContext<ApplicationUser>
    {
        public CarRentalsContext(DbContextOptions<CarRentalsContext> options) : base(options)
        {
            SqlProviderServices.TruncateDecimalsToScale = false;
        }

        public DbSet<Car> Cars { get; set; }

        public DbSet<ParkLocation> ParkLocations { get; set; }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            builder.Entity<ParkLocation>()
                .Property(location => location.Latitude)
                .HasPrecision(28, 15);
            builder.Entity<ParkLocation>()
                .Property(location => location.Longitude)
                .HasPrecision(28, 15);

            base.OnModelCreating(builder);
        }
    }
}
