#include "Service.h"

Service::Service()
{
	isAdmin = false;
	currentTrenchCoatIndex = 0;
}

bool Service::setMode(char mode)
{
	switch (mode)
	{
	case 'A':
		isAdmin = true;
		return isAdmin;
	default:
		isAdmin = false;
		return isAdmin;
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

ArrayList<TrenchCoat> Service::getListOfTrenchCoats()
{
	return repository.getTrenchCoatsAsArrayList();
}

TrenchCoat Service::getNextTrenchCoat()
{
	auto trenchCoats = repository.getTrenchCoatsAsArrayList();

	if (!trenchCoats.hasIndex(currentTrenchCoatIndex))
	{
		currentTrenchCoatIndex = 0;
	}

	return trenchCoats.get(currentTrenchCoatIndex++);
}

bool Service::saveTrenchCoat(std::string trenchCoatName)
{
	try
	{
		auto trenchCoat = repository.getTrenchCoatsAsArrayList().find([&](const TrenchCoat& trenchCoatElement) {
			return trenchCoatElement.getName() == trenchCoatName;
		});

		shoppingCart.add(trenchCoat);
		return true;
	}
	catch (...)
	{
		return false;
	}
}

ArrayList<TrenchCoat> Service::getListOfTrenchCoatsBySizeAndPrice(std::string trenchCoatSize, int trenchCoatMaxPrice)
{
	auto listOfAllTrenchCoats = repository.getTrenchCoatsAsArrayList();
	return listOfAllTrenchCoats.filter([&](const TrenchCoat& trenchCoatElement) {
		return trenchCoatElement.size == trenchCoatSize && trenchCoatElement.price < trenchCoatMaxPrice;
	});
}

const ArrayList<TrenchCoat>& Service::getShoppingListOfTrenchCoats()
{
	return shoppingCart;
}

void Service::setFileLocation(std::string fileLocation)
{
	repository.addFilePath(fileLocation);
}
