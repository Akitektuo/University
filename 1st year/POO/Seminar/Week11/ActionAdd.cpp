#include "ActionAdd.h"

void ActionAdd::setAddedSong(Song addedSong)
{
	this->addedSong = addedSong;
}

void ActionAdd::executeUndo()
{
	repository.removeSong(addedSong.getId());
}

void ActionAdd::executeRedo()
{
	repository.addSong(addedSong);
}
