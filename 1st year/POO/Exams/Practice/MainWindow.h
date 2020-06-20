#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_MainWindow.h"
#include "EditWindow.h"
#include <string>
#include "Service.h"
#include <QMessageBox>

class MainWindow : public QMainWindow, public Observer
{
    Q_OBJECT

public:
    MainWindow(std::string teacherName, Service& service, QWidget *parent = Q_NULLPTR);

private:
    Ui::MainWindowClass ui;
    std::string teacherName;
    Service& service;
    std::vector<Student> tableStudentsData;
    std::vector<Student> tableTeacherData;

    void setupListeners();
    void setupTables();
    void updateTeacherTable();
    void updateStudentsTable();

    void onClickSet();
    void onClickEdit();

    void update() override;
};
