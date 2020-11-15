#pragma once

#include "Action.h"
#include "Repository.h"

class ActionUpdate :
	public Action
{
private:
	Song songBeforeUpdate;
	Song songAfterUpdate;

public:
	void setSongBeforeUpdate(Song songBeforeUpdate);
	void setSongAfterUpdate(Song songAfterUpdate);

	void executeUndo();
	void executeRedo();
};

