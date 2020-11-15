#include "ActionRemove.h"

ActionRemove::ActionRemove(Repository* repository) : Action { repository } {}

void ActionRemove::setRemovedTrenchCoat(TrenchCoat trenchCoat)
{
	this->trenchCoat = trenchCoat;
}

void ActionRemove::executeUndo()
{
	repository->add(trenchCoat);
}

void ActionRemove::executeRedo()
{
	repository->remove(trenchCoat.getName());
}
