using Assignment9.Services;
using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;

namespace Assignment9.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class TypesController : ControllerBase
    {
        private ITypeService typeService;

        public TypesController(ITypeService typeService)
        {
            this.typeService = typeService;
        }

        [HttpGet]
        public IActionResult GetAllTypes()
        {
            return Ok(typeService.GetAll());
        }
    }
}
