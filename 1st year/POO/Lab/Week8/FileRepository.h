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
	const std::string FILE_TYPE_TXT = ".txt";
	const std::string FILE_TYPE_CSV = ".csv";
	const std::string FILE_TYPE_HTML = ".html";

	const std::string DELIMITER_TXT = "_;_";
	const std::string DELIMITER_CSV = ",";
	const std::string DELIMITER_HTML = "</td><td>";

	std::string fileName;

	bool endsWith(std::string string, std::string withString) const;
	ArrayList<std::string> splitData(std::string line, std::string delimiter) const;
	std::string getFileType() const;

	ArrayList<TrenchCoat> decodeFileWithDelimiter(std::string delimiter) const;
	void encodeFileWithDelimiter(ArrayList<TrenchCoat> trenchCoats, std::string delimiter) const;

	ArrayList<TrenchCoat> decodeTxtFile() const;
	void encodeTxtFile(ArrayList<TrenchCoat> trenchCoats) const;

	ArrayList<TrenchCoat> decodeCsvFile() const;
	void encodeCsvFile(ArrayList<TrenchCoat> trenchCoats) const;

	ArrayList<TrenchCoat> decodeHtmlFile() const;
	void encodeHtmlFile(ArrayList<TrenchCoat> trenchCoats) const;

	ArrayList<TrenchCoat> getTrenchCoatArrayListFromFile() const;
	void saveTrenchCoatArrayListToFile(ArrayList<TrenchCoat> trenchCoats) const;

public:
	void setFilePath(std::string path);

	bool add(const TrenchCoat& trenchCoat);
	bool update(const TrenchCoat& trenchCoat);
	bool remove(std::string trenchCoatName);

	ArrayList<TrenchCoat> getTrenchCoatsAsArrayList() const;
};

