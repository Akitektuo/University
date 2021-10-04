using Microsoft.EntityFrameworkCore;
using Posts.Models;

namespace Posts
{
    public class PostsContext : DbContext
    {
        public PostsContext(DbContextOptions<PostsContext> options) : base(options)
        {
        }

        public DbSet<Group> Groups { get; set; }
    }
}
