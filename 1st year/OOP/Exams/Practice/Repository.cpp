#include "Repository.h"

std::vector<std::string> Repository::split(std::string line)
{
	std::vector<std::string> stringData;

	size_t previous = 0, position = 0;
	do
	{
		position = line.find(", ", previous);
		if (position == std::string::npos)
		{
			position = line.length();
		}
		std::string token = line.substr(previous, position - previous);
		if (!token.empty())
		{
			stringData.push_back(token);
		}
		previous = position + 2;
	} while (position < line.length() && previous < line.length());

	return stringData;
}

std::vector<Student> Repository::readStudentsFromFile()
{
	std::ifstream file(studentsFileName);
	std::vector<Student> students;

	std::string line;
	while (std::getline(file, line))
	{
		auto data = split(line);
		students.push_back({ data[0], data[1], data[2], std::stoi(data[3]), data[4], data.size() == 6 ? data[5] : "" });
	}

	file.close();

	return students;
}

void Repository::saveStudentsToFile(std::vector<Student> students)
{
	std::ofstream file(studentsFileName);

	for (auto& student : students) {
		file << student.getId() << ", "
			<< student.name << ", "
			<< student.email << ", "
			<< student.year << ", "
			<< student.thesis << ", "
			<< student.teacher << "\n";
	}

	file.close();
}

Repository::Repository(std::string teachersFileName, std::string studentsFileName) : teachersFileName{teachersFileName}, studentsFileName{studentsFileName}
{}

std::vector<std::string> Repository::getAllTeachers()
{
	std::ifstream file(teachersFileName);
	std::vector<std::string> teachers;

	std::string line;
	while (std::getline(file, line))
	{
		teachers.push_back(line);
	}

	file.close();

	return teachers;
}

std::vector<Student> Repository::getAllStudents()
{
    return readStudentsFromFile();
}

void Repository::editStudent(Student student)
{
	auto students = readStudentsFromFile();

	auto foundStudent = std::find_if(students.begin(), students.end(), [&](const Student& s) { return s.getId() == student.getId(); });

	if (foundStudent == students.end())
	{
		return;
	}

	foundStudent->email = student.email;
	foundStudent->thesis = student.thesis;
	foundStudent->teacher = student.teacher;

	saveStudentsToFile(students);
}
