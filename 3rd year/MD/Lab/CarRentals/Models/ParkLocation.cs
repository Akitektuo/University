using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace CarRentals.Models
{
    public class ParkLocation
    {
        [Key, ForeignKey("User")]
        public Guid UserId { get; set; }

        public decimal Latitude { get; set; }

        public decimal Longitude { get; set; }
    }
}
