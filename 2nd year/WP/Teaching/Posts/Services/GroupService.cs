using Posts.Models;
using System.Collections.Generic;
using System.Linq;

namespace Posts.Services
{
    public class GroupService : IGroupService
    {
        private PostsContext context;

        public GroupService(PostsContext context)
        {
            this.context = context;
        }

        public List<Group> GetAll()
        {
            return context.Groups.ToList();
        }
    }
}
