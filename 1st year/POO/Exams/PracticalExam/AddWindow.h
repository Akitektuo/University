#pragma once

#include <QDialog>
#include "Domain.h"
#include "ui_AddWindow.h"

class AddWindow : public QDialog
{
	Q_OBJECT

public:
	AddWindow(Star& star, QWidget *parent = Q_NULLPTR);
	~AddWindow();

private:
	Ui::AddWindow ui;
	Star& star;

	void setupClicks();

	void onClickCancel();
	void onClickAdd();
};
