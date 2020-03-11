#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Service.h"

Service* createService()
{
	Service* newService = (Service*) malloc(sizeof(Service));

	if (newService == NULL)
	{
		return NULL;
	}

	newService->repository = createRepository();
	return newService;
}

int addOfferService(Service* service, int id, char* type, char* destination, int price)
{
	Offer* newOffer = createOffer(id, type, destination, price);

	return addOffer(service->repository, newOffer);
}

int updateOfferService(Service* service, int existingOfferId, char* type, char* destination, int price)
{
	Offer* offerToUpdate = getOffer(service->repository, existingOfferId);
	if (offerToUpdate == NULL)
	{
		return 0;
	}

	setType(offerToUpdate, type);
	setDestination(offerToUpdate, destination);
	setPrice(offerToUpdate, price);

	return 1;
}

int deleteOfferService(Service* service, int existingOfferId)
{
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
	Offer** listOfOffers = (Offer**)malloc((repository->size + 1) * sizeof(Offer*));
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

void destroyService(Service* service)
{
	destroyRepository(service->repository);
	free(service);
}