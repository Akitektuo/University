#include "Repository.h"

std::vector<std::string> Repository::split(std::string line)
{
	std::vector<std::string> stringData;

	size_t previous = 0, position = 0;
	do
	{
		position = line.find(", ", previous);
		if (position == std::string::npos)
		{
			position = line.length();
		}
		std::string token = line.substr(previous, position - previous);
		if (!token.empty())
		{
			stringData.push_back(token);
		}
		previous = position + 2;
	} while (position < line.length() && previous < line.length());

	return stringData;
}

std::vector<Astronomer> Repository::readAstronomersFromFile()
{
	std::ifstream file(astronomersFileName);
	std::vector<Astronomer> astronomers;

	std::string line;
	while (std::getline(file, line))
	{
		auto data = split(line);
		astronomers.push_back({ data[0], data[1] });
	}

	file.close();

	return astronomers;
}

void Repository::saveAstronomersToFile(std::vector<Astronomer> astronomers)
{
	std::ofstream file(astronomersFileName);

	for (auto& astronomer : astronomers) {
		file << astronomer.getName() << ", " << astronomer.getConstellation() << "\n";
	}

	file.close();
}

std::vector<Star> Repository::readStarsFromFile()
{
	std::ifstream file(starsFileName);
	std::vector<Star> stars;

	std::string line;
	while (std::getline(file, line))
	{
		auto data = split(line);
		stars.push_back({ data[0], data[1], std::stoi(data[2]), std::stoi(data[3]), std::stoi(data[4]) });
	}

	file.close();

	return stars;
}

void Repository::saveStarsToFile(std::vector<Star> stars)
{
	std::ofstream file(starsFileName);

	for (auto& star : stars) {
		file << star.name << ", "
			<< star.constellation << ", "
			<< star.ascension << ", "
			<< star.declination << ", "
			<< star.diameter << "\n";
	}

	file.close();
}

Repository::Repository(std::string astronomersFileName, std::string starsFileName) : astronomersFileName{ astronomersFileName }, starsFileName{ starsFileName }
{}

std::vector<Astronomer> Repository::getAllAstronomers()
{
	return readAstronomersFromFile();
}

std::vector<Star> Repository::getAllStars()
{
	return readStarsFromFile();
}

void Repository::addStar(Star starToAdd)
{
	auto stars = readStarsFromFile();

	for (const auto& star : stars)
	{
		if (star.name == starToAdd.name)
		{
			throw std::exception{};
		}
	}

	stars.push_back(starToAdd);

	saveStarsToFile(stars);
}

void Repository::removeStar(std::string byName)
{
	auto stars = readStarsFromFile();
	std::vector<Star> filteredStars;

	std::copy_if(stars.begin(), stars.end(), std::back_inserter(filteredStars), [&](const Star& star) {
		return star.name != byName;
	});

	saveStarsToFile(filteredStars);
}

void Repository::sortStars()
{
	auto stars = readStarsFromFile();

	std::sort(stars.begin(), stars.end(), [](const Star& starA, const Star& starB) {
		return starA.constellation < starB.constellation;
	});

	saveStarsToFile(stars);
}
