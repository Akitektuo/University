using Assignment9.Aggregates;
using Assignment9.Models;
using System.Collections.Generic;

namespace Assignment9.Services
{
    public interface IRecipeService
    {
        List<RecipeAggregate> GetAllByType(int typeId);

        RecipeAggregate Get(int id);

        Recipe Create(Recipe recipe);

        Recipe Update(Recipe recipe);

        Recipe Delete(int id);
    }
}
