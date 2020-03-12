#include "TestOffer.h"

void CreateOffer_AnyOffer_OfferCreated()
{
	int expectedId = 1;
	char* expectedType = "abc";
	char* expectedDestination = "abc";
	int expectedPrice = 123;

	Offer* createdOffer = createOffer(expectedId, expectedType, expectedDestination, expectedPrice);

	assert(expectedId == createdOffer->id);
	assert(!strcmp(expectedType, createdOffer->type));
	assert(!strcmp(expectedDestination, createdOffer->destination));
	assert(expectedPrice == createdOffer->price);

	destroyOffer(createdOffer);
}

void CreateOfferCopy_AnyOffer_OfferCopied()
{
	int expectedId = 1;
	char* expectedType = "abc";
	char* expectedDestination = "abc";
	int expectedPrice = 123;

	Offer* originalOffer = createOffer(expectedId, expectedType, expectedDestination, expectedPrice);

	Offer* offerCopy = createOfferCopy(originalOffer);

	destroyOffer(originalOffer);

	assert(expectedId == offerCopy->id);
	assert(!strcmp(expectedType, offerCopy->type));
	assert(!strcmp(expectedDestination, offerCopy->destination));
	assert(expectedPrice == offerCopy->price);

	destroyOffer(offerCopy);
}

void CopyOfferWithoutId_AnyOffer_OfferCopiedWithoutId()
{
	int expectedId = 0;
	char* expectedType = "abc";
	char* expectedDestination = "abc";
	int expectedPrice = 123;

	Offer* offerToCopyTo = createOffer(expectedId, "", "", 0);
	Offer* originalOffer = createOffer(1, expectedType, expectedDestination, expectedPrice);

	copyOfferWithoutId(offerToCopyTo, originalOffer);

	destroyOffer(originalOffer);

	assert(expectedId == offerToCopyTo->id);
	assert(!strcmp(expectedType, offerToCopyTo->type));
	assert(!strcmp(expectedDestination, offerToCopyTo->destination));
	assert(expectedPrice == offerToCopyTo->price);

	destroyOffer(offerToCopyTo);
}

void SetGetOffer_AnyOffer_SetAndGetOfferProperties()
{
	int expectedId = 1;
	char* expectedType = "abc";
	char* expectedDestination = "abc";
	int expectedPrice = 123;

	Offer* createdOffer = createOffer(expectedId, "", "", 0);

	setType(createdOffer, expectedType);
	setDestination(createdOffer, expectedDestination);
	setPrice(createdOffer, expectedPrice);

	assert(expectedId == getId(createdOffer));
	assert(!strcmp(expectedType, getType(createdOffer)));
	assert(!strcmp(expectedDestination, getDestination(createdOffer)));
	assert(expectedPrice == getPrice(createdOffer));

	destroyOffer(createdOffer);
}

void runAllOfferTests()
{
	CreateOffer_AnyOffer_OfferCreated();
	CreateOfferCopy_AnyOffer_OfferCopied();
	CopyOfferWithoutId_AnyOffer_OfferCopiedWithoutId();
	SetGetOffer_AnyOffer_SetAndGetOfferProperties();
}