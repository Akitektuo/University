#include "MemoryRepository.h"

bool MemoryRepository::add(const TrenchCoat& trenchCoat)
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

bool MemoryRepository::update(const TrenchCoat& trenchCoat)
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

bool MemoryRepository::remove(std::string trenchCoatName)
{
	int indexOfMatchingCoat = trenchCoats.findIndexOf([&](const TrenchCoat& trenchCoatElement) {
		return trenchCoatElement.getName() == trenchCoatName;
	});

	if (indexOfMatchingCoat < 0)
	{
		return false;
	}

	trenchCoats.removeAt(indexOfMatchingCoat);
	return true;
}

ArrayList<TrenchCoat> MemoryRepository::getTrenchCoatsAsArrayList() const
{
	return trenchCoats;
}
