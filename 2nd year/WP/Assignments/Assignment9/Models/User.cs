using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Assignment9.Models
{
    public class User
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public string Username { get; set; }

        [Required]
        public string Password { get; set; }

        public ICollection<Recipe> Recipes { get; set; }
    }
}
