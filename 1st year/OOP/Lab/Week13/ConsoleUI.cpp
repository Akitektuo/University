#include "ConsoleUI.h"

ArrayList<std::string> ConsoleUI::split(std::string string) const
{
	ArrayList<std::string> words;

	if (string.empty())
	{
		return words;
	}

	std::istringstream stringStream(string);
	std::string word;

	while (stringStream)
	{
		stringStream >> word;
		words.add(word.at(word.size() - 1) == ',' ? word.substr(0, word.size() - 1) : word);
	}

	words.removeAt(words.getSize() - 1);

	return words;
}

void ConsoleUI::displayError(std::string error) const
{
	std::cout << "Error: " << error << "\n";
}

void ConsoleUI::start()
{
	std::string command;

	while (true)
	{
		std::getline(std::cin, command);
		auto words = split(command);

		std::string commandType = words.getFirst();
		if (commandType == "exit")
		{
			return;
		}
		else if (commandType == "mode")
		{
			if (words.getSize() != 2)
			{
				displayError("Incorrect number of parameters!");
				continue;
			}
			service.setMode(words.get(1).at(0));
		}
		else if (commandType == "add")
		{
			if (words.getSize() != 5)
			{
				displayError("Incorrect number of parameters!");
				continue;
			}
			if (!service.addTrenchCoat(words.get(1), words.get(2), std::stoi(words.get(3)), words.get(4)))
			{
				displayError("Something went wrong!");
			}
		}
		else if (commandType == "update")
		{
			if (words.getSize() != 5)
			{
				displayError("Incorrect number of parameters!");
				continue;
			}
			if (!service.updateTrenchCoat(words.get(1), words.get(2), std::stoi(words.get(3)), words.get(4)))
			{
				displayError("Something went wrong!");
			}
		}
		else if (commandType == "delete")
		{
			if (words.getSize() != 2)
			{
				displayError("Incorrect number of parameters!");
				continue;
			}
			if (!service.deleteTrenchCoat(words.get(1)))
			{
				displayError("Something went wrong!");
			}
		}
		else if (commandType == "list")
		{
			if (words.getSize() == 1)
			{
				service.getListOfTrenchCoats().forEach([](const TrenchCoat& trenchCoat) {
					std::cout << trenchCoat.toString() << "\n";
				});
				continue;
			}
			if (words.getSize() == 3)
			{
				service.getListOfTrenchCoatsBySizeAndPrice(words.get(1), std::stoi(words.get(2))).forEach([](const TrenchCoat& trenchCoat) {
					std::cout << trenchCoat.toString() << "\n";
				});
				continue;
			}
			displayError("Incorrect number of parameters!");
		}
		else if (commandType == "next")
		{
			if (words.getSize() != 1)
			{
				displayError("Incorrect number of parameters!");
				continue;
			}
			try
			{
				std::cout << service.getNextTrenchCoat().toString() << "\n";
			}
			catch (const NoTrenchCoatException& exception)
			{
				displayError("No trench coats found!");
			}
		}
		else if (commandType == "save")
		{
			if (words.getSize() != 2)
			{
				displayError("Incorrect number of parameters!");
				continue;
			}
			if (!service.saveTrenchCoat(words.get(1)))
			{
				displayError("Something went wrong!");
			}
		}
		else if (commandType == "mylist")
		{
			if (words.getSize() != 1)
			{
				displayError("Incorrect number of parameters!");
				continue;
			}
			service.getShoppingListOfTrenchCoats().forEach([](const TrenchCoat& trenchCoat) {
				std::cout << trenchCoat.toString() << "\n";
				});
		}
		else if (commandType == "fileLocation")
		{
			if (words.getSize() < 2)
			{
				displayError("Incorrect number of parameters!");
				continue;
			}

			std::string filePath = words.get(1);
			words.forEachIndexed([&](const std::string& splittedPathBySpace, int index) {
				if (index < 2)
				{
					return;
				}
				filePath += " " + splittedPathBySpace;
			});

			service.setFileLocation(filePath);
		}
		else if (commandType == "mylistLocation")
		{
			if (words.getSize() < 2)
			{
				displayError("Incorrect number of parameters!");
				continue;
			}

			std::string filePath = words.get(1);
			words.forEachIndexed([&](const std::string& splittedPathBySpace, int index) {
				if (index < 2)
				{
					return;
				}
				filePath += " " + splittedPathBySpace;
			});

			service.setShoppingCartLocation(filePath);
		}
		else
		{
			displayError("Invalid command!");
		}
	}
}
