#include "TrenchCoat.h"

TrenchCoat::TrenchCoat()
{
	price = 0;
}

TrenchCoat::TrenchCoat(std::string name, std::string size, int price, std::string image)
{
	this->name = name;
	this->size = size;
	this->price = price;
	this->image = image;
}

bool TrenchCoat::operator==(const TrenchCoat& other)
{
	return getName() == other.getName() && size == other.size && price == other.price && image == other.image;
}

std::string TrenchCoat::getName() const
{
	return name;
}

bool TrenchCoat::isDuplicateTo(const TrenchCoat& otherTrenchCoat) const
{
	return getName() == otherTrenchCoat.getName();
}

std::string TrenchCoat::toString() const
{
	std::string string = "Name: " + name + "\n";
	string += "Size: " + size + "\n";
	string += "Price: " + std::to_string(price) + "\n";
	string += "Image: " + image + "\n";

	return string;
}
