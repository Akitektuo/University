#pragma once

#include "Action.h"

class ActionAdd : public Action
{
private:
	TrenchCoat trenchCoat;

public:
	ActionAdd(Repository* repository);

	void setAddedTrenchCoat(TrenchCoat trenchCoat);

	void executeUndo() override;
	void executeRedo() override;
};

