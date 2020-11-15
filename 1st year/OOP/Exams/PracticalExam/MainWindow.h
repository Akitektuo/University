#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_MainWindow.h"
#include "AddWindow.h"
#include <string>
#include "Service.h"
#include <QMessageBox>
#include <QLabel>

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(Astronomer astronomer, Service& service, QWidget *parent = Q_NULLPTR);

private:
    Ui::MainWindowClass ui;
    Astronomer astronomer;
    Service& service;

    void setupListeners();
    void setupTable();
    void updateTable();

    void onClickAdd();
};
