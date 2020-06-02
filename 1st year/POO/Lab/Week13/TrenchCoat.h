#pragma once

#include <string>

class TrenchCoat
{
private:
	std::string name;
public:
	std::string size;
	int price;
	std::string image;

	TrenchCoat();
	TrenchCoat(std::string name, std::string size, int price, std::string image);

	bool operator==(const TrenchCoat& other);

	std::string getName() const;
	bool isDuplicateTo(const TrenchCoat& otherTrenchCoat) const;
	std::string toString() const;
};

