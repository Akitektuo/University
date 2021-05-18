using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Assignment9.Models
{
    public class Type
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public string Name { get; set; }

        [Required]
        public string Image { get; set; }

        public ICollection<Recipe> Recipes { get; set; }
    }
}
