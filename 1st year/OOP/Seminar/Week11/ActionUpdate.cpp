#include "ActionUpdate.h"

void ActionUpdate::setSongBeforeUpdate(Song songBeforeUpdate)
{
	this->songBeforeUpdate = songBeforeUpdate;
}

void ActionUpdate::setSongAfterUpdate(Song songAfterUpdate)
{
	this->songAfterUpdate = songAfterUpdate;
}

void ActionUpdate::executeUndo()
{
	repository.updateSong(songBeforeUpdate);
}

void ActionUpdate::executeRedo()
{
	repository.updateSong(songAfterUpdate);
}
