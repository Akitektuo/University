#include "Repository.h"

bool Repository::add(const TrenchCoat& trenchCoat)
{
	if (trenchCoats.any([&](const TrenchCoat& trenchCoatElement) {
		return trenchCoatElement.isDuplicateTo(trenchCoat);
	}))
	{
		return false;
	}

	trenchCoats.add(trenchCoat);
	return true;
}

bool Repository::update(const TrenchCoat& trenchCoat)
{
	int indexOfMatchingCoat = trenchCoats.findIndexOf([&](const TrenchCoat& trenchCoatElement) {
		return trenchCoatElement.isDuplicateTo(trenchCoat);
	});
	
	if (indexOfMatchingCoat < 0)
	{
		return false;
	}

	trenchCoats.set(indexOfMatchingCoat, trenchCoat);
	return true;
}

bool Repository::remove(std::string trenchCoatName)
{
	int indexOfMatchingCoat = trenchCoats.findIndexOf([&](const TrenchCoat& trenchCoatElement) {
		return trenchCoatElement.getName() == trenchCoatName;
	});

	if (indexOfMatchingCoat < 0)
	{
		return false;
	}

	return trenchCoats.removeAt(indexOfMatchingCoat);
}

const ArrayList<TrenchCoat>& Repository::getTrenchCoatsAsArrayList() const
{
	return trenchCoats;
}
