#pragma once
#include "Service.h"

#define MAXIMUM_COMMAND_SIZE 64

#define COMMAND_UNKNOWN 0
#define COMMAND_ADD 1
#define COMMAND_UPDATE 2
#define COMMAND_DELETE 3
#define COMMAND_LIST 4
#define COMMAND_EXIT 5
#define COMMAND_UNDO 6
#define COMMAND_REDO 7

#define RESULT_SUCCESSFUL 0
#define RESULT_ERROR 1
#define RESULT_EXIT 2

typedef struct
{
	Service* service;
} ConsoleController;

ConsoleController* createConsoleController();

int handleCommand(ConsoleController* consoleController, char* command, char* stringResult);

void destroyConsoleController(ConsoleController* consoleController);