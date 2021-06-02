using Assignment9.Models;
using Microsoft.EntityFrameworkCore;

namespace Assignment9
{
    public class RecipeContext : DbContext
    {
        public RecipeContext(DbContextOptions<RecipeContext> options): base(options)
        {
        }

        public DbSet<User> Users { get; set; }

        public DbSet<Type> Types { get; set; }

        public DbSet<Recipe> Recipes { get; set; }
    }
}
