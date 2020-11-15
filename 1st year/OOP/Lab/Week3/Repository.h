#pragma once
#include "Offer.h"

typedef struct
{
	Offer** offers;
	int size;
	int memorySize;
} Repository;

Repository* createRepository();

int addOffer(Repository* toRepository, Offer* newOffer);

Offer* getOffer(Repository* inRepository, int existingOfferId);

int removeOffer(Repository* fromRepository, int existingOfferId);

int isOfferInRepository(Repository* inRepository, int existingOfferId);

Offer* getOfferAtIndex(Repository* fromRepository, int index);

void destroyRepository(Repository* repository);