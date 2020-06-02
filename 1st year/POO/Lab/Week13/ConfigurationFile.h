#pragma once

#include <string>
#include "Configuration.h"
#include "ArrayList.h"
#include <fstream>
#include <sstream>

class ConfigurationFile
{
private:
	const std::string CONFIG_FILE = "config.txt";
	const std::string DELIMITER = "=";

	const int POSITION_KEY = 0;
	const int POSITION_VALUE = 1;

	ArrayList<Configuration> configurations;

	ArrayList<std::string> split(std::string line, std::string delimiter) const;
	void readConfigurationFile();

public:
	const std::string KEY_FILE_LOCATION = "fileLocation";
	const std::string KEY_MYLIST_LOCATION = "mylistLocation";

	ConfigurationFile();

	std::string getStringValue(std::string key);
};

