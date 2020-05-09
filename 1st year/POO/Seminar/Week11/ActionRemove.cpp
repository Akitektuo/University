#include "ActionRemove.h"

void ActionRemove::setDeletedSong(Song deletedSong)
{
	this->deletedSong = deletedSong;
}

void ActionRemove::executeUndo()
{
	repository.addSong(deletedSong);
}

void ActionRemove::executeRedo()
{
	repository.removeSong(deletedSong.getId());
}
