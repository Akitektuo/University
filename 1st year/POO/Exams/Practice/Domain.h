#pragma once

#include <string>

class Student
{
private:
	std::string id;

public:
	std::string name;
	std::string email;
	int year;
	std::string thesis;
	std::string teacher;

	Student(std::string id, std::string name, std::string email, int year, std::string thesis, std::string teacher = "");

	std::string getId() const;
};
