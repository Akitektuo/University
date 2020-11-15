#pragma once
#include "Repository.h"
#include "ActionTracker.h"

#define OFFER_STRING_AVERAGE_MEMORY_SIZE 110
#define LIST_MAXIMUM_STRING_SIZE 1024

typedef struct
{
	Repository* repository;
	Tracker* tracker;
} Service;

Service* createService();

int addOfferService(Service* service, int id, char* type, char* destination, int price);

int updateOfferService(Service* service, int existingOfferId, char* type, char* destination, int price);

int deleteOfferService(Service* service, int existingOfferId);

Offer** getOffersListService(Service* service);

Offer** getOffersListByDestinationService(Service* service, char* destination);

Offer** getOffersListByMaxPriceService(Service* service, int maxPrice);

void undoAction(Service* service);

void redoAction(Service* service);

void destroyService(Service* service);