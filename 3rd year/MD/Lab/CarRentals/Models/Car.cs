using System;
using System.ComponentModel.DataAnnotations;

namespace CarRentals.Models
{
    public class Car
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public string Brand { get; set; }

        [Required]
        public string Model { get; set; }

        [Required]
        public int FabricationYear { get; set; }

        [Required]
        public string Color { get; set; }

        [Required]
        public byte[] Image { get; set; }

        [Required]
        public bool IsAutomatic { get; set; }

        public Guid UserId { get; set; }
    }
}
