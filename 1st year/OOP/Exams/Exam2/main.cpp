#include <QtWidgets/QApplication>

#include "GraphicalUI.h"

int main(int argc, char *argv[])
{
    QApplication application { argc, argv };
    GraphicalUI graphicalUI;
    return application.exec();
}
