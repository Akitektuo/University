#include "HtmlRepository.h"

bool HtmlRepository::endsWith(std::string string, std::string withString) const
{
	return string.size() >= withString.size() && string.substr(string.size() - withString.size()) == withString;
}

ArrayList<TrenchCoat> HtmlRepository::getTrenchCoatArrayListFromFile() const
{
	std::ifstream file(filePath);
	ArrayList<TrenchCoat> trenchCoats;

	std::string line;
	std::getline(file, line);
	while (std::getline(file, line))
	{
		if (endsWith(line, END_OF_HTML))
		{
			break;
		}
		auto data = split(line.substr(START_OF_DATA, line.size() - END_OF_DATA), DELIMITER);
		auto imageTag = data.get(POSITION_IMAGE);
		trenchCoats.add({ data.get(POSITION_NAME), data.get(POSITION_SIZE), std::stoi(data.get(POSITION_PRICE)), imageTag.substr(10, imageTag.size() - 12) });
	}

	file.close();
	return trenchCoats;
}

void HtmlRepository::saveTrenchCoatArrayListToFile(ArrayList<TrenchCoat> trenchCoats) const
{
	std::ofstream file(filePath);

	file << START_OF_HTML;
	trenchCoats.forEach([&](const TrenchCoat& trenchCoat) {
		file << START_OF_LINE << trenchCoat.getName() << DELIMITER
			<< trenchCoat.size << DELIMITER
			<< trenchCoat.price << DELIMITER
			<< "<img src=\"" << trenchCoat.image << END_OF_LINE;
	});
	file << END_OF_HTML;

	file.close();
}
