#include "Week10.h"
#include <QtWidgets/QApplication>
#include <QtWidgets/QLabel>
#include <QVBoxLayout>
#include <QLineEdit>

int main(int argc, char *argv[])
{
	QApplication a(argc, argv);
	QWidget w;
	QVBoxLayout layout { &w };
	QLabel label { "Hello, world!" };
	QLineEdit lineEdit;
	layout.addWidget(&label);
	layout.addWidget(&lineEdit);
	w.show();
	w.close();
	w.show();
	return a.exec();
}
