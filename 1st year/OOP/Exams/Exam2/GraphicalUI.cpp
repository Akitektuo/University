#include "GraphicalUI.h"

std::string GraphicalUI::getString(QLineEdit* input)
{
	return input->text().toStdString();
}

int GraphicalUI::getInt(QLineEdit* input)
{
	return input->text().toInt();
}

QString GraphicalUI::getString(std::string string)
{
	return QString::fromStdString(string);
}

QString GraphicalUI::getString(int number)
{
	return QString::number(number);
}

void GraphicalUI::cleanLayout()
{
	delete mainLayout;
	qDeleteAll(children());
	mainLayout = new QVBoxLayout;
}

void GraphicalUI::updateLayout()
{
	setLayout(mainLayout);
}

void GraphicalUI::showMainMenu()
{
	cleanLayout();

	auto buttonAllCars = new QPushButton{ "See all cars" };
	auto buttonNumberOfCars = new QPushButton { "See the number of cars for a manufacturer" };

	connect(buttonAllCars, &QPushButton::clicked, this, &GraphicalUI::handleAllCarsClick);
	connect(buttonNumberOfCars, &QPushButton::clicked, this, &GraphicalUI::handleNumberOfCarsClick);

	mainLayout->addWidget(buttonAllCars);
	mainLayout->addWidget(buttonNumberOfCars);

	updateLayout();
}

void GraphicalUI::handleAllCarsClick()
{
	cleanLayout();

	auto cars = service.getAllCars();

	auto table = new QTableWidget{ (int) cars.size(), 4 };
	table->setHorizontalHeaderLabels({ "Manufacturer’s Name", "Model", "Year of Fabrication", "Color" });

	auto buttonback = new QPushButton{ "Back" };

	for (auto i = 0; i < cars.size(); i++) {
		table->setCellWidget(i, 0, new QLabel{ getString(cars[i].name) });
		table->setCellWidget(i, 1, new QLabel{ getString(cars[i].model) });
		table->setCellWidget(i, 2, new QLabel{ getString(cars[i].year) });
		table->setCellWidget(i, 3, new QLabel{ getString(cars[i].color) });
	}

	connect(buttonback, &QPushButton::clicked, [this]() {
		showMainMenu();
	});

	mainLayout->addWidget(table);
	mainLayout->addWidget(buttonback);

	updateLayout();
}

void GraphicalUI::handleNumberOfCarsClick()
{
	cleanLayout();

	auto inputManufacturer = new QLineEdit;

	auto buttonShow = new QPushButton{ "Show cars" };
	auto buttonBack = new QPushButton{ "Back" };

	inputManufacturer->setPlaceholderText("Manufacturer’s Name");

	connect(buttonShow, &QPushButton::clicked, [=]() {
		auto numberOfCars = service.getNumberOfCarsForManufacturer(getString(inputManufacturer));
		cleanLayout();

		auto labelNumberOfCars = new QLabel{ getString(numberOfCars) };
		auto buttonback = new QPushButton{ "Back" };

		connect(buttonback, &QPushButton::clicked, [this]() {
			handleNumberOfCarsClick();
		});

		mainLayout->addWidget(labelNumberOfCars);
		mainLayout->addWidget(buttonback);

		updateLayout();
	});
	connect(buttonBack, &QPushButton::clicked, [this]() {
		showMainMenu();
	});

	mainLayout->addWidget(inputManufacturer);

	mainLayout->addWidget(buttonShow);
	mainLayout->addWidget(buttonBack);

	updateLayout();
}

GraphicalUI::GraphicalUI()
{
	mainLayout = new QVBoxLayout;
	showMainMenu();
	show();
}
