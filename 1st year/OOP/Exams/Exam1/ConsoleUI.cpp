#include "ConsoleUI.h"

std::vector<std::string> ConsoleUI::split(std::string lineString, std::string delimiter)
{
	std::vector<std::string> stringData;

	size_t previous = 0, position = 0;
	do
	{
		position = lineString.find(delimiter, previous);
		if (position == std::string::npos)
		{
			position = lineString.length();
		}
		std::string token = lineString.substr(previous, position - previous);
		if (!token.empty())
		{
			stringData.push_back(token);
		}
		previous = position + delimiter.length();
	}
	while (position < lineString.length() && previous < lineString.length());

	return stringData;
}

void ConsoleUI::displayError(std::string error)
{
	std::cout << "Error: " << error << "\n";
}

void ConsoleUI::start()
{
	std::string command;

	while (true)
	{
		std::getline(std::cin, command);

		if (!command.rfind("exit", 0))
		{
			return;
		}
		else if (!command.rfind("add", 0))
		{
			auto separatedParams = split(command.substr(4), ", ");
			if (separatedParams.size() == 4)
			{
				controller.addDepartment(new Surgery{ separatedParams[0], stoi(separatedParams[2]), stoi(separatedParams[3]) });
				continue;
			}
			if (separatedParams.size() == 6)
			{
				controller.addDepartment(new NeonatalUnit{ separatedParams[0], stoi(separatedParams[2]), stoi(separatedParams[3]), stoi(separatedParams[4]), stod(separatedParams[5]) });
				continue;
			}
			displayError("Invalid number of parameters!");
		}
		else if (!command.rfind("list", 0))
		{
			auto departments = controller.getAllDepartments();
			for (auto department : departments) {
				std::cout << department->toString() << "\n";
			}
		}
		else if (!command.rfind("list efficient", 0))
		{
			auto departments = controller.getAllEfficientDepartments();
			for (auto department : departments) {
				std::cout << department->toString() << "\n";
			}
		}
		else
		{
			displayError("Invalid command!");
		}
	}
}
