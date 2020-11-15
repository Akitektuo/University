#include "TestRepository.h"

void CreateRepository_SomeRepository_RepositoryCreated()
{
	Repository* testRepository = createRepository();

	assert(testRepository->memorySize == 0);
	assert(testRepository->size == 0);
	assert(testRepository->offers == NULL);

	destroyRepository(testRepository);
}

void GetOfferAtIndex_InvalidIndex_ReturnsNull()
{
	Repository* testRepository = createRepository();

	assert(!getOfferAtIndex(testRepository, 0));

	destroyRepository(testRepository);
}

void GetOfferAtIndex_ValidIndex_ReturnsOffer()
{
	Repository* testRepository = createRepository();

	Offer* offerToAddToRepository = createOffer(1, "abc", "def", 100);
	addOffer(testRepository, offerToAddToRepository);

	assert(getId(getOfferAtIndex(testRepository, 0)) == getId(offerToAddToRepository));

	destroyRepository(testRepository);
}

void IsOfferInRepository_InvalidId_ReturnsFalse()
{
	Repository* testRepository = createRepository();

	assert(!isOfferInRepository(testRepository, 1));

	destroyRepository(testRepository);
}

void IsOfferInRepository_ValidId_ReturnsTrue()
{
	Repository* testRepository = createRepository();

	Offer* offerToAddToRepository = createOffer(1, "abc", "def", 100);
	addOffer(testRepository, offerToAddToRepository);

	assert(isOfferInRepository(testRepository, 1));

	destroyRepository(testRepository);
}

void GetOffer_InvalidId_ReturnsNull()
{
	Repository* testRepository = createRepository();

	assert(!getOffer(testRepository, 1));

	destroyRepository(testRepository);
}

void GetOffer_ValidId_ReturnsOffer()
{
	Repository* testRepository = createRepository();

	Offer* offerToAddToRepository = createOffer(1, "abc", "def", 100);
	addOffer(testRepository, offerToAddToRepository);

	assert(getId(getOffer(testRepository, 1)) == getId(offerToAddToRepository));

	destroyRepository(testRepository);
}

void AddOffer_ValidOffer_ReturnsTrue()
{
	Repository* testRepository = createRepository();

	Offer* offerToAddToRepository = createOffer(1, "abc", "def", 100);
	assert(addOffer(testRepository, offerToAddToRepository));

	destroyRepository(testRepository);
}

void AddOffer_ValidOffer_AddedToList()
{
	Repository* testRepository = createRepository();

	Offer* offerToAddToRepository = createOffer(1, "abc", "def", 100);
	addOffer(testRepository, offerToAddToRepository);

	assert(testRepository->size == 1);

	destroyRepository(testRepository);
}

void AddOffer_DuplicateOffer_ReturnsFalse()
{
	Repository* testRepository = createRepository();

	addOffer(testRepository, createOffer(1, "abc", "def", 100));
	Offer* offerDuplicateToAddToRepository = createOffer(1, "def", "abc", 200);

	assert(!addOffer(testRepository, offerDuplicateToAddToRepository));

	destroyRepository(testRepository);
}

void AddOffer_DuplicateOffer_NotAddedToList()
{
	Repository* testRepository = createRepository();

	addOffer(testRepository, createOffer(1, "abc", "def", 100));
	Offer* offerDuplicateToAddToRepository = createOffer(1, "def", "abc", 200);
	addOffer(testRepository, offerDuplicateToAddToRepository);

	assert(testRepository->size == 1);

	destroyRepository(testRepository);
}

void RemoveOffer_InvalidId_ReturnsFalse()
{
	Repository* testRepository = createRepository();

	assert(!removeOffer(testRepository, 1));

	destroyRepository(testRepository);
}

void RemoveOffer_InvalidId_SameSize()
{
	Repository* testRepository = createRepository();

	Offer* offerToAddToRepository = createOffer(1, "abc", "def", 100);
	addOffer(testRepository, offerToAddToRepository);
	removeOffer(testRepository, 2);

	assert(testRepository->size == 1);

	destroyRepository(testRepository);
}

void RemoveOffer_ValidId_ReturnsTrue()
{
	Repository* testRepository = createRepository();

	Offer* offerToAddToRepository = createOffer(1, "abc", "def", 100);
	addOffer(testRepository, offerToAddToRepository);

	assert(removeOffer(testRepository, 1));

	destroyRepository(testRepository);
}

void RemoveOffer_ValidId_RemovedFromList()
{
	Repository* testRepository = createRepository();

	Offer* offerToAddToRepository = createOffer(1, "abc", "def", 100);
	addOffer(testRepository, offerToAddToRepository);
	removeOffer(testRepository, 1);

	assert(testRepository->size == 0);

	destroyRepository(testRepository);
}

void runAllRepositoryTests()
{
	CreateRepository_SomeRepository_RepositoryCreated();
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
