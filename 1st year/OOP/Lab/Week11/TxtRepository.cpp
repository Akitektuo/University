#include "TxtRepository.h"

ArrayList<TrenchCoat> TxtRepository::getTrenchCoatArrayListFromFile() const
{
	std::ifstream file(filePath);
	ArrayList<TrenchCoat> trenchCoats;

	std::string line;
	while (std::getline(file, line))
	{
		auto data = split(line, DELIMITER);
		trenchCoats.add({ data.get(POSITION_NAME), data.get(POSITION_SIZE), std::stoi(data.get(POSITION_PRICE)), data.get(POSITION_IMAGE) });
	}

	file.close();

	return trenchCoats;
}

void TxtRepository::saveTrenchCoatArrayListToFile(ArrayList<TrenchCoat> trenchCoats) const
{
	std::ofstream file(filePath);
	trenchCoats.forEach([&](const TrenchCoat& trenchCoat) {
		file << trenchCoat.getName() << DELIMITER
			<< trenchCoat.size << DELIMITER
			<< trenchCoat.price << DELIMITER
			<< trenchCoat.image << "\n";
	});

	file.close();
}
