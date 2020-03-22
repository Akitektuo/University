#pragma once

#include "Service.h"
#include <iostream>
#include <sstream>

class ConsoleUI
{
private:
	Service service;

	ArrayList<std::string> split(std::string string) const;

	void displayError(std::string error) const;

public:
	void start();
};

