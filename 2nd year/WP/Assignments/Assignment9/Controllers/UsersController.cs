using Assignment9.Models;
using Assignment9.Services;
using Microsoft.AspNetCore.Mvc;

namespace Assignment9.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class UsersController : ControllerBase
    {
        private IUserService userService;

        public UsersController(IUserService userService)
        {
            this.userService = userService;
        }

        [HttpPost]
        [Route("register")]
        public IActionResult RegisterUser(User user)
        {
            if (userService.Register(user)) return Ok();
            return Conflict();
        }

        [HttpPost]
        [Route("login")]
        public IActionResult LoginUser(User user)
        {
            var userId = userService.Login(user);

            if (userId == null) return NotFound();
            return Ok(userId);
        }
    }
}
