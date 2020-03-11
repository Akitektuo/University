#include "ConsoleUI.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

ConsoleUI* createConsoleUI()
{
	ConsoleUI* newConsoleUI = (ConsoleUI*) malloc(sizeof(ConsoleUI));

	if (newConsoleUI == NULL)
	{
		return NULL;
	}

	newConsoleUI->controller = createConsoleController();
	return newConsoleUI;
}

void destroyConsoleUI(ConsoleUI* consoleUI)
{
	destroyConsoleController(consoleUI->controller);
	free(consoleUI);
}

void startConsoleUI()
{
	char command[MAXIMUM_COMMAND_SIZE];
	char result[LIST_MAXIMUM_STRING_SIZE] = { '\0' };
	ConsoleUI* consoleUI = createConsoleUI();

	while (1)
	{
		fgets(command, 64, stdin);
		int result_code = handleCommand(consoleUI->controller, command, result);
		switch (result_code)
		{
			case RESULT_EXIT:
				destroyConsoleUI(consoleUI);
				return;
			case RESULT_ERROR:
				printf("No!\n");
				break;
			case RESULT_SUCCESSFUL:
				if (strlen(result))
				{
					printf("%s", result);
					result[0] = '\0';
				}
				break;
		}
	}
}
