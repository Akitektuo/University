#pragma once

#include "ArrayList.h"
#include "TrenchCoat.h"

class MemoryRepository
{
private:
	ArrayList<TrenchCoat> trenchCoats;

public:
	bool add(const TrenchCoat& trenchCoat);
	bool update(const TrenchCoat& trenchCoat);
	bool remove(std::string trenchCoatName);

	const ArrayList<TrenchCoat>& getTrenchCoatsAsArrayList() const;
};

