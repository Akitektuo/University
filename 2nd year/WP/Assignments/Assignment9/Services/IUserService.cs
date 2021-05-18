using Assignment9.Models;

namespace Assignment9.Services
{
    public interface IUserService
    {
        bool Register(User user);

        int? Login(User user);
    }
}
