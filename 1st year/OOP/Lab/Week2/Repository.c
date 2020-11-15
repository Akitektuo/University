#include "Repository.h"
#include <stdlib.h>

Repository* createRepository()
{
	Repository* newRepository = (Repository*) malloc(sizeof(Repository));
	
	if (newRepository == NULL)
	{
		return NULL;
	}

	newRepository->memorySize = 0;
	newRepository->size = 0;
	newRepository->offers = NULL;

	return newRepository;
}

int addOffer(Repository* toRepository, Offer* newOffer)
{
	if (isOfferInRepository(toRepository, getId(newOffer)))
	{
		destroyOffer(newOffer);
		return 0;
	}

	if (toRepository->size < 1)
	{
		toRepository->memorySize = 2;
		toRepository->offers = (Offer**) calloc(toRepository->memorySize, sizeof(Offer*));
	}
	else if (toRepository->size >= toRepository->memorySize)
	{
		toRepository->memorySize *= 2;
		toRepository->offers = (Offer**) realloc(toRepository->offers, toRepository->memorySize * sizeof(Offer*));
	}

	if (toRepository->offers == NULL)
	{
		toRepository->memorySize /= 2;
		return 0;
	}

	toRepository->offers[toRepository->size] = newOffer;
	toRepository->size++;
	return 1;
}

Offer* getOffer(Repository* inRepository, int existingOfferId)
{
	for (int i = 0; i < inRepository->size; i++)
	{
		Offer* offerAtCurrentIndex = getOfferAtIndex(inRepository, i);
		if (getId(offerAtCurrentIndex) == existingOfferId)
		{
			return offerAtCurrentIndex;
		}
	}
	return NULL;
}

int removeOffer(Repository* fromRepository, int existingOfferId)
{
	for (int i = 0; i < fromRepository->size; i++)
	{
		Offer* offerAtCurrentIndex = getOfferAtIndex(fromRepository, i);
		if (getId(offerAtCurrentIndex) == existingOfferId)
		{
			destroyOffer(offerAtCurrentIndex);
			for (int j = i + 1; j < fromRepository->size; j++)
			{
				fromRepository->offers[j - 1] = fromRepository->offers[j];
			}

			if (--fromRepository->size < 1)
			{
				fromRepository->memorySize = 0;
				free(fromRepository->offers);
			}
			else if (fromRepository->size > 2 && fromRepository->size * 2 >= fromRepository->memorySize)
			{
				fromRepository->memorySize /= 2;
				fromRepository->offers = (Offer**) realloc(fromRepository->offers, fromRepository->memorySize * sizeof(Offer*));
			}
			return 1;
		}
	}
	return 0;
}

int isOfferInRepository(Repository* inRepository, int existingOfferId)
{
	for (int i = 0; i < inRepository->size; i++)
	{
		Offer* offerAtCurrentIndex = getOfferAtIndex(inRepository, i);
		if (getId(offerAtCurrentIndex) == existingOfferId)
		{
			return 1;
		}
	}
	return 0;
}

Offer* getOfferAtIndex(Repository* fromRepository, int index)
{
	if (index >= fromRepository->size)
	{
		return NULL;
	}
	return fromRepository->offers[index];
}

void destroyRepository(Repository* repository)
{
	if (repository->size > 0)
	{
		for (int i = 0; i < repository->size; i++)
		{
			destroyOffer(getOfferAtIndex(repository, i));
		}
		free(repository->offers);
	}
	free(repository);
}
