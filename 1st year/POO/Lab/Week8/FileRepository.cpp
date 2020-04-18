#include "FileRepository.h"

bool FileRepository::endsWith(std::string string, std::string withString) const
{
	return string.size() >= withString.size() && string.substr(string.size() - withString.size(), string.size()) == withString;
}

ArrayList<std::string> FileRepository::splitData(std::string line, std::string delimiter) const
{
	ArrayList<std::string> stringData;

	size_t previous = 0, position = 0;
	do
	{
		position = line.find(delimiter, previous);
		if (position == std::string::npos)
		{
			position = line.length();
		}
		std::string token = line.substr(previous, position - previous);
		if (!token.empty())
		{
			stringData.add(token);
		}
		previous = position + delimiter.length();
	}
	while (position < line.length() && previous < line.length());

	return stringData;
}

std::string FileRepository::getFileType() const
{
	if (fileName.size() > 4)
	{
		if (endsWith(fileName, FILE_TYPE_TXT))
		{
			return FILE_TYPE_TXT;
		}
		if (endsWith(fileName, FILE_TYPE_CSV))
		{
			return FILE_TYPE_CSV;
		}
	}
	if (fileName.size() > 5 && endsWith(fileName, FILE_TYPE_HTML)) {
		return FILE_TYPE_HTML;
	}
	return {};
}

ArrayList<TrenchCoat> FileRepository::decodeFileWithDelimiter(std::string delimiter) const
{
	std::ifstream file(fileName);
	ArrayList<TrenchCoat> trenchCoats;

	std::string line;
	while (std::getline(file, line))
	{
		auto data = splitData(line, delimiter);
		trenchCoats.add({ data.get(0), data.get(1), std::stoi(data.get(2)), data.get(3) });
	}

	file.close();
	return trenchCoats;
}

void FileRepository::encodeFileWithDelimiter(ArrayList<TrenchCoat> trenchCoats, std::string delimiter) const
{
	std::ofstream file(fileName);
	trenchCoats.forEach([&](const TrenchCoat& trenchCoat) {
		file << trenchCoat.getName() << delimiter
			<< trenchCoat.size << delimiter
			<< trenchCoat.price << delimiter
			<< trenchCoat.image << "\n";
	});

	file.close();
}

ArrayList<TrenchCoat> FileRepository::decodeTxtFile() const
{
	return decodeFileWithDelimiter(DELIMITER_TXT);
}

void FileRepository::encodeTxtFile(ArrayList<TrenchCoat> trenchCoats) const
{
	encodeFileWithDelimiter(trenchCoats, DELIMITER_TXT);
}

ArrayList<TrenchCoat> FileRepository::decodeCsvFile() const
{
	return decodeFileWithDelimiter(DELIMITER_CSV);
}

void FileRepository::encodeCsvFile(ArrayList<TrenchCoat> trenchCoats) const
{
	encodeFileWithDelimiter(trenchCoats, DELIMITER_CSV);
}

ArrayList<TrenchCoat> FileRepository::decodeHtmlFile() const
{
	std::ifstream file(fileName);
	ArrayList<TrenchCoat> trenchCoats;

	std::string line;
	std::getline(file, line);
	while (std::getline(file, line))
	{
		if (endsWith(line, "</table>"))
		{
			break;
		}
		auto data = splitData(line, DELIMITER_HTML);
		trenchCoats.add({ data.get(0), data.get(1), std::stoi(data.get(2)), data.get(3) });
	}

	file.close();
	return trenchCoats;
}

void FileRepository::encodeHtmlFile(ArrayList<TrenchCoat> trenchCoats) const
{
	std::ofstream file(fileName);

	file << "<table><tr><th>Name</th><th>Size</th><th>Price</th><th>Image</th></tr><tr><td>\n";
	trenchCoats.forEach([&](const TrenchCoat& trenchCoat) {
		file << trenchCoat.getName() << DELIMITER_HTML
			<< trenchCoat.size << DELIMITER_HTML
			<< trenchCoat.price << DELIMITER_HTML
			<< trenchCoat.image << "\n";
	});
	file << "</td></tr></table>";

	file.close();
}

ArrayList<TrenchCoat> FileRepository::getTrenchCoatArrayListFromFile() const
{
	if (fileName.empty())
	{
		return {};
	}

	auto fileType = getFileType();

	if (fileType == FILE_TYPE_TXT)
	{
		return decodeTxtFile();
	}
	if (fileType == FILE_TYPE_CSV)
	{
		return decodeCsvFile();
	}
	if (fileType == FILE_TYPE_HTML)
	{
		return decodeHtmlFile();
	}
	return {};
}

void FileRepository::saveTrenchCoatArrayListToFile(ArrayList<TrenchCoat> trenchCoats) const
{
	if (fileName.empty())
	{
		return;
	}

	auto fileType = getFileType();

	if (fileType == FILE_TYPE_TXT)
	{
		encodeTxtFile(trenchCoats);
	}
	if (fileType == FILE_TYPE_CSV)
	{
		encodeCsvFile(trenchCoats);
	}
	if (fileType == FILE_TYPE_HTML)
	{
		encodeHtmlFile(trenchCoats);
	}
}

void FileRepository::setFilePath(std::string path)
{
	fileName = path;
}

bool FileRepository::add(const TrenchCoat& trenchCoat)
{
	if (fileName.empty())
	{
		return false;
	}

	ArrayList<TrenchCoat> trenchCoats = getTrenchCoatArrayListFromFile();

	if (trenchCoats.any([&](const TrenchCoat& trenchCoatElement) {
		return trenchCoatElement.isDuplicateTo(trenchCoat);
	}))
	{
		return false;
	}

	trenchCoats.add(trenchCoat);

	saveTrenchCoatArrayListToFile(trenchCoats);

	return true;
}

bool FileRepository::update(const TrenchCoat& trenchCoat)
{
	if (fileName.empty())
	{
		return false;
	}

	ArrayList<TrenchCoat> trenchCoats = getTrenchCoatArrayListFromFile();

	int indexOfMatchingCoat = trenchCoats.findIndexOf([&](const TrenchCoat& trenchCoatElement) {
		return trenchCoatElement.isDuplicateTo(trenchCoat);
	});

	if (indexOfMatchingCoat < 0)
	{
		return false;
	}

	trenchCoats.set(indexOfMatchingCoat, trenchCoat);

	saveTrenchCoatArrayListToFile(trenchCoats);

	return true;
}

bool FileRepository::remove(std::string trenchCoatName)
{
	if (fileName.empty())
	{
		return false;
	}

	ArrayList<TrenchCoat> trenchCoats = getTrenchCoatArrayListFromFile();

	int indexOfMatchingCoat = trenchCoats.findIndexOf([&](const TrenchCoat& trenchCoatElement) {
		return trenchCoatElement.getName() == trenchCoatName;
		});

	if (indexOfMatchingCoat < 0)
	{
		return false;
	}

	trenchCoats.removeAt(indexOfMatchingCoat);

	saveTrenchCoatArrayListToFile(trenchCoats);

	return true;
}

ArrayList<TrenchCoat> FileRepository::getTrenchCoatsAsArrayList() const
{
	if (fileName.empty())
	{
		return {};
	}

	return getTrenchCoatArrayListFromFile();
}
