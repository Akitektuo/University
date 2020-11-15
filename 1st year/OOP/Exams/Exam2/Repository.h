#pragma once

#include <vector>
#include <fstream>
#include <algorithm>
#include "Car.h"

class Repository
{
private:
	std::vector<Car> cars;

	std::vector<std::string> split(std::string line, std::string delimiter);

	void getCarsFromFile(std::string fileName);
public:
	Repository(std::string fileName = "");

	const std::vector<Car>& getAllCars() const;
};

