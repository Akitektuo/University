using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace CarRentals.Migrations
{
    public partial class AddParkLocations : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "ParkLocations",
                columns: table => new
                {
                    UserId = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Latitude = table.Column<decimal>(type: "decimal(28,15)", precision: 28, scale: 15, nullable: false),
                    Longitude = table.Column<decimal>(type: "decimal(28,15)", precision: 28, scale: 15, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ParkLocations", x => x.UserId);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ParkLocations");
        }
    }
}
