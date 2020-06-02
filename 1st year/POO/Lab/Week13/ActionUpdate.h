#pragma once

#include "Action.h"

class ActionUpdate : public Action
{
private:
	TrenchCoat trenchCoatBeforeUpdate;
	TrenchCoat trenchCoatAfterUpdate;

public:
	ActionUpdate(Repository* repository);

	void setTrenchCoatBeforeUpdate(TrenchCoat trenchCoatBeforeUpdate);
	void setTrenchCoatAfterUpdate(TrenchCoat trenchCoatAfterUpdate);

	void executeUndo() override;
	void executeRedo() override;
};

