#pragma once

#include "Action.h"
#include "Repository.h"

class ActionRemove : public Action
{
private:
	Song deletedSong;

public:
	void setDeletedSong(Song deletedSong);

	void executeUndo();
	void executeRedo();
};

