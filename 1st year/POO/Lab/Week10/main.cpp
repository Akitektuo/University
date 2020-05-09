#include "GraphicalUI.h"

int main(int argc, char *argv[])
{
	QApplication application {argc, argv};
	GraphicalUI ui;
	return application.exec();
}
