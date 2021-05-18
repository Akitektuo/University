using Assignment9.Models;

namespace Assignment9.Aggregates
{
    public class RecipeAggregate
    {
        public RecipeAggregate(Recipe recipe)
        {
            Id = recipe.Id;
            UserId = recipe.UserId;
            User = recipe.User.Username;
            TypeId = recipe.TypeId;
            Type = recipe.Type.Name;
            Title = recipe.Title;
            Description = recipe.Description;
        }

        public int Id { get; set; }

        public int UserId { get; set; }

        public string User { get; set; }

        public int TypeId { get; set; }

        public string Type { get; set; }

        public string Title { get; set; }

        public string Description { get; set; }
    }
}
