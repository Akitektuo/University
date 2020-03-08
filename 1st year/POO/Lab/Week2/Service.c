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

void getOffersListService(Service* service, char* listOfOffers)
{
	Repository* repository = service->repository;

	char* offerAsString = (char*) malloc(OFFER_STRING_AVERAGE_MEMORY_SIZE * sizeof(char));

	for (int i = 0; i < repository->size; i++)
	{
		Offer* currentOffer = getOfferAtIndex(repository, i);

		snprintf(
			offerAsString, 
			OFFER_STRING_AVERAGE_MEMORY_SIZE, 
			"Id: %d\nType: %s\nDestination: %s\nPrice: %d\n\n", 
			getId(currentOffer), 
			getType(currentOffer), 
			getDestination(currentOffer), 
			getPrice(currentOffer)
		);

		if (offerAsString != NULL)
		{
			strcat_s(listOfOffers, LIST_MAXIMUM_STRING_SIZE, offerAsString);
		}
	}

	free(offerAsString);
}

void getOffersListByDestinationService(Service* service, char* destination, char* listOfOffers)
{
	Repository* repository = service->repository;

	char* offerAsString = (char*) malloc(OFFER_STRING_AVERAGE_MEMORY_SIZE * sizeof(char));

	for (int i = 0; i < repository->size; i++)
	{
		Offer* currentOffer = getOfferAtIndex(repository, i);

		if (_strcmpi(getDestination(currentOffer), destination))
		{
			continue;
		}

		snprintf(
			offerAsString,
			OFFER_STRING_AVERAGE_MEMORY_SIZE,
			"Id: %d\nType: %s\nDestination: %s\nPrice: %d\n\n",
			getId(currentOffer),
			getType(currentOffer),
			getDestination(currentOffer),
			getPrice(currentOffer)
		);

		if (offerAsString != NULL)
		{
			strcat_s(listOfOffers, OFFER_STRING_AVERAGE_MEMORY_SIZE, offerAsString);
		}
	}

	free(offerAsString);
}

void destroyService(Service* service)
{
	destroyRepository(service->repository);
	free(service);
}