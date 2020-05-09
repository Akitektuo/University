#pragma once

#include "Action.h"
#include "Repository.h"

class ActionAdd : public Action
{
private:
	Song addedSong;

public:
	void setAddedSong(Song addedSong);

	void executeUndo();
	void executeRedo();
};

