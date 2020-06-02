#include "ConfigurationFile.h"

ArrayList<std::string> ConfigurationFile::split(std::string line, std::string delimiter) const
{
	ArrayList<std::string> stringData;

	size_t previous = 0, position = 0;
	do
	{
		position = line.find(delimiter, previous);
		if (position == std::string::npos)
		{
			position = line.length();
		}
		std::string token = line.substr(previous, position - previous);
		if (!token.empty())
		{
			stringData.add(token);
		}
		previous = position + delimiter.length();
	} while (position < line.length() && previous < line.length());

	return stringData;
}

void ConfigurationFile::readConfigurationFile()
{
	std::ifstream file(CONFIG_FILE);

	std::string line;
	while (std::getline(file, line))
	{
		auto data = split(line, DELIMITER);
		configurations.add({ data.get(POSITION_KEY), data.getSize() > POSITION_VALUE ? data.get(POSITION_VALUE) : "" });
	}

	file.close();
}

ConfigurationFile::ConfigurationFile()
{
	readConfigurationFile();
}

std::string ConfigurationFile::getStringValue(std::string key)
{
	return configurations.find([&](const Configuration& configuration) {
		return configuration.key == key;
	}).value;
}
