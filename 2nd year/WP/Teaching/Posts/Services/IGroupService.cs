using Posts.Models;
using System.Collections.Generic;

namespace Posts.Services
{
    public interface IGroupService
    {
        List<Group> GetAll();
    }
}
