#include "TestRepository.h"

void CreateRepository_SomeRepository_RepositoryCreated()
{

}

void GetOfferAtIndex_InvalidIndex_ReturnsNull()
{
	Repository* testRepository = createRepository();

}

void GetOfferAtIndex_ValidIndex_ReturnsOffer()
{

}

void IsOfferInRepository_InvalidId_ReturnsFalse()
{

}

void IsOfferInRepository_ValidId_ReturnsTrue()
{

}

void GetOffer_InvalidId_ReturnsNull()
{

}

void GetOffer_ValidId_ReturnsOffer()
{

}

void AddOffer_ValidOffer_ReturnsTrue()
{

}

void AddOffer_ValidOffer_AddedToList()
{

}

void AddOffer_DuplicateOffer_ReturnsFalse()
{

}

void AddOffer_DuplicateOffer_NotAddedToList()
{

}

void RemoveOffer_InvalidId_ReturnsFalse()
{

}

void RemoveOffer_InvalidId_SameSize()
{

}

void RemoveOffer_ValidId_ReturnsTrue()
{

}

void RemoveOffer_ValidId_RemovedFromList()
{

}

void runAllRepositoryTests()
{
	GetOfferAtIndex_InvalidIndex_ReturnsNull();
	GetOfferAtIndex_ValidIndex_ReturnsOffer();
	IsOfferInRepository_InvalidId_ReturnsFalse();
	IsOfferInRepository_ValidId_ReturnsTrue();
	GetOffer_InvalidId_ReturnsNull();
	GetOffer_ValidId_ReturnsOffer();
	AddOffer_ValidOffer_ReturnsTrue();
	AddOffer_ValidOffer_AddedToList();
	AddOffer_DuplicateOffer_ReturnsFalse();
	AddOffer_DuplicateOffer_NotAddedToList();
	RemoveOffer_InvalidId_ReturnsFalse();
	RemoveOffer_InvalidId_SameSize();
	RemoveOffer_ValidId_ReturnsTrue();
	RemoveOffer_ValidId_RemovedFromList();
}
