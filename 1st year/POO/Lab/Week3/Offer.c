#include "Offer.h"
#include <stdlib.h>
#include <string.h>

Offer* createOffer(int id, char* type, char* destination, int price)
{
	Offer* newOffer = (Offer*) malloc(sizeof(Offer));
	if (newOffer == NULL)
	{
		return NULL;
	}

	newOffer->id = id;
	newOffer->type = NULL;
	newOffer->destination = NULL;

	setType(newOffer, type);
	setDestination(newOffer, destination);
	setPrice(newOffer, price);

	return newOffer;
}

int getId(Offer* offer)
{
	return offer->id;
}

void setType(Offer* offer, char* type)
{
	if (offer->type && !strcmp(offer->type, type)) {
		return;
	}

	if (offer->type != NULL)
	{
		free(offer->type);
	}

	const int writableMemorySize = strlen(type) * sizeof(char) + 1;
	offer->type = (char*) malloc(writableMemorySize);

	if (offer->type == NULL)
	{
		return;
	}

	strcpy_s(offer->type, writableMemorySize, type);
}

char* getType(Offer* offer)
{
	return offer->type;
}

void setDestination(Offer* offer, char* destination)
{
	if (offer->destination && !strcmp(offer->destination, destination)) {
		return;
	}

	if (offer->destination != NULL)
	{
		free(offer->destination);
	}

	const int writableMemorySize = strlen(destination) * sizeof(char) + 1;
	offer->destination = (char*) malloc(writableMemorySize);

	if (offer->destination == NULL)
	{
		return;
	}

	strcpy_s(offer->destination, writableMemorySize, destination);
}

char* getDestination(Offer* offer)
{
	return offer->destination;
}

void setPrice(Offer* offer, int price)
{
	offer->price = price;
}

int getPrice(Offer* offer)
{
	return offer->price;
}

void destroyOffer(Offer* offer)
{
	free(offer->type);
	free(offer->destination);
	free(offer);
}
