#include "TestService.h"

void CreateService_SomeService_ServiceCreated()
{
	Service* testService = createService();

	assert(testService->repository != NULL);
	assert(testService->tracker != NULL);

	destroyService(testService);
}

void AddOfferService_ValidOffer_ReturnsTrue()
{
	Service* testService = createService();

	assert(addOfferService(testService, 1, "abc", "def", 100));

	destroyService(testService);
}

void AddOfferService_DuplicateOffer_ReturnsFalse()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 100);

	assert(!addOfferService(testService, 1, "def", "abc", 200));

	destroyService(testService);
}

void UpdateOfferService_InvalidId_ReturnsFalse()
{
	Service* testService = createService();

	assert(!updateOfferService(testService, 1, "abc", "def", 100));

	destroyService(testService);
}

void UpdateOfferService_ValidId_ReturnsTrue()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 100);

	assert(updateOfferService(testService, 1, "def", "abc", 200));

	destroyService(testService);
}

void DeleteOfferService_InvalidId_ReturnsFalse()
{
	Service* testService = createService();

	assert(!deleteOfferService(testService, 1));

	destroyService(testService);
}

void DeleteOfferService_ValidId_ReturnsTrue()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 100);

	assert(deleteOfferService(testService, 1));

	destroyService(testService);
}

void GetOffersListService_ServiceWithNoOffers_ReturnsEmptyString()
{
	Service* testService = createService();

	Offer** offers = getOffersListService(testService);

	assert(offers[0] == NULL);

	destroyService(testService);
}

void GetOffersListService_ServiceWithOffers_ReturnsNotEmptyString()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 100);
	Offer** offers = getOffersListService(testService);

	assert(offers[0] != NULL);

	destroyService(testService);
}

void GetOffersListByDestinationService_ServiceWithNoOffers_ReturnsEmptyString()
{
	Service* testService = createService();

	Offer** offers = getOffersListByDestinationService(testService, "abc");

	assert(offers[0] == NULL);

	destroyService(testService);
}

void GetOffersListByDestinationService_ServiceWithOffersAndInvalidDestination_ReturnsEmptyString()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 100);
	Offer** offers = getOffersListByDestinationService(testService, "abc");

	assert(offers[0] == NULL);

	destroyService(testService);
}

void GetOffersListByDestinationService_ServiceWithOffersAndValidDestination_ReturnsNotEmptyString()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "abc", 100);
	Offer** offers = getOffersListByDestinationService(testService, "abc");

	assert(offers[0] != NULL);

	destroyService(testService);
}

void GetOffersListByMaxPriceService_ServiceWithNoOffers_ReturnsEmptyString()
{
	Service* testService = createService();

	Offer** offers = getOffersListByMaxPriceService(testService, 200);

	assert(offers[0] == NULL);

	destroyService(testService);
}

void GetOffersListByMaxPriceService_ServiceWithOffersAndInvalidMaxPrice_ReturnsEmptyString()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 200);
	Offer** offers = getOffersListByMaxPriceService(testService, 100);

	assert(offers[0] == NULL);

	destroyService(testService);
}

void GetOffersListByMaxPriceService_ServiceWithOffersAndValidMaxPrice_ReturnsNotEmptyString()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 100);
	Offer** offers = getOffersListByMaxPriceService(testService, 100);

	assert(offers[0] != NULL);

	destroyService(testService);
}

void UndoAction_ServiceWithNoOffers_NothingChanges()
{
	Service* testService = createService();

	undoAction(testService);
	Offer** offers = getOffersListService(testService);

	assert(offers[0] == NULL);

	destroyService(testService);
}

void UndoAction_ServiceWithAddedOffer_AddedOfferRemoved()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 100);
	undoAction(testService);
	Offer** offers = getOffersListService(testService);

	assert(offers[0] == NULL);

	destroyService(testService);
}

void UndoAction_ServiceWithEditedOffer_EditReverted()
{
	Service* testService = createService();

	char* expectedType = "abc";
	char* expectedDestination = "def";
	int expectedPrice = 100;

	addOfferService(testService, 1, expectedType, expectedDestination, expectedPrice);
	updateOfferService(testService, 1, "def", "abc", 200);
	undoAction(testService);
	Offer* offer = getOffersListService(testService)[0];

	assert(!strcmp(expectedType, getType(offer)));
	assert(!strcmp(expectedDestination, getDestination(offer)));
	assert(expectedPrice == getPrice(offer));

	destroyService(testService);
}

void UndoAction_ServiceWithDeletedOffer_RemovedOfferAdded()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 100);
	deleteOfferService(testService, 1);
	undoAction(testService);
	Offer** offers = getOffersListService(testService);

	assert(offers[0] != NULL);

	destroyService(testService);
}

