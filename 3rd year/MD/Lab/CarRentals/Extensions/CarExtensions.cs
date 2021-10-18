using CarRentals.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Security.Claims;

namespace CarRentals.Extensions
{
    public static class CarExtensions
    {
        public static Car AttachUserId(
            this Car car,
            ControllerBase controller)
        {
            car.UserId = Guid.Parse(controller.User.FindFirst(ClaimTypes.NameIdentifier).Value);
            return car;
        }
    }
}
