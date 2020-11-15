#include "MainWindow.h"
#include "Service.h"
#include <QtWidgets/QApplication>
#include "Tester.h"

/*
    I worte here what I solved so you don't have to check for the other features
    - Point 1 
    - Point 2
    - Point 3
    - Point 4
    - Point 7
    - Specification and testing of addStar function from service, can be found in Tester.cpp
*/

int main(int argc, char *argv[])
{
    testAll();

    Service service{ "astronomers.txt", "stars.txt" };
    auto astronomers = service.getAstronomers();

    QApplication a(argc, argv);

    for (const auto& astronomer : astronomers)
    {
        MainWindow* w = new MainWindow{ astronomer, service };
        w->show();
    }

    return a.exec();

    int code = a.exec();
    service.sortRepository();
    return code;
}
