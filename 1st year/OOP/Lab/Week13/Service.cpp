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
	auto action = new ActionAdd { repository };
	action->setAddedTrenchCoat(trenchCoat);

	try
	{
		if (repository->add(trenchCoat))
		{
			redoStack.clearAndFree();
			undoStack.add(action);
			return true;
		}
		delete action;
		return false;
	}
	catch (const FileRepositoryException& exception)
	{
		delete action;
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
	auto action = new ActionUpdate{ repository };
	action->setTrenchCoatAfterUpdate(trenchCoat);

	try
	{
		if (repository->update(trenchCoat, [&](const TrenchCoat& trenchCoatBeforeUpdate) { action->setTrenchCoatBeforeUpdate(trenchCoatBeforeUpdate); }))
		{
			redoStack.clearAndFree();
			undoStack.add(action);
			return true;
		}
		delete action;
		return false;
	}
	catch (const FileRepositoryException& exception)
	{
		delete action;
		return false;
	}
}

bool Service::deleteTrenchCoat(std::string trenchCoatName)
{
	if (!isAdmin)
	{
		return false;
	}

	auto action = new ActionRemove{ repository };

	try
	{
		if (repository->remove(trenchCoatName, [&](const TrenchCoat& trenchCoatBeforeDelete) { action->setRemovedTrenchCoat(trenchCoatBeforeDelete); }))
		{
			redoStack.clearAndFree();
			undoStack.add(action);
			return true;
		}
		delete action;
		return false;
	}
	catch (const FileRepositoryException& exception)
	{
		delete action;
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
	undoStack.clearAndFree();
	redoStack.clearAndFree();

	setFilePathToRepository(fileLocation, &repository);
}

void Service::setShoppingCartLocation(std::string fileLocation)
{
	setFilePathToRepository(fileLocation, &shoppingCart);
}

void Service::undo()
{
	if (!isAdmin || undoStack.isEmpty())
	{
		return;
	}

	auto action = undoStack.pop();
	action->executeUndo();
	redoStack.add(action);
}

void Service::redo()
{
	if (!isAdmin || redoStack.isEmpty())
	{
		return;
	}

	auto action = redoStack.pop();
	action->executeRedo();
	undoStack.add(action);
}

Service::~Service()
{
	delete repository;
	delete shoppingCart;
}
