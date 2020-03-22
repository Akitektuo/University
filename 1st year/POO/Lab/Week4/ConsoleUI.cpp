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
		ArrayList<std::string> words = split(command);

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
			if (words.getSize() != 1)
			{
				displayError("Incorrect number of parameters!");
				continue;
			}
			service.getListOfTrenchCoats().forEach([](const TrenchCoat& trenchCoat) {
				std::cout << trenchCoat.toString() << "\n";
			});
		}
		else
		{
			displayError("Invalid command!");
		}
	}
}
