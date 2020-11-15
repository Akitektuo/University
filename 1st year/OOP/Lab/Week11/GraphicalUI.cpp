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

	auto buttonMode = new QPushButton { "Change mode" };
	auto buttonAdd = new QPushButton { "Add a trench coat" };
	auto buttonUpdate = new QPushButton { "Update a trench coat" };
	auto buttonDelete = new QPushButton { "Delete a trench coat" };
	auto buttonList = new QPushButton { "List all trench coats" };
	auto buttonFilter = new QPushButton { "Filter the list of trench coats" };
	auto buttonNext = new QPushButton { "Show next recommendation" };
	auto buttonSave = new QPushButton { "Save a trench coat in the shopping list" };
	auto buttonShoppingList = new QPushButton { "See the shopping list" };
	auto buttonFiles = new QPushButton { "Set the path of the save files" };

	connect(buttonMode, &QPushButton::clicked, this, &GraphicalUI::handleModeClick);
	connect(buttonAdd, &QPushButton::clicked, this, &GraphicalUI::handleAddClick);
	connect(buttonUpdate, &QPushButton::clicked, this, &GraphicalUI::handleUpdateClick);
	connect(buttonDelete, &QPushButton::clicked, this, &GraphicalUI::handleDeleteClick);
	connect(buttonList, &QPushButton::clicked, this, &GraphicalUI::handleListClick);
	connect(buttonFilter, &QPushButton::clicked, this, &GraphicalUI::handleFilterClick);
	connect(buttonNext, &QPushButton::clicked, this, &GraphicalUI::handleNextClick);
	connect(buttonSave, &QPushButton::clicked, this, &GraphicalUI::handleSaveClick);
	connect(buttonShoppingList, &QPushButton::clicked, this, &GraphicalUI::handleShoppingListClick);
	connect(buttonFiles, &QPushButton::clicked, this, &GraphicalUI::handleFilesClick);

	mainLayout->addWidget(buttonMode);
	if (service.isAdmin)
	{
		mainLayout->addWidget(buttonAdd);
		mainLayout->addWidget(buttonUpdate);
		mainLayout->addWidget(buttonDelete);
		mainLayout->addWidget(buttonFiles);
	}
	mainLayout->addWidget(buttonList);
	mainLayout->addWidget(buttonFilter);
	mainLayout->addWidget(buttonNext);
	mainLayout->addWidget(buttonSave);
	mainLayout->addWidget(buttonShoppingList);

	updateLayout();
}

void GraphicalUI::handleModeClick()
{
	service.isAdmin = !service.isAdmin;
	showMainMenu();
}

void GraphicalUI::handleAddClick()
{
	cleanLayout();

	auto inputName = new QLineEdit;
	auto inputSize = new QLineEdit;
	auto inputPrice = new QLineEdit;
	auto inputImage = new QLineEdit;

	auto buttonAdd = new QPushButton { "Add trench coat" };
	auto buttonCancel = new QPushButton { "Cancel" };

	inputName->setPlaceholderText("Name");
	inputSize->setPlaceholderText("Size");
	inputPrice->setPlaceholderText("Price");
	inputImage->setPlaceholderText("Image URL");

	connect(buttonAdd, &QPushButton::clicked, [=]() {
		service.addTrenchCoat(getString(inputName), getString(inputSize), getInt(inputPrice), getString(inputImage));
		showMainMenu();
	});
	connect(buttonCancel, &QPushButton::clicked, this, [this]() {
		showMainMenu();
	});

	mainLayout->addWidget(inputName);
	mainLayout->addWidget(inputSize);
	mainLayout->addWidget(inputPrice);
	mainLayout->addWidget(inputImage);

	mainLayout->addWidget(buttonAdd);
	mainLayout->addWidget(buttonCancel);

	updateLayout();
}

