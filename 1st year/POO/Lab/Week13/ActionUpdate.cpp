#include "ActionUpdate.h"

ActionUpdate::ActionUpdate(Repository* repository) : Action { repository } {}

void ActionUpdate::setTrenchCoatBeforeUpdate(TrenchCoat trenchCoatBeforeUpdate)
{
	this->trenchCoatBeforeUpdate = trenchCoatBeforeUpdate;
}

void ActionUpdate::setTrenchCoatAfterUpdate(TrenchCoat trenchCoatAfterUpdate)
{
	this->trenchCoatAfterUpdate = trenchCoatAfterUpdate;
}

void ActionUpdate::executeUndo()
{
	repository->update(trenchCoatBeforeUpdate);
}

void ActionUpdate::executeRedo()
{
	repository->update(trenchCoatAfterUpdate);
}
