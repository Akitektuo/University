#include "FileRepository.h"

ArrayList<std::string> FileRepository::splitData(std::string line) const
{
	ArrayList<std::string> stringData;

	size_t previous = 0, position = 0;
	do
	{
		position = line.find(DELIMITER, previous);
		if (position == std::string::npos)
		{
			position = line.length();
		}
		std::string token = line.substr(previous, position - previous);
		if (!token.empty())
		{
			stringData.add(token);
		}
		previous = position + DELIMITER.length();
	}
	while (position < line.length() && previous < line.length());

	return stringData;
}

ArrayList<TrenchCoat> FileRepository::getTrenchCoatArrayListFromFile() const
{
	if (fileName.empty())
	{
		return {};
	}

	std::ifstream file(fileName);
	ArrayList<TrenchCoat> trenchCoats;

	std::string line;
	while (std::getline(file, line))
	{
		auto data = splitData(line);
		trenchCoats.add({ data.get(0), data.get(1), std::stoi(data.get(2)), data.get(3) });
	}

	file.close();
	return trenchCoats;
}

void FileRepository::saveTrenchCoatArrayListToFile(ArrayList<TrenchCoat> trenchCoats) const
{
	if (fileName.empty())
	{
		return;
	}

	std::ofstream file(fileName);
	trenchCoats.forEach([&](const TrenchCoat& trenchCoat) {
		file << trenchCoat.getName() << DELIMITER << trenchCoat.size << DELIMITER << trenchCoat.price << DELIMITER << trenchCoat.image << "\n";
	});

	file.close();
}

void FileRepository::addFilePath(std::string path)
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