void GraphicalUI::handleUpdateClick()
{
	cleanLayout();

	auto inputName = new QLineEdit;
	auto inputSize = new QLineEdit;
	auto inputPrice = new QLineEdit;
	auto inputImage = new QLineEdit;

	auto buttonAdd = new QPushButton { "Update trench coat" };
	auto buttonCancel = new QPushButton { "Cancel" };

	inputName->setPlaceholderText("Old Name");
	inputSize->setPlaceholderText("New Size");
	inputPrice->setPlaceholderText("New Price");
	inputImage->setPlaceholderText("New Image URL");

	connect(buttonAdd, &QPushButton::clicked, [=]() {
		service.updateTrenchCoat(getString(inputName), getString(inputSize), getInt(inputPrice), getString(inputImage));
		showMainMenu();
	});
	connect(buttonCancel, &QPushButton::clicked, [this]() {
		showMainMenu();
	});

	mainLayout->addWidget(inputName);
	mainLayout->addWidget(inputSize);
	mainLayout->addWidget(inputPrice);
	mainLayout->addWidget(inputImage);

	mainLayout->addWidget(buttonAdd);
	mainLayout->addWidget(buttonCancel);

	updateLayout();
}

void GraphicalUI::handleDeleteClick()
{
	cleanLayout();

	auto inputName = new QLineEdit;

	auto buttonAdd = new QPushButton { "Delete trench coat" };
	auto buttonCancel = new QPushButton { "Cancel" };

	inputName->setPlaceholderText("Existing Name");

	connect(buttonAdd, &QPushButton::clicked, [=]() {
		service.deleteTrenchCoat(getString(inputName));
		showMainMenu();
	});
	connect(buttonCancel, &QPushButton::clicked, [this]() {
		showMainMenu();
	});

	mainLayout->addWidget(inputName);

	mainLayout->addWidget(buttonAdd);
	mainLayout->addWidget(buttonCancel);

	updateLayout();
}

void GraphicalUI::handleListClick()
{
	cleanLayout();

	auto trenchCoats = service.getListOfTrenchCoats();

	auto table = new QTableWidget { trenchCoats.getSize(), 4 };
	table->setHorizontalHeaderLabels({"Name", "Size", "Price", "Image"});

	auto buttonback = new QPushButton { "Back" };

	trenchCoats.forEachIndexed([&](const TrenchCoat& trenchCoat, int index) {
		table->setCellWidget(index, 0, new QLabel { getString(trenchCoat.getName()) });
		table->setCellWidget(index, 1, new QLabel { getString(trenchCoat.size) });
		table->setCellWidget(index, 2, new QLabel { getString(trenchCoat.price) });
		table->setCellWidget(index, 3, new QLabel { getString(trenchCoat.image) });
	});
	connect(buttonback, &QPushButton::clicked, [this]() {
		showMainMenu();
	});

	mainLayout->addWidget(table);
	mainLayout->addWidget(buttonback);

	updateLayout();
}

void GraphicalUI::handleFilterClick()
{
	cleanLayout();

	auto inputSize = new QLineEdit;
	auto inputPrice = new QLineEdit;

	auto buttonFilter = new QPushButton { "Filter" };
	auto buttonBack = new QPushButton { "Back" };

	inputSize->setPlaceholderText("Wanted Size");
	inputPrice->setPlaceholderText("Wanted maximum Price");

	connect(buttonFilter, &QPushButton::clicked, [=]() {
		auto trenchCoats = service.getListOfTrenchCoatsBySizeAndPrice(getString(inputSize), getInt(inputPrice));
		cleanLayout();

		auto table = new QTableWidget{ trenchCoats.getSize(), 4 };
		table->setHorizontalHeaderLabels({ "Name", "Size", "Price", "Image" });

		auto buttonback = new QPushButton{ "Back" };

		trenchCoats.forEachIndexed([&](const TrenchCoat& trenchCoat, int index) {
			table->setCellWidget(index, 0, new QLabel{ getString(trenchCoat.getName()) });
			table->setCellWidget(index, 1, new QLabel{ getString(trenchCoat.size) });
			table->setCellWidget(index, 2, new QLabel{ getString(trenchCoat.price) });
			table->setCellWidget(index, 3, new QLabel{ getString(trenchCoat.image) });
		});
		connect(buttonback, &QPushButton::clicked, [this]() {
			handleFilterClick();
		});

		mainLayout->addWidget(table);
		mainLayout->addWidget(buttonback);

		updateLayout();
	});
	connect(buttonBack, &QPushButton::clicked, [this]() {
		showMainMenu();
	});

	mainLayout->addWidget(inputSize);
	mainLayout->addWidget(inputPrice);

	mainLayout->addWidget(buttonFilter);
	mainLayout->addWidget(buttonBack);

	updateLayout();
}

