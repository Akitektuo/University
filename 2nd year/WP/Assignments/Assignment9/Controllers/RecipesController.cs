using Assignment9.Models;
using Assignment9.Services;
using Microsoft.AspNetCore.Mvc;

namespace Assignment9.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RecipesController : ControllerBase
    {
        private IRecipeService recipeService;

        public RecipesController(IRecipeService recipeService)
        {
            this.recipeService = recipeService;
        }

        [HttpPost]
        public IActionResult CreateRecipe(Recipe recipe)
        {
            var createdRecipe = recipeService.Create(recipe);

            if (createdRecipe == null) return BadRequest();

            return Ok(createdRecipe);
        }

        [HttpPut]
        public IActionResult UpdateRecipe(Recipe recipe)
        {
            var updatedRecipe = recipeService.Update(recipe);

            if (updatedRecipe == null) return NotFound();

            return Ok(updatedRecipe);
        }

        [HttpDelete]
        [Route("{id}")]
        public IActionResult DeleteRecipe(int id)
        {
            var deletedRecipe = recipeService.Delete(id);

            if (deletedRecipe == null) return NotFound();

            return Ok(deletedRecipe);
        }

        [HttpGet]
        [Route("{id}")]
        public IActionResult GetRecipe(int id)
        {
            var recipe = recipeService.Get(id);

            if (recipe == null) return NotFound();

            return Ok(recipe);
        }

        [HttpGet]
        public IActionResult GetRecipesByType(int typeId)
        {
            return Ok(recipeService.GetAllByType(typeId));
        }
    }
}
