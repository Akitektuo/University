#include "MainWindow.h"

MainWindow::MainWindow(std::string teacherName, Service& service, QWidget* parent) : teacherName{ teacherName }, service{service}, QMainWindow(parent)
{
    ui.setupUi(this);
    setWindowTitle(QString::fromStdString(teacherName));
    setupListeners();
    setupTables();

    service.addObserver(this);
}

void MainWindow::setupListeners()
{
    connect(ui.editSearch, &QLineEdit::textChanged, this, &MainWindow::updateStudentsTable);
    connect(ui.buttonSet, &QPushButton::clicked, this, &MainWindow::onClickSet);
    connect(ui.buttonEdit, &QPushButton::clicked, this, &MainWindow::onClickEdit);
}

void MainWindow::setupTables()
{
    ui.tableTeacher->setColumnCount(6);
    ui.tableStudents->setColumnCount(6);

    QStringList labels{ "Id", "Name", "Email", "Year", "Thesis", "Teacher" };

    ui.tableTeacher->setHorizontalHeaderLabels(labels);
    ui.tableStudents->setHorizontalHeaderLabels(labels);

    ui.tableTeacher->setSelectionBehavior(QAbstractItemView::SelectRows);
    ui.tableStudents->setSelectionBehavior(QAbstractItemView::SelectRows);

    update();
}

void MainWindow::updateTeacherTable()
{
    ui.tableTeacher->clearContents();

    auto students = service.getCoordinatedStudentsBy(teacherName);
    this->tableTeacherData = students;

    ui.tableTeacher->setRowCount(students.size());

    int index = 0;
    for (const auto& student : students)
    {
        ui.tableTeacher->setCellWidget(index, 0, new QLabel{ QString::fromStdString(student.getId()) });
        ui.tableTeacher->setCellWidget(index, 1, new QLabel{ QString::fromStdString(student.name) });
        ui.tableTeacher->setCellWidget(index, 2, new QLabel{ QString::fromStdString(student.email) });
        ui.tableTeacher->setCellWidget(index, 3, new QLabel{ QString::number(student.year) });
        ui.tableTeacher->setCellWidget(index, 4, new QLabel{ QString::fromStdString(student.thesis) });
        ui.tableTeacher->setCellWidget(index++, 5, new QLabel{ QString::fromStdString(student.teacher) });
    }
}

void MainWindow::updateStudentsTable()
{
    auto search = ui.editSearch->text().toStdString();
    ui.tableStudents->clearContents();

    auto students = service.searchStudents(search);
    this->tableStudentsData = students;

    ui.tableStudents->setRowCount(students.size());

    int index = 0;
    for (const auto& student : students)
    {
        ui.tableStudents->setCellWidget(index, 0, new QLabel{ QString::fromStdString(student.getId()) });
        ui.tableStudents->setCellWidget(index, 1, new QLabel{ QString::fromStdString(student.name) });
        ui.tableStudents->setCellWidget(index, 2, new QLabel{ QString::fromStdString(student.email) });
        ui.tableStudents->setCellWidget(index, 3, new QLabel{ QString::number(student.year) });
        ui.tableStudents->setCellWidget(index, 4, new QLabel{ QString::fromStdString(student.thesis) });
        ui.tableStudents->setCellWidget(index++, 5, new QLabel{ QString::fromStdString(student.teacher) });
    }
}

void MainWindow::onClickSet()
{
    auto indexes = ui.tableStudents->selectionModel()->selectedRows();
    
    for (const auto& index : indexes)
    {
        if (index.row())
        {
            auto student = tableStudentsData[index.row()];
            if (!service.addCoordinator(teacherName, student))
            {
                QMessageBox* error = new QMessageBox;
                error->setText("Selected student already has a teacher!");
                error->show();
            }
        }
    }

    update();
}

void MainWindow::onClickEdit()
{
    auto indexes = ui.tableTeacher->selectionModel()->selectedRows();

    for (const auto& index : indexes)
    {
        if (index.row())
        {
            auto student = tableTeacherData[index.row()];
            int result;
            EditWindow editWindow{ student };
            result = editWindow.exec();
            if (result == QDialog::Accepted)
            {
                service.editStudent(student);
                update();
            }
        }
    }
}

void MainWindow::update()
{
    updateTeacherTable();
    updateStudentsTable();
}
