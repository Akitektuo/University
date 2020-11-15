#pragma once

#include <string>


class Astronomer
{
private:
	std::string name;
	std::string constellation;

public:
	Astronomer(std::string name, std::string constellation);

	std::string getName() const;
	std::string getConstellation() const;
};

class Star
{
public:
	std::string name;
	std::string constellation;
	int ascension;
	int declination;
	int diameter;

	Star(std::string name = "", std::string constellation = "", int ascension = 0, int declination = 0, int diameter = 0);
};