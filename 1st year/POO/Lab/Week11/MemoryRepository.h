#pragma once

#include "Repository.h"

class MemoryRepository : public Repository
{
private:
	ArrayList<TrenchCoat> trenchCoats;

public:
	const std::string TYPE = TYPE_MEMORY;

	bool add(const TrenchCoat& trenchCoat) override;
	bool update(const TrenchCoat& trenchCoat) override;
	bool remove(std::string trenchCoatName) override;

	ArrayList<TrenchCoat> getTrenchCoatsAsArrayList() const override;
};

