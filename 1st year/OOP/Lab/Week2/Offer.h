#pragma once

typedef struct
{
	int id;
	char* type;
	char* destination;
	int price;
} Offer;

Offer* createOffer(int id, char* type, char* destination, int price);

int getId(Offer* offer);

void setType(Offer* offer, char* type);

char* getType(Offer* offer);

void setDestination(Offer* offer, char* destination);

char* getDestination(Offer* offer);

void setPrice(Offer* offer, int price);

int getPrice(Offer* offer);

void destroyOffer(Offer* offer);