void UndoAction_ServiceWithMultipleChanges_ServiceWithNoOffers()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 100);
	updateOfferService(testService, 1, "def", "abc", 200);
	deleteOfferService(testService, 1);
	undoAction(testService);
	undoAction(testService);
	undoAction(testService);
	undoAction(testService);
	undoAction(testService);
	undoAction(testService);
	Offer** offers = getOffersListService(testService);

	assert(offers[0] == NULL);

	destroyService(testService);
}

void RedoAction_ServiceWithNoOffers_NothingChanges()
{
	Service* testService = createService();

	redoAction(testService);
	Offer** offers = getOffersListService(testService);

	assert(offers[0] == NULL);

	destroyService(testService);
}

void RedoAction_ServiceWithAddedOfferByUndo_AddedOfferRemoved()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 100);
	deleteOfferService(testService, 1);
	undoAction(testService);
	redoAction(testService);
	Offer** offers = getOffersListService(testService);

	assert(offers[0] == NULL);

	destroyService(testService);
}

void RedoAction_ServiceWithEditedOfferByUndo_EditReverted()
{
	Service* testService = createService();

	char* expectedType = "abc";
	char* expectedDestination = "def";
	int expectedPrice = 100;

	addOfferService(testService, 1, "def", "abc", 200);
	updateOfferService(testService, 1, expectedType, expectedDestination, expectedPrice);
	undoAction(testService);
	redoAction(testService);
	Offer* offer = getOffersListService(testService)[0];

	assert(!strcmp(expectedType, getType(offer)));
	assert(!strcmp(expectedDestination, getDestination(offer)));
	assert(expectedPrice == getPrice(offer));

	destroyService(testService);
}

void RedoAction_ServiceWithDeletedOfferByUndo_RemovedOfferAdded()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 100);
	undoAction(testService);
	redoAction(testService);
	Offer** offers = getOffersListService(testService);

	assert(offers[0] != NULL);

	destroyService(testService);
}

void RedoAction_ServiceOperationAfterUndos_OperationOverridesRedos()
{
	Service* testService = createService();

	addOfferService(testService, 1, "abc", "def", 100);
	updateOfferService(testService, 1, "def", "abc", 200);
	deleteOfferService(testService, 1);
	undoAction(testService);
	undoAction(testService);
	undoAction(testService);
	addOfferService(testService, 1, "abc", "def", 100);
	redoAction(testService);
	Offer** offers = getOffersListService(testService);

	assert(offers[0] != NULL);

	destroyService(testService);
}

void runAllServiceTests()
{
	CreateService_SomeService_ServiceCreated();
	AddOfferService_ValidOffer_ReturnsTrue();
	AddOfferService_DuplicateOffer_ReturnsFalse();
	UpdateOfferService_InvalidId_ReturnsFalse();
	UpdateOfferService_ValidId_ReturnsTrue();
	DeleteOfferService_InvalidId_ReturnsFalse();
	DeleteOfferService_ValidId_ReturnsTrue();
	GetOffersListService_ServiceWithNoOffers_ReturnsEmptyString();
	GetOffersListService_ServiceWithOffers_ReturnsNotEmptyString();
	GetOffersListByDestinationService_ServiceWithNoOffers_ReturnsEmptyString();
	GetOffersListByDestinationService_ServiceWithOffersAndInvalidDestination_ReturnsEmptyString();
	GetOffersListByDestinationService_ServiceWithOffersAndValidDestination_ReturnsNotEmptyString();
	GetOffersListByMaxPriceService_ServiceWithNoOffers_ReturnsEmptyString();
	GetOffersListByMaxPriceService_ServiceWithOffersAndInvalidMaxPrice_ReturnsEmptyString();
	GetOffersListByMaxPriceService_ServiceWithOffersAndValidMaxPrice_ReturnsNotEmptyString();
	UndoAction_ServiceWithNoOffers_NothingChanges();
	UndoAction_ServiceWithAddedOffer_AddedOfferRemoved();
	UndoAction_ServiceWithEditedOffer_EditReverted();
	UndoAction_ServiceWithDeletedOffer_RemovedOfferAdded();
	UndoAction_ServiceWithMultipleChanges_ServiceWithNoOffers();
	RedoAction_ServiceWithNoOffers_NothingChanges();
	RedoAction_ServiceWithAddedOfferByUndo_AddedOfferRemoved();
	RedoAction_ServiceWithEditedOfferByUndo_EditReverted();
	RedoAction_ServiceWithDeletedOfferByUndo_RemovedOfferAdded();
	RedoAction_ServiceOperationAfterUndos_OperationOverridesRedos();
}
