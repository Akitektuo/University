#pragma once

#include "Repository.h"

class Service
{
private:
	Repository repository;

public:
	Service(std::string astronomersFileName = "", std::string starsFileName = "");

	std::vector<Astronomer> getAstronomers();

	std::vector<Star> filterStars(Astronomer byAstronomer, std::string search, bool sameConstellation);

	void addStar(Star starToAdd, Astronomer addedBy);

	void removeStarByName(std::string starName);

	void sortRepository();
};

