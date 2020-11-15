#include "Service.h"

Service::Service(std::string teachersFileName, std::string studentsFileName): repository{ teachersFileName, studentsFileName }
{}

std::vector<std::string> Service::getTeachers()
{
	return repository.getAllTeachers();
}

std::vector<Student> Service::getCoordinatedStudentsBy(std::string teacher)
{
	auto students = repository.getAllStudents();
	std::vector<Student> coordinatedStudents;

	std::sort(students.begin(), students.end(), [](const Student& studentA, const Student& studentB) {
		if (studentA.year == 2020 && studentB.year != 2020)
		{
			return true;
		}
		if (studentA.year != 2020 && studentB.year == 2020)
		{
			return false;
		}
		return studentA.year > studentB.year;
	});

	std::copy_if(students.begin(), students.end(), std::back_inserter(coordinatedStudents), [&](const Student& student) { return student.teacher == teacher; });

	return coordinatedStudents;
}

std::vector<Student> Service::searchStudents(std::string search)
{
	auto students = repository.getAllStudents();
	std::vector<Student> foundStudents;

	std::sort(students.begin(), students.end(), [](const Student& studentA, const Student& studentB) {
		if (studentA.year == 2020 && studentB.year != 2020)
		{
			return true;
		}
		if (studentA.year != 2020 && studentB.year == 2020)
		{
			return false;
		}
		return studentA.year > studentB.year;
		});

	std::copy_if(students.begin(), students.end(), std::back_inserter(foundStudents), [&](const Student& student) {
		return student.getId().find(search) != std::string::npos || student.name.find(search) != std::string::npos;
	});

	return foundStudents;
}

bool Service::addCoordinator(std::string teacher, Student toStudent)
{
	if (toStudent.teacher.empty())
	{
		toStudent.teacher = teacher;
		repository.editStudent(toStudent);

		notify();
		return true;
	}
	return false;	
}

void Service::editStudent(Student student)
{
	repository.editStudent(student);
	notify();
}
