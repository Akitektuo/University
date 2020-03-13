#include "TestService.h"

void CreateService_SomeService_ServiceCreated()
{
	Service* testService = createService();

	assert(testService->repository != NULL);
	assert(testService->tracker != NULL);

	destroyService(testService);
}

void runAllServiceTests()
{
	CreateService_SomeService_ServiceCreated();
}
