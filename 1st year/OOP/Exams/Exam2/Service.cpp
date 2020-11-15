#include "Service.h"

const std::vector<Car>& Service::getAllCars() const
{
	return repository.getAllCars();
}

/*
	Computes the number of cars for a given manufacturer

	Input: string - name of manufacturer
	Output: int - number of cars
 */
int Service::getNumberOfCarsForManufacturer(std::string name) const
{
	auto cars = repository.getAllCars();
	return std::count_if(cars.begin(), cars.end(), [&](const Car& car) { return car.name == name; });
}

void GetNumberOfCarsForManufacturer_NoCars_Returns0()
{
	Repository testRepository{};

}

void GetNumberOfCarsForManufacturer_OneCarForManufacturer_Returns1()
{

}

void GetNumberOfCarsForManufacturer_FourCarsForManufacturer_Returns4()
{

}

void runAll_GetNumberOfCarsForManufacturer_Tests()
{
	GetNumberOfCarsForManufacturer_NoCars_Returns0();
	GetNumberOfCarsForManufacturer_OneCarForManufacturer_Returns1();
	GetNumberOfCarsForManufacturer_FourCarsForManufacturer_Returns4();
}
