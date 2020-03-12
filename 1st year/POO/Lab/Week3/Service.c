#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Service.h"

Service* createService()
{
	Service* newService = (Service*)malloc(sizeof(Service));

	if (newService == NULL)
	{
		return NULL;
	}

	newService->repository = createRepository();
	newService->tracker = createTracker();

	return newService;
}

int addOfferService(Service* service, int id, char* type, char* destination, int price)
{
	if (isOfferInRepository(service->repository, id))
	{
		return 0;
	}

	Offer* newOffer = createOffer(id, type, destination, price);

	resetRedoActions(service->tracker);
	trackAdd(service->tracker, newOffer);
	
	return addOffer(service->repository, newOffer);
}

int updateOfferService(Service* service, int existingOfferId, char* type, char* destination, int price)
{
	Offer* offerToUpdate = getOffer(service->repository, existingOfferId);
	if (offerToUpdate == NULL)
	{
		return 0;
	}

	resetRedoActions(service->tracker);
	trackEdit(service->tracker, offerToUpdate);

	setType(offerToUpdate, type);
	setDestination(offerToUpdate, destination);
	setPrice(offerToUpdate, price);

	return 1;
}

int deleteOfferService(Service* service, int existingOfferId)
{
	Offer* offerToDelete = getOffer(service->repository, existingOfferId);
	if (offerToDelete == NULL)
	{
		return 0;
	}

	resetRedoActions(service->tracker);
	trackDelete(service->tracker, offerToDelete);

	return removeOffer(service->repository, existingOfferId);
}

Offer** getOffersListService(Service* service)
{
	Repository* repository = service->repository;
	Offer** listOfOffers = (Offer**) malloc((repository->size + 1) * sizeof(Offer*));
	if (listOfOffers == NULL)
	{
		return;
	}

	int listOfOffersSize = 0;

	for (int i = 0; i < repository->size; i++)
	{
		listOfOffers[listOfOffersSize++] = getOfferAtIndex(repository, i);
	}

	listOfOffers[listOfOffersSize] = NULL;
	return listOfOffers;
}

Offer** getOffersListByDestinationService(Service* service, char* destination)
{
	Repository* repository = service->repository;
	Offer** listOfOffers = (Offer**) malloc((repository->size + 1) * sizeof(Offer*));
	if (listOfOffers == NULL)
	{
		return;
	}

	int listOfOffersSize = 0;

	for (int i = 0; i < repository->size; i++)
	{
		Offer* currentOffer = getOfferAtIndex(repository, i);

		if (_strcmpi(getDestination(currentOffer), destination))
		{
			continue;
		}

		listOfOffers[listOfOffersSize++] = currentOffer;
	}

	listOfOffers[listOfOffersSize] = NULL;
	return listOfOffers;
}

Offer** getOffersListByMaxPriceService(Service* service, int maxPrice)
{
	Repository* repository = service->repository;
	Offer** listOfOffers = (Offer**) malloc((repository->size + 1) * sizeof(Offer*));
	if (listOfOffers == NULL)
	{
		return;
	}

	int listOfOffersSize = 0;

	for (int i = 0; i < repository->size; i++)
	{
		Offer* currentOffer = getOfferAtIndex(repository, i);

		if (getPrice(currentOffer) > maxPrice)
		{
			continue;
		}

		listOfOffers[listOfOffersSize++] = currentOffer;
	}

	listOfOffers[listOfOffersSize] = NULL;
	return listOfOffers;
}

void undoAction(Service* service)
{
	Tracker* tracker = service->tracker;
	Action* actionToUndo = getAvailableUndo(tracker);

	if (actionToUndo == NULL)
	{
		return;
	}

	Offer* offerToRestore = getOfferFromAction(actionToUndo);
	Offer* offerToUpdate = getOffer(service->repository, getId(offerToRestore));

	switch (getTypeFromAction(actionToUndo))
	{
		case ACTION_ADD:
			trackUndoAdd(tracker, offerToRestore);
			addOffer(service->repository, offerToRestore);
			break;
		case ACTION_EDIT:
			trackUndoEdit(tracker, offerToUpdate);
			copyOfferWithoutId(offerToUpdate, offerToRestore);
			break;
		case ACTION_DELETE:
			trackUndoDelete(tracker, offerToRestore);
			removeOffer(service->repository, getId(offerToRestore));
			break;
	}

	destroyAction(actionToUndo);
}

void redoAction(Service* service)
{
	Tracker* tracker = service->tracker;
	Action* actionToRedo = getAvailableRedo(tracker);

	if (actionToRedo == NULL)
	{
		return;
	}

	Offer* offerToRestore = getOfferFromAction(actionToRedo);
	Offer* offerToUpdate = getOffer(service->repository, getId(offerToRestore));

	switch (getTypeFromAction(actionToRedo))
	{
		case ACTION_ADD:
			trackAdd(tracker, offerToRestore);
			addOffer(service->repository, offerToRestore);
			break;
		case ACTION_EDIT:
			trackEdit(tracker, offerToUpdate);
			copyOfferWithoutId(offerToUpdate, offerToRestore);
			break;
		case ACTION_DELETE:
			trackDelete(tracker, offerToRestore);
			removeOffer(service->repository, getId(offerToRestore));
			break;
	}

	destroyAction(actionToRedo);
}

void destroyService(Service* service)
{
	destroyRepository(service->repository);
	destroyTracker(service->tracker);

	free(service);
}