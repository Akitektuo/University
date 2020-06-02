#pragma once

#include "ArrayList.h"
#include "TrenchCoat.h"

const std::string TYPE_BASE = "base";
const std::string TYPE_MEMORY = "memory";
const std::string TYPE_FILE = "file";

class Repository
{
public:
	const std::string TYPE = TYPE_BASE;

	virtual bool add(const TrenchCoat& trenchCoat) = 0;
	virtual bool update(const TrenchCoat& trenchCoat, std::function<void(const TrenchCoat& trenchCoatBeforeUpdate)> beforeUpdateAction = [](const TrenchCoat&) {}) = 0;
	virtual bool remove(std::string trenchCoatName, std::function<void(const TrenchCoat& trenchCoatBeforeRemove)> beforeRemoveAction = [](const TrenchCoat&) {}) = 0;

	virtual ArrayList<TrenchCoat> getTrenchCoatsAsArrayList() const = 0;

	~Repository() {}
};

