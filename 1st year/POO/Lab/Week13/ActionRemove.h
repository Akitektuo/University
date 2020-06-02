#pragma once

#include "Action.h"

class ActionRemove : public Action
{
private:
	TrenchCoat trenchCoat;

public:
	ActionRemove(Repository* repository);

	void setRemovedTrenchCoat(TrenchCoat trenchCoat);

	void executeUndo() override;
	void executeRedo() override;
};
