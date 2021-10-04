using Microsoft.AspNetCore.Mvc;
using Posts.Models;
using Posts.Services;
using System.Collections.Generic;

namespace Posts.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GroupsController : ControllerBase
    {
        private IGroupService groupService;

        public GroupsController(IGroupService groupService)
        {
            this.groupService = groupService;
        }

        [HttpGet]
        public IActionResult GetAllGroups()
        {
            return Ok(groupService.GetAll());
        }
    }
}
