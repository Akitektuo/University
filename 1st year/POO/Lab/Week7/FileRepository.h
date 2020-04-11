#pragma once

#include "ArrayList.h"
#include "TrenchCoat.h"
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>

class FileRepository
{
private:
	const std::string DELIMITER = "_;_";
	std::string fileName;

	ArrayList<std::string> splitData(std::string line) const;
	ArrayList<TrenchCoat> getTrenchCoatArrayListFromFile() const;
	void saveTrenchCoatArrayListToFile(ArrayList<TrenchCoat> trenchCoats) const;

public:
	void addFilePath(std::string path);

	bool add(const TrenchCoat& trenchCoat);
	bool update(const TrenchCoat& trenchCoat);
	bool remove(std::string trenchCoatName);

	ArrayList<TrenchCoat> getTrenchCoatsAsArrayList() const;
};

