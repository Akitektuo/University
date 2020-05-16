#pragma once

#include "ConfigurationFile.h"
#include "MemoryRepository.h"
#include "TxtRepository.h"
#include "CsvRepository.h"
#include "HtmlRepository.h"
#include <shlobj.h>

class Service
{
private:
	int currentTrenchCoatIndex;
	Repository* repository;
	Repository* shoppingCart;

	bool endsWith(std::string string, std::string withString) const;

	void readConfiguration();

	void openFile(Repository* fromRepository);

	void setFilePathToRepository(std::string filePath, Repository** toRepository);

public:
	bool isAdmin;

	Service();

	bool setMode(char mode);

	bool addTrenchCoat(std::string trenchCoatName, std::string trenchCoatSize, int trenchCoatPrice, std::string trenchCoatImage);

	bool updateTrenchCoat(std::string oldTrenchCoatName, std::string newTrenchCoatSize, int newTrenchCoatPrice, std::string newTrenchCoatImage);

	bool deleteTrenchCoat(std::string trenchCoatName);

	ArrayList<TrenchCoat> getListOfTrenchCoats();

	TrenchCoat getNextTrenchCoat();

	bool saveTrenchCoat(std::string trenchCoatName);

	ArrayList<TrenchCoat> getListOfTrenchCoatsBySizeAndPrice(std::string trenchCoatSize, int trenchCoatPrice);

	ArrayList<TrenchCoat> getShoppingListOfTrenchCoats();

	void setFileLocation(std::string fileLocation);

	void setShoppingCartLocation(std::string fileLocation);

	~Service();
};
