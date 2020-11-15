#include "Service.h"

Service::Service(std::string astronomersFileName, std::string starsFileName): repository{ astronomersFileName, starsFileName }
{}

std::vector<Astronomer> Service::getAstronomers()
{
	return repository.getAllAstronomers();
}

std::vector<Star> Service::filterStars(Astronomer byAstronomer, std::string search, bool sameConstellation)
{
	auto stars = repository.getAllStars();
	std::vector<Star> filteredStars;

	std::copy_if(stars.begin(), stars.end(), std::back_inserter(filteredStars), [&](const Star& star) {
		if (sameConstellation) {
			return star.name.find(search) != std::string::npos && star.constellation == byAstronomer.getConstellation();
		}
		return star.name.find(search) != std::string::npos;
	});

	return filteredStars;
}

/*
 * Adds a new star to the repository
 * Input:
 *  	- Star: star to add
 *  	- Astronomer: the astronomer that adds the star
 * Throws: exception in case name is empty, alrdeay exists or the diameter is negative or zero
 */
void Service::addStar(Star starToAdd, Astronomer addedBy)
{
	if (starToAdd.name.empty() || starToAdd.diameter <= 0)
	{
		throw std::exception{};
	}
	starToAdd.constellation = addedBy.getConstellation();
	repository.addStar(starToAdd);
	// repository.sortStars();
}

void Service::removeStarByName(std::string starName)
{
	repository.removeStar(starName);
}

void Service::sortRepository()
{
	repository.sortStars();
}