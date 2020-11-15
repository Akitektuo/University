#pragma once

#include "Service.h"
#include "TrenchCoatTableView.h"
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
#include <QShortcut>

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

	void setupUndoRedoShortcuts();

	Q_INVOKABLE void showMainMenu();

	Q_INVOKABLE void handleModeClick();
	Q_INVOKABLE void handleAddClick();
	Q_INVOKABLE void handleUpdateClick();
	Q_INVOKABLE void handleDeleteClick();
	Q_INVOKABLE void handleListClick();
	Q_INVOKABLE void handleFilterClick();
	Q_INVOKABLE void handleNextClick();
	Q_INVOKABLE void handleSaveClick();
	Q_INVOKABLE void handleShoppingListClick();
	Q_INVOKABLE void handleFilesClick();
	Q_INVOKABLE void handleUndoClick();
	Q_INVOKABLE void handleRedoClick();

public:
	GraphicalUI();
};

