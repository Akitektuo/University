#pragma once

#include "Repository.h"

class Action
{
protected:
	Repository* repository;

public:
	Action(Repository* repository);

	virtual void executeUndo() = 0;
	virtual void executeRedo() = 0;

	~Action();
};

