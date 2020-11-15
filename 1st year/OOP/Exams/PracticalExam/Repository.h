#pragma once

#include "Domain.h"
#include <vector>
#include <algorithm>
#include <fstream>
#include <sstream>
#include <exception>

class Repository
{
private:
	std::string astronomersFileName;
	std::string starsFileName;

	std::vector<std::string> split(std::string line);

	std::vector<Astronomer> readAstronomersFromFile();
	void saveAstronomersToFile(std::vector<Astronomer> astronomers);

	std::vector<Star> readStarsFromFile();
	void saveStarsToFile(std::vector<Star> stars);

public:
	Repository(std::string astronomersFileName = "", std::string starsFileName = "");

	std::vector<Astronomer> getAllAstronomers();

	std::vector<Star> getAllStars();

	void addStar(Star starToAdd);

	void removeStar(std::string byName);

	void sortStars();
};

