#pragma once

#include "Repository.h"

class MemoryRepository : public Repository
{
private:
	ArrayList<TrenchCoat> trenchCoats;

public:
	const std::string TYPE = TYPE_MEMORY;

	bool add(const TrenchCoat& trenchCoat) override;
	bool update(const TrenchCoat& trenchCoat, std::function<void(const TrenchCoat& trenchCoatBeforeUpdate)> beforeUpdateAction = [](const TrenchCoat&) {}) override;
	bool remove(std::string trenchCoatName, std::function<void(const TrenchCoat& trenchCoatBeforeRemove)> beforeRemoveAction = [](const TrenchCoat&) {}) override;

	ArrayList<TrenchCoat> getTrenchCoatsAsArrayList() const override;
};

