using Assignment9.Models;
using System;
using System.Linq;

namespace Assignment9.Services
{
    public class UserService : IUserService
    {
        private RecipeContext context;

        public UserService(RecipeContext context)
        {
            this.context = context;
        }

        public int? Login(User user)
        {
            return context.Users
                .FirstOrDefault(u => u.Username == user.Username && u.Password == user.Password)
                ?.Id;
        }

        public bool Register(User user)
        {
            try
            {
                context.Users.Add(user);
                context.SaveChanges();
            }
            catch (Exception)
            {
                return false;
            }
            return true;
        }
    }
}
