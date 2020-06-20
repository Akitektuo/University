#pragma once

#include <QDialog>
#include "ui_EditWindow.h"
#include "Domain.h"

class EditWindow : public QDialog
{
	Q_OBJECT

public:
	EditWindow(Student& student, QWidget *parent = Q_NULLPTR);
	~EditWindow();

private:
	Ui::EditWindow ui;
	Student& student;

	void setupData();
	void setupClicks();

	void onClickCancel();
	void onClickSave();
};
