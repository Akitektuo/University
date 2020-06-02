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

bool MemoryRepository::update(const TrenchCoat& trenchCoat, std::function<void(const TrenchCoat& trenchCoatBeforeUpdate)> beforeUpdateAction)
{
	int indexOfMatchingCoat = trenchCoats.findIndexOf([&](const TrenchCoat& trenchCoatElement) {
		return trenchCoatElement.isDuplicateTo(trenchCoat);
	});

	if (indexOfMatchingCoat < 0)
	{
		return false;
	}

	beforeUpdateAction(trenchCoats.get(indexOfMatchingCoat));
	trenchCoats.set(indexOfMatchingCoat, trenchCoat);
	return true;
}

bool MemoryRepository::remove(std::string trenchCoatName, std::function<void(const TrenchCoat& trenchCoatBeforeRemove)> beforeRemoveAction)
{
	int indexOfMatchingCoat = trenchCoats.findIndexOf([&](const TrenchCoat& trenchCoatElement) {
		return trenchCoatElement.getName() == trenchCoatName;
	});

	if (indexOfMatchingCoat < 0)
	{
		return false;
	}

	beforeRemoveAction(trenchCoats.get(indexOfMatchingCoat));
	trenchCoats.removeAt(indexOfMatchingCoat);
	return true;
}

ArrayList<TrenchCoat> MemoryRepository::getTrenchCoatsAsArrayList() const
{
	return trenchCoats;
}
