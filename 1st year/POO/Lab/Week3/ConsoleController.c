#include "ConsoleController.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int getCommandType(char* commandKeyWord)
{
	if (!strcmp(commandKeyWord, "exit"))
	{
		return COMMAND_EXIT;
	}
	if (!strcmp(commandKeyWord, "add"))
	{
		return COMMAND_ADD;
	}
	if (!strcmp(commandKeyWord, "update"))
	{
		return COMMAND_UPDATE;
	}
	if (!strcmp(commandKeyWord, "delete"))
	{
		return COMMAND_DELETE;
	}
	if (!strcmp(commandKeyWord, "list"))
	{
		return COMMAND_LIST;
	}
	if (!strcmp(commandKeyWord, "undo"))
	{
		return COMMAND_UNDO;
	}
	if (!strcmp(commandKeyWord, "redo"))
	{
		return COMMAND_REDO;
	}
	return COMMAND_UNKNOWN;
}

void buildStringList(Offer** listOfOffers, char* stringResult)
{
	if (listOfOffers[0] == NULL)
	{
		return;
	}

	char* offerAsString = (char*) malloc(OFFER_STRING_AVERAGE_MEMORY_SIZE * sizeof(char));

	for (int i = 0; listOfOffers[i] != NULL; i++)
	{
		Offer* currentOffer = listOfOffers[i];

		snprintf(
			offerAsString,
			OFFER_STRING_AVERAGE_MEMORY_SIZE,
			"Id: %d\nType: %s\nDestination: %s\nPrice: %d\n\n",
			getId(currentOffer),
			getType(currentOffer),
			getDestination(currentOffer),
			getPrice(currentOffer)
		);

		if (offerAsString != NULL)
		{
			strcat_s(stringResult, LIST_MAXIMUM_STRING_SIZE, offerAsString);
		}
	}

	free(offerAsString);
	free(listOfOffers);
}

int isNumber(char* string)
{
	int stringSize = strlen(string);
	if (stringSize < 1 || stringSize > 10) {
		return 0;
	}

	for (int i = 0; i < stringSize; i++)
	{
		if (string[i] < '0' || string[i] > '9')
		{
			return 0;
		}
	}

	return 1;
}

ConsoleController* createConsoleController()
{
	ConsoleController* newConsoleController = (ConsoleController*) malloc(sizeof(ConsoleController));

	if (newConsoleController == NULL)
	{
		return NULL;
	}

	newConsoleController->service = createService();
	return newConsoleController;
}

int handleCommand(ConsoleController* consoleController, char* command, char* stringResult)
{
	Service* service = consoleController->service;

	// getting rid of '\n'
	strncpy_s(command, MAXIMUM_COMMAND_SIZE, command, strlen(command) - 1);

	char* token = NULL;
	char* parameter = strtok_s(command, ", ", &token);
	int phase = 0;
	int commandType = COMMAND_EXIT;

	int id = 0;
	char* type = NULL;
	char* destination = NULL;
	Offer** listOfOffers = NULL;

	while (parameter != NULL)
	{
		switch (phase++)
		{
			case 0:
				commandType = getCommandType(parameter);
				switch (commandType)
				{
					case COMMAND_UNKNOWN:
						return RESULT_ERROR;

					case COMMAND_EXIT:
						return RESULT_EXIT;

					case COMMAND_UNDO:
						undoAction(service);
						return RESULT_SUCCESSFUL;

					case COMMAND_REDO:
						redoAction(service);
						return RESULT_SUCCESSFUL;
				}
				break;
			case 1:
				switch (commandType)
				{
					case COMMAND_LIST:
						if (isNumber(parameter))
						{
							listOfOffers = getOffersListByMaxPriceService(service, atoi(parameter));
						}
						else
						{
							listOfOffers = getOffersListByDestinationService(service, parameter);
						}
						buildStringList(listOfOffers, stringResult);
						return RESULT_SUCCESSFUL;

					case COMMAND_DELETE:
						return !deleteOfferService(service, atoi(parameter));
				}
				id = atoi(parameter);
				break;
			case 2:
				type = parameter;
				break;
			case 3:
				destination = parameter;
				break;
			case 4:
				switch (commandType)
				{
					case COMMAND_ADD:
						return !addOfferService(service, id, type, destination, atoi(parameter));
					case COMMAND_UPDATE:
						return !updateOfferService(service, id, type, destination, atoi(parameter));
				}
				break;
		}
		parameter = strtok_s(NULL, ", ", &token);
	}

	if (phase == 1 && commandType == COMMAND_LIST)
	{
		listOfOffers = getOffersListService(service);
		buildStringList(listOfOffers, stringResult);
		return RESULT_SUCCESSFUL;
	}

	return RESULT_ERROR;
}

void destroyConsoleController(ConsoleController* consoleController)
{
	destroyService(consoleController->service);
	free(consoleController);
}
