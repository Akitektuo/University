#include "MainWindow.h"
#include "Service.h"
#include <QtWidgets/QApplication>
#include "Tester.h"

int main(int argc, char *argv[])
{
    testAll();

    Service service{ "teachers.txt", "students.txt" };
    auto teachers = service.getTeachers();
    QApplication a(argc, argv);
    for (const auto& teacher : teachers)
    {
        MainWindow* w = new MainWindow{ teacher, service };
        w->show();
    }
    
    return a.exec();
}
