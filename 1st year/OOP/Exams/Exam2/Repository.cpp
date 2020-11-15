#include "Repository.h"

std::vector<std::string> Repository::split(std::string line, std::string delimiter)
{
	std::vector<std::string> data;

	size_t previous = 0, position = 0;
	do
	{
		position = line.find(delimiter, previous);
		if (position == std::string::npos)
		{
			position = line.length();
		}
		std::string token = line.substr(previous, position - previous);
		if (!token.empty())
		{
			data.push_back(token);
		}
		previous = position + delimiter.length();
	} while (position < line.length() && previous < line.length());

	return data;

    return std::vector<std::string>();
}

void Repository::getCarsFromFile(std::string fileName)
{
	std::ifstream file{ fileName };

	std::string line;
	while (std::getline(file, line))
	{
		auto data = split(line, " | ");
		cars.push_back({ data[0], data[1], std::stoi(data[2]), data[3] });
	}

	file.close();

	std::sort(cars.begin(), cars.end(), [](const Car& carA, const Car& carB) { return carA.name < carB.name; });
}

Repository::Repository(std::string fileName)
{
	if (!fileName.empty())
	{
		getCarsFromFile(fileName);
	}
}

const std::vector<Car>& Repository::getAllCars() const
{
	return cars;
}
