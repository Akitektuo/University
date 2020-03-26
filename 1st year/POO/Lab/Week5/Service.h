#pragma once

#include "Repository.h"

class Service
{
private:
	bool isAdmin;
	int currentTrenchCoatIndex;
	Repository repository;
	ArrayList<TrenchCoat> shoppingCart;

public:
	bool setMode(char mode);

	bool addTrenchCoat(std::string trenchCoatName, std::string trenchCoatSize, int trenchCoatPrice, std::string trenchCoatImage);

	bool updateTrenchCoat(std::string oldTrenchCoatName, std::string newTrenchCoatSize, int newTrenchCoatPrice, std::string newTrenchCoatImage);

	bool deleteTrenchCoat(std::string trenchCoatName);

	const ArrayList<TrenchCoat>& getListOfTrenchCoats();

	const TrenchCoat getNextTrenchCoat();

	bool saveTrenchCoat(std::string trenchCoatName);

	const ArrayList<TrenchCoat> getListOfTrenchCoatsBySizeAndPrice(std::string trenchCoatSize, int trenchCoatPrice);

	const ArrayList<TrenchCoat>& getShoppingListOfTrenchCoats();
};

