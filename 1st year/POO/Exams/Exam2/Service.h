#pragma once

#include <cassert>
#include "Repository.h"

class Service
{
private:
	std::string FILE_PATH = "cars.txt";

	Repository repository{ FILE_PATH };

public:
	const std::vector<Car>& getAllCars() const;
	int getNumberOfCarsForManufacturer(std::string name) const;
};

void runAll_GetNumberOfCarsForManufacturer_Tests();

