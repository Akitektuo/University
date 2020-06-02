#pragma once

#include "Repository.h"
#include "Exceptions.h"
#include <fstream>
#include <sstream>

const std::string FILE_TYPE_TXT = ".txt";
const std::string FILE_TYPE_CSV = ".csv";
const std::string FILE_TYPE_HTML = ".html";

class FileRepository : public Repository
{
protected:
	const int POSITION_NAME = 0;
	const int POSITION_SIZE = 1;
	const int POSITION_PRICE = 2;
	const int POSITION_IMAGE = 3;

	const std::string FILE_TYPE_TXT = ".txt";
	const std::string FILE_TYPE_CSV = ".csv";
	const std::string FILE_TYPE_HTML = ".html";

	const std::string DELIMITER;

	ArrayList<std::string> split(std::string line, std::string delimiter) const;

	virtual ArrayList<TrenchCoat> getTrenchCoatArrayListFromFile() const;
	virtual void saveTrenchCoatArrayListToFile(ArrayList<TrenchCoat> trenchCoats) const;

public:
	const std::string TYPE = TYPE_FILE;

	std::string filePath;

	bool add(const TrenchCoat& trenchCoat) override;
	bool update(const TrenchCoat& trenchCoat, std::function<void(const TrenchCoat& trenchCoatBeforeUpdate)> beforeUpdateAction = [](const TrenchCoat&) {}) override;
	bool remove(std::string trenchCoatName, std::function<void(const TrenchCoat& trenchCoatBeforeRemove)> beforeRemoveAction = [](const TrenchCoat&) {}) override;

	ArrayList<TrenchCoat> getTrenchCoatsAsArrayList() const override;
};

