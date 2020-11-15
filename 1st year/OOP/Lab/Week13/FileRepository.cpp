#include "FileRepository.h"

ArrayList<std::string> FileRepository::split(std::string line, std::string delimiter) const
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

ArrayList<TrenchCoat> FileRepository::getTrenchCoatArrayListFromFile() const
{
	return {};
}

void FileRepository::saveTrenchCoatArrayListToFile(ArrayList<TrenchCoat> trenchCoats) const
{
}

bool FileRepository::add(const TrenchCoat& trenchCoat)
{
	if (filePath.empty())
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

bool FileRepository::update(const TrenchCoat& trenchCoat, std::function<void(const TrenchCoat& trenchCoatBeforeUpdate)> beforeUpdateAction)
{
	if (filePath.empty())
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

	beforeUpdateAction(trenchCoats.get(indexOfMatchingCoat));
	trenchCoats.set(indexOfMatchingCoat, trenchCoat);

	saveTrenchCoatArrayListToFile(trenchCoats);

	return true;
}

bool FileRepository::remove(std::string trenchCoatName, std::function<void(const TrenchCoat& trenchCoatBeforeRemove)> beforeRemoveAction)
{
	if (filePath.empty())
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

	beforeRemoveAction(trenchCoats.get(indexOfMatchingCoat));
	trenchCoats.removeAt(indexOfMatchingCoat);

	saveTrenchCoatArrayListToFile(trenchCoats);

	return true;
}

ArrayList<TrenchCoat> FileRepository::getTrenchCoatsAsArrayList() const
{
	if (filePath.empty())
	{
		return {};
	}

	return getTrenchCoatArrayListFromFile();
}
