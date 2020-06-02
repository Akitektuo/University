#pragma once

#include "Service.h"
#include <QObject>
#include <QCoreApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QApplication>
#include <QLabel>
#include <QVBoxLayout>
#include <QLineEdit>
#include <QFileDialog>
#include <QPushButton>
#include <QTableWidget>

class GraphicalUI : public QWidget
{
	Q_OBJECT
private:
	Service service;
	QVBoxLayout* mainLayout;

	std::string getString(QLineEdit* input);
	int getInt(QLineEdit* input);

	QString getString(std::string string);
	QString getString(int number);

	void cleanLayout();
	void updateLayout();

	Q_INVOKABLE void showMainMenu();

	Q_INVOKABLE void handleAllCarsClick();
	Q_INVOKABLE void handleNumberOfCarsClick();

public:
	GraphicalUI();
};

