#include "Service.h"

void Service::openFile(const FileRepository& fromRepository)
{
	if (fromRepository.filePath.empty())
	{
		return;
	}
	try
	{
		auto path = fromRepository.filePath;
		ShellExecute(0, 0, std::wstring(path.begin(), path.end()).c_str(), 0, 0, SW_SHOW);
	}
	catch (...)
	{
		return;
	}
}

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

	try
	{
		return repository.add(trenchCoat);
	}
	catch (const FileRepositoryException& exception)
	{
		return false;
	}
}

bool Service::updateTrenchCoat(std::string oldTrenchCoatName, std::string newTrenchCoatSize, int newTrenchCoatPrice, std::string newTrenchCoatImage)
{
	if (!isAdmin)
	{
		return false;
	}

	TrenchCoat trenchCoat { oldTrenchCoatName, newTrenchCoatSize, newTrenchCoatPrice, newTrenchCoatImage };

	try
	{
		return repository.update(trenchCoat);
	}
	catch (const FileRepositoryException& exception)
	{
		return false;
	}
}

bool Service::deleteTrenchCoat(std::string trenchCoatName)
{
	if (!isAdmin)
	{
		return false;
	}

	try
	{
		return repository.remove(trenchCoatName);
	}
	catch (const FileRepositoryException& exception)
	{
		return false;
	}
}

ArrayList<TrenchCoat> Service::getListOfTrenchCoats()
{
	openFile(repository);

	try
	{
		return repository.getTrenchCoatsAsArrayList();
	}
	catch (const FileRepositoryException& exception)
	{
		return {};
	}
}

TrenchCoat Service::getNextTrenchCoat()
{
	auto trenchCoats = repository.getTrenchCoatsAsArrayList();

	if (!trenchCoats.hasIndex(currentTrenchCoatIndex))
	{
		currentTrenchCoatIndex = 0;
	}
	try
	{
		return trenchCoats.get(currentTrenchCoatIndex++);
	}
	catch (...)
	{
		throw NoTrenchCoatException();
	}
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

ArrayList<TrenchCoat> Service::getShoppingListOfTrenchCoats()
{
	openFile(shoppingCart);
	return shoppingCart.getTrenchCoatsAsArrayList();
}

void Service::setFileLocation(std::string fileLocation)
{
	repository.filePath = fileLocation;
}

void Service::setShoppingCartLocation(std::string fileLocation)
{
	shoppingCart.filePath = fileLocation;
}