void GraphicalUI::handleNextClick()
{
	cleanLayout();

	auto trenchCoat = service.getNextTrenchCoat();

	auto labelName = new QLabel { getString(trenchCoat.getName()) };
	auto labelSize = new QLabel { getString(trenchCoat.size) };
	auto labelPrice = new QLabel { getString(trenchCoat.price) };
	auto labelImage = new QLabel { getString(trenchCoat.image) };

	auto buttonNext = new QPushButton { "Next recommandation" };
	auto buttonBack = new QPushButton { "Back" };

	connect(buttonNext, &QPushButton::clicked, [this]() {
		handleNextClick();
	});
	connect(buttonBack, &QPushButton::clicked, [this]() {
		showMainMenu();
	});

	mainLayout->addWidget(labelName);
	mainLayout->addWidget(labelSize);
	mainLayout->addWidget(labelPrice);
	mainLayout->addWidget(labelImage);

	mainLayout->addWidget(buttonNext);
	mainLayout->addWidget(buttonBack);

	updateLayout();
}

void GraphicalUI::handleSaveClick()
{
	cleanLayout();

	auto inputName = new QLineEdit;

	auto buttonAdd = new QPushButton { "Add to the shopping cart" };
	auto buttonCancel = new QPushButton { "Cancel" };

	inputName->setPlaceholderText("Name of Trench Coat to save");

	connect(buttonAdd, &QPushButton::clicked, [=]() {
		service.saveTrenchCoat(getString(inputName));
		showMainMenu();
		});
	connect(buttonCancel, &QPushButton::clicked, [this]() {
		showMainMenu();
	});

	mainLayout->addWidget(inputName);

	mainLayout->addWidget(buttonAdd);
	mainLayout->addWidget(buttonCancel);

	updateLayout();
}

void GraphicalUI::handleShoppingListClick()
{
	cleanLayout();

	auto trenchCoats = service.getShoppingListOfTrenchCoats();

	auto table = new QTableWidget { trenchCoats.getSize(), 4 };
	table->setHorizontalHeaderLabels({ "Name", "Size", "Price", "Image" });

	auto buttonback = new QPushButton { "Back" };

	trenchCoats.forEachIndexed([&](const TrenchCoat& trenchCoat, int index) {
		table->setCellWidget(index, 0, new QLabel{ getString(trenchCoat.getName()) });
		table->setCellWidget(index, 1, new QLabel{ getString(trenchCoat.size) });
		table->setCellWidget(index, 2, new QLabel{ getString(trenchCoat.price) });
		table->setCellWidget(index, 3, new QLabel{ getString(trenchCoat.image) });
	});
	connect(buttonback, &QPushButton::clicked, [this]() {
		showMainMenu();
	});

	mainLayout->addWidget(table);
	mainLayout->addWidget(buttonback);

	updateLayout();
}

void GraphicalUI::handleFilesClick()
{
	cleanLayout();

	auto inputFileAll = new QLineEdit;
	auto inputFileCart = new QLineEdit;

	auto buttonSet = new QPushButton { "Set paths" };
	auto buttonCancel = new QPushButton { "Cancel" };

	inputFileAll->setPlaceholderText("File for all Trench Coats");
	inputFileCart->setPlaceholderText("File for shopping cart");

	connect(buttonSet, &QPushButton::clicked, [=]() {
		service.setFileLocation(getString(inputFileAll));
		service.setShoppingCartLocation(getString(inputFileCart));
		showMainMenu();
	});
	connect(buttonCancel, &QPushButton::clicked, [this]() {
		showMainMenu();
	});

	mainLayout->addWidget(inputFileAll);
	mainLayout->addWidget(inputFileCart);

	mainLayout->addWidget(buttonSet);
	mainLayout->addWidget(buttonCancel);

	updateLayout();
}

GraphicalUI::GraphicalUI()
{
	mainLayout = new QVBoxLayout;
	showMainMenu();
	show();
}
