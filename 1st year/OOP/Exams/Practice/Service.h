#pragma once

#include "Repository.h"
#include "Observer.h"

class Service : public Subject
{
private:
	Repository repository;

public:
	Service(std::string teachersFileName = "", std::string studentsFileName = "");

	std::vector<std::string> getTeachers();

	std::vector<Student> getCoordinatedStudentsBy(std::string teacher);

	std::vector<Student> searchStudents(std::string search);

	bool addCoordinator(std::string teacher, Student toStudent);

	void editStudent(Student student);
};

