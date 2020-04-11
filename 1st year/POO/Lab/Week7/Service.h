#pragma once

#include "FileRepository.h"

class Service
{
private:
	bool isAdmin;
	int currentTrenchCoatIndex;
	FileRepository repository;
	ArrayList<TrenchCoat> shoppingCart;

public:
	Service();

	bool setMode(char mode);

	bool addTrenchCoat(std::string trenchCoatName, std::string trenchCoatSize, int trenchCoatPrice, std::string trenchCoatImage);

	bool updateTrenchCoat(std::string oldTrenchCoatName, std::string newTrenchCoatSize, int newTrenchCoatPrice, std::string newTrenchCoatImage);

	bool deleteTrenchCoat(std::string trenchCoatName);

	ArrayList<TrenchCoat> getListOfTrenchCoats();

	TrenchCoat getNextTrenchCoat();

	bool saveTrenchCoat(std::string trenchCoatName);

	ArrayList<TrenchCoat> getListOfTrenchCoatsBySizeAndPrice(std::string trenchCoatSize, int trenchCoatPrice);

	const ArrayList<TrenchCoat>& getShoppingListOfTrenchCoats();

	void setFileLocation(std::string fileLocation);
};
