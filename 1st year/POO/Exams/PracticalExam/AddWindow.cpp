#include "AddWindow.h"

AddWindow::AddWindow(Star& star, QWidget* parent) : star{ star }, QDialog(parent)
{
	ui.setupUi(this);
	setupClicks();
}

AddWindow::~AddWindow()
{
}

void AddWindow::setupClicks()
{
	connect(ui.buttonCancel, &QPushButton::clicked, this, &AddWindow::onClickCancel);
	connect(ui.buttonAdd, &QPushButton::clicked, this, &AddWindow::onClickAdd);
}

void AddWindow::onClickCancel()
{
	reject();
}

void AddWindow::onClickAdd()
{
	auto name = ui.editName->text().toStdString();
	auto ascension = ui.editAscension->text().toInt();
	auto declination = ui.editDeclination->text().toInt();
	auto diameter = ui.editDiameter->text().toInt();

	star.name = name;
	star.ascension = ascension;
	star.declination = declination;
	star.diameter = diameter;

	accept();
}
