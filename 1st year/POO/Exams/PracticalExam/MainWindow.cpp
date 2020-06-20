#include "MainWindow.h"

MainWindow::MainWindow(Astronomer astronomer, Service& service, QWidget* parent) : astronomer{ astronomer }, service{service}, QMainWindow(parent)
{
    ui.setupUi(this);
    setWindowTitle(QString::fromStdString(astronomer.getName()));
    setupListeners();
    setupTable();
}

void MainWindow::setupListeners()
{
    connect(ui.checkBox, &QCheckBox::clicked, this, &MainWindow::updateTable);
    connect(ui.editSearch, &QLineEdit::textChanged, this, &MainWindow::updateTable);
    connect(ui.buttonAdd, &QPushButton::clicked, this, &MainWindow::onClickAdd);
}

void MainWindow::setupTable()
{
    ui.tableStars->setColumnCount(5);
    ui.tableStars->setHorizontalHeaderLabels({ "Name", "Constellation", "Right Ascension", "Declination", "Diameter" });

    updateTable();
}

void MainWindow::updateTable()
{
    ui.tableStars->clearContents();
    auto sameConstellation = ui.checkBox->isChecked();
    auto search = ui.editSearch->text().toStdString();


    auto stars = service.filterStars(astronomer, search, sameConstellation);

    ui.tableStars->setRowCount(stars.size());

    int index = 0;
    for (const auto& star : stars)
    {
        ui.tableStars->setCellWidget(index, 0, new QLabel{ QString::fromStdString(star.name) });
        ui.tableStars->setCellWidget(index, 1, new QLabel{ QString::fromStdString(star.constellation) });
        ui.tableStars->setCellWidget(index, 2, new QLabel{ QString::number(star.ascension) });
        ui.tableStars->setCellWidget(index, 3, new QLabel{ QString::number(star.declination) });
        ui.tableStars->setCellWidget(index++, 4, new QLabel{ QString::number(star.diameter) });
    }
}

void MainWindow::onClickAdd()
{
    Star newStar;
    AddWindow addWindow{ newStar };
    int result = addWindow.exec();
    if (result != QDialog::Accepted)
    {
        return;
    }
    try
    {
        service.addStar(newStar, astronomer);
        updateTable();
    }
    catch (...)
    {
        QMessageBox* error = new QMessageBox;
        error->setText("Error on adding the star!");
        error->show();
    }
}
