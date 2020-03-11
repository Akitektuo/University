#include "ConsoleUI.h"
#include <crtdbg.h>

int main()
{
    startConsoleUI();

    _CrtDumpMemoryLeaks();
    return 0;
}
