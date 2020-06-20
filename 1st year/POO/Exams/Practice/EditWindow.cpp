#include "EditWindow.h"

EditWindow::EditWindow(Student& student, QWidget* parent) : student{ student }, QDialog(parent)
{
	ui.setupUi(this);
	setupClicks();
	setupData();
}

EditWindow::~EditWindow()
{
}

void EditWindow::setupData()
{
	ui.textId->setText(QString::fromStdString(student.getId()));
	ui.textName->setText(QString::fromStdString(student.name));
	ui.editEmail->setText(QString::fromStdString(student.email));
	ui.textYear->setText(QString::number(student.year));
	ui.editThesis->setText(QString::fromStdString(student.thesis == "No title" ? "" : student.thesis));
	ui.textCoordinator->setText(QString::fromStdString(student.teacher));
}

void EditWindow::setupClicks()
{
	connect(ui.buttonCancel, &QPushButton::clicked, this, &EditWindow::onClickCancel);
	connect(ui.buttonSave, &QPushButton::clicked, this, &EditWindow::onClickSave);
}

void EditWindow::onClickCancel()
{
	reject();
}

void EditWindow::onClickSave()
{
	auto email = ui.editEmail->text().toStdString();
	auto thesis = ui.editThesis->text().toStdString();

	student.email = email;
	student.thesis = thesis.empty() ? "No title" : thesis;

	accept();
}
