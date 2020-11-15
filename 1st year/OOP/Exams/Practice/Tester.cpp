#include "Tester.h"

void AddCoordinator_WhenStudentHasACoordinator_TheCoordinatorIsNotAdded()
{
	// Arrange
	Service service{ "testTeachers.txt", "testStudents.txt" };

	// Act
	auto result = service.addCoordinator("teacher2", { "def", "cab", "Email2", 2020, "Title2", "teacher1" });

	// Assert
	assert(!result);
}

void AddCoordinator_WhenStudentHasNoCoordinator_TheCoordinatorIsAdded()
{
	// Arrange
	Service service{ "testTeachers.txt", "testStudents.txt" };

	// Act
	auto result = service.addCoordinator("teacher2", { "jkl", "Some Name", "Email4", 2022, "No title" });

	// Assert
	assert(result);
}

void SearchStudents_WhenSearchDoesNotMach_TheVectorIsEmpty()
{
	// Arrange
	Service service{ "testTeachers.txt", "testStudents.txt" };

	// Act
	auto result = service.searchStudents("mno");

	// Assert
	assert(result.empty());
}

void SearchStudents_WhenSearchingByValidId_TheVectorHasAStudent()
{
	// Arrange
	Service service{ "testTeachers.txt", "testStudents.txt" };

	// Act
	auto result = service.searchStudents("def");

	// Assert
	assert(result.size() == 1);
}

void SearchStudents_WhenSearchingByValidName_TheVectorHasAStudent()
{
	// Arrange
	Service service{ "testTeachers.txt", "testStudents.txt" };

	// Act
	auto result = service.searchStudents("abc");

	// Assert
	assert(result.size() == 2);
}

void testAll()
{
	AddCoordinator_WhenStudentHasACoordinator_TheCoordinatorIsNotAdded();
	AddCoordinator_WhenStudentHasNoCoordinator_TheCoordinatorIsAdded();
	SearchStudents_WhenSearchDoesNotMach_TheVectorIsEmpty();
	SearchStudents_WhenSearchingByValidId_TheVectorHasAStudent();
	SearchStudents_WhenSearchingByValidName_TheVectorHasAStudent();
}
