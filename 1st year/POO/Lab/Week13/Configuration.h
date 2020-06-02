#pragma once

#include <string>

class Configuration
{
public:
	std::string key;
	std::string value;

	Configuration(std::string key = "", std::string value = "");
};

