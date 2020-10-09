#include "Service.h"

bool Service::endsWith(std::string string, std::string withString) const
{
	return string.size() >= withString.size() && string.substr(string.size() - withString.size()) == withString;
}

void Service::readConfiguration()
{
	ConfigurationFile configuration;

	auto fileLocation = configuration.getStringValue(configuration.KEY_FILE_LOCATION);
	auto mylistLocation = configuration.getStringValue(configuration.KEY_MYLIST_LOCATION);

	if (fileLocation.empty())
	{
		repository = new MemoryRepository;
	}
	else
	{
		setFilePathToRepository(fileLocation, &repository);
	}

	if (mylistLocation.empty())
	{
		shoppingCart = new MemoryRepository;
	}
	else
	{
		setFilePathToRepository(mylistLocation, &shoppingCart);
	}
}

void Service::openFile(Repository* fromRepository)
{
	try
	{
		auto path = ((FileRepository*) fromRepository)->filePath;
		if (path.empty())
		{
			return;
		}
	
		ShellExecute(0, 0, std::wstring(path.begin(), path.end()).c_str(), 0, 0, SW_SHOW);
	}
	catch (...)
	{
		return;
	}
}

void Service::setFilePathToRepository(std::string filePath, Repository** toRepository)
{
	if (endsWith(filePath, FILE_TYPE_TXT))
	{
		delete *toRepository;
		*toRepository = new TxtRepository;
		(*(FileRepository**) toRepository)->filePath = filePath;
	}
	else if (endsWith(filePath, FILE_TYPE_CSV))
	{
		delete *toRepository;
		*toRepository = new CsvRepository;
		(*(FileRepository**)toRepository)->filePath = filePath;
	}
	else if (endsWith(filePath, FILE_TYPE_HTML))
	{
		delete *toRepository;
		*toRepository = new HtmlRepository;
		(*(FileRepository**)toRepository)->filePath = filePath;
	}
}

Service::Service()
{
	isAdmin = false;
	currentTrenchCoatIndex = 0;
	repository = nullptr;
	shoppingCart = nullptr;
	readConfiguration();
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
		return repository->add(trenchCoat);
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
		return repository->update(trenchCoat);
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
		return repository->remove(trenchCoatName);
	}
	catch (const FileRepositoryException& exception)
	{
		return false;
	}
}

ArrayList<TrenchCoat> Service::getListOfTrenchCoats()
{
	openFile((FileRepository*) repository);

	try
	{
		return repository->getTrenchCoatsAsArrayList();
	}
	catch (const FileRepositoryException& exception)
	{
		return {};
	}
}

TrenchCoat Service::getNextTrenchCoat()
{
	auto trenchCoats = repository->getTrenchCoatsAsArrayList();

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
		auto trenchCoat = repository->getTrenchCoatsAsArrayList().find([&](const TrenchCoat& trenchCoatElement) {
			return trenchCoatElement.getName() == trenchCoatName;
		});

		shoppingCart->add(trenchCoat);
		return true;
	}
	catch (...)
	{
		return false;
	}
}

ArrayList<TrenchCoat> Service::getListOfTrenchCoatsBySizeAndPrice(std::string trenchCoatSize, int trenchCoatMaxPrice)
{
	auto listOfAllTrenchCoats = repository->getTrenchCoatsAsArrayList();
	return listOfAllTrenchCoats.filter([&](const TrenchCoat& trenchCoatElement) {
		return trenchCoatElement.size == trenchCoatSize && trenchCoatElement.price < trenchCoatMaxPrice;
	});
}

ArrayList<TrenchCoat> Service::getShoppingListOfTrenchCoats()
{
	openFile((FileRepository*) shoppingCart);
	return shoppingCart->getTrenchCoatsAsArrayList();
}

void Service::setFileLocation(std::string fileLocation)
{
	setFilePathToRepository(fileLocation, &repository);
}

void Service::setShoppingCartLocation(std::string fileLocation)
{
	setFilePathToRepository(fileLocation, &shoppingCart);
}

Service::~Service()
{
	delete repository;
	delete shoppingCart;
}