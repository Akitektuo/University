#pragma once
#include "FileRepository.h"
class CsvRepository : public FileRepository
{
private:
	const std::string DELIMITER = ",";

public:
	virtual ArrayList<TrenchCoat> getTrenchCoatArrayListFromFile() const override;
	virtual void saveTrenchCoatArrayListToFile(ArrayList<TrenchCoat> trenchCoats) const override;
};

