#include "ActionAdd.h"

ActionAdd::ActionAdd(Repository* repository) : Action { repository } {}

void ActionAdd::setAddedTrenchCoat(TrenchCoat trenchCoat)
{
	this->trenchCoat = trenchCoat;
}

void ActionAdd::executeUndo()
{
	repository->remove(trenchCoat.getName());
}

void ActionAdd::executeRedo()
{
	repository->add(trenchCoat);
}
