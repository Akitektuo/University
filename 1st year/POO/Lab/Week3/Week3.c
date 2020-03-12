#include "ConsoleUI.h"
#include "TestManager.h"
#include <crtdbg.h>

int main()
{
    runAllTests();
    startConsoleUI();

    _CrtDumpMemoryLeaks();
    return 0;
}
