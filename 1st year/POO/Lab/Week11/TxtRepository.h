#pragma once
#include "FileRepository.h"
class TxtRepository : public FileRepository
{
private:
	const std::string DELIMITER = "_;_";

public:
	virtual ArrayList<TrenchCoat> getTrenchCoatArrayListFromFile() const override;
	virtual void saveTrenchCoatArrayListToFile(ArrayList<TrenchCoat> trenchCoats) const override;
};

