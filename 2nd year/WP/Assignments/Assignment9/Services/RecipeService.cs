using Assignment9.Aggregates;
using Assignment9.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Assignment9.Services
{
    public class RecipeService : IRecipeService
    {
        private RecipeContext context;

        public RecipeService(RecipeContext context)
        {
            this.context = context;
        }

        public Recipe Create(Recipe recipe)
        {
            try
            {
                context.Recipes.Add(recipe);
                context.SaveChanges();
            }
            catch (Exception)
            {
                return null;
            }

            return recipe;
        }

        public Recipe Delete(int id)
        {
            var recipe = context.Recipes.Find(id);

            if (recipe == null) return null;

            context.Recipes.Remove(recipe);
            context.SaveChanges();

            return recipe;
        }

        public RecipeAggregate Get(int id)
        {
            var recipe = context.Recipes.Include(recipe => recipe.Type)
                .Include(recipe => recipe.User)
                .FirstOrDefault(recipe => recipe.Id == id);

            return new RecipeAggregate(recipe);
        }

        public List<RecipeAggregate> GetAllByType(int typeId)
        {
            return context.Recipes.Include(recipe => recipe.Type)
                .Include(recipe => recipe.User)
                .Where(recipe => recipe.TypeId == typeId)
                .Select(recipe => new RecipeAggregate(recipe))
                .ToList();
        }

        public Recipe Update(Recipe recipe)
        {
            var existingRecipe = context.Recipes.Find(recipe.Id);

            if (existingRecipe == null) return null;

            context.Recipes.Update(recipe);
            context.SaveChanges();

            return recipe;
        }
    }
}
