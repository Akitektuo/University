#pragma once

#include "Domain.h"
#include <vector>
#include <algorithm>
#include <fstream>
#include <sstream>

class Repository
{
private:
	std::string teachersFileName;
	std::string studentsFileName;

	std::vector<std::string> split(std::string line);
	std::vector<Student> readStudentsFromFile();
	void saveStudentsToFile(std::vector<Student> students);

public:
	Repository(std::string teachersFileName = "", std::string studentsFileName = "");

	std::vector<std::string> getAllTeachers();

	std::vector<Student> getAllStudents();

	void editStudent(Student student);
};

