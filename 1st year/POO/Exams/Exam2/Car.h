#pragma once

#include <string>

class Car
{
public:
	std::string name;
	std::string model;
	int year;
	std::string color;
public:
	Car(std::string name = "", std::string model = "", int year = 0, std::string color = "");
};

