#pragma once

#include "Controller.h"
#include <iostream>

class ConsoleUI
{
private:
	Controller controller;

	std::vector<std::string> split(std::string lineString, std::string delimiter);

	void displayError(std::string error);

public:
	void start();
};

