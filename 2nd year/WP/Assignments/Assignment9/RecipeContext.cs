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

        //protected override void OnModelCreating(ModelBuilder modelBuilder)
        //{
        //    modelBuilder.Entity<Recipe>()
        //        .HasOne(recipe => recipe.Type)
        //        .WithMany(type => type.Recipes)
        //        .HasForeignKey(recipe => recipe.TypeId);
        //}
    }
}
