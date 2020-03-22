#include "Service.h"

void Service::setMode(char mode)
{
	switch (mode)
	{
	case 'A':
		isAdmin = true;
		return;
	default:
		isAdmin = false;
		return;
	}
}

bool Service::addTrenchCoat(std::string trenchCoatName, std::string trenchCoatSize, int trenchCoatPrice, std::string trenchCoatImage)
{
	if (!isAdmin)
	{
		return false;
	}

	TrenchCoat trenchCoat { trenchCoatName, trenchCoatSize, trenchCoatPrice, trenchCoatImage };
	return repository.add(trenchCoat);
}

bool Service::updateTrenchCoat(std::string oldTrenchCoatName, std::string newTrenchCoatSize, int newTrenchCoatPrice, std::string newTrenchCoatImage)
{
	if (!isAdmin)
	{
		return false;
	}

	TrenchCoat trenchCoat { oldTrenchCoatName, newTrenchCoatSize, newTrenchCoatPrice, newTrenchCoatImage };
	return repository.update(trenchCoat);
}

bool Service::deleteTrenchCoat(std::string trenchCoatName)
{
	if (!isAdmin)
	{
		return false;
	}

	return repository.remove(trenchCoatName);
}

const ArrayList<TrenchCoat>& Service::getListOfTrenchCoats()
{
	return repository.getTrenchCoatsAsArrayList();
}
