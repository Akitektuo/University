using System.ComponentModel.DataAnnotations;

namespace Assignment9.Models
{
    public class Recipe
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public int UserId { get; set; }
        public User User { get; set; }

        [Required]
        public int TypeId { get; set; }
        public Type Type { get; set; }

        [Required]
        public string Title { get; set; }

        [Required]
        public string Description { get; set; }
    }
}
