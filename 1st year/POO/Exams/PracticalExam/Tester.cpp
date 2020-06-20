#include "Tester.h"

void AddStar_WhenGivenDefaultValues_ThrowsException()
{
	// Arrange
	Service service{ "testAstronomers.txt", "testStars.txt" };
	Star startToAdd;
	Astronomer astronomer{ "Seeker", "abc" };

	// Act
	try
	{
		service.addStar(startToAdd, astronomer);

		// Assert
		assert(false);
	}
	catch (...) {}
}

void AddStar_WhenNameIsEmpty_ThrowsException()
{
	// Arrange
	Service service{ "testAstronomers.txt", "testStars.txt" };
	Star startToAdd{ "", "", 12, 34, 56 };
	Astronomer astronomer{ "Seeker", "abc" };

	// Act
	try
	{
		service.addStar(startToAdd, astronomer);

		// Assert
		assert(false);
	}
	catch (...) {}
}

void AddStar_WhenNameAlreadyExists_ThrowsException()
{
	// Arrange
	Service service{ "testAstronomers.txt", "testStars.txt" };
	Star startToAdd{ "The Sun", "", 12, 34, 56 };
	Astronomer astronomer{ "Seeker", "abc" };

	// Act
	try
	{
		service.addStar(startToAdd, astronomer);

		// Assert
		assert(false);
	}
	catch (...) {}
}

void AddStar_WhenDiameterIsNegative_ThrowsException()
{
	// Arrange
	Service service{ "testAstronomers.txt", "testStars.txt" };
	Star startToAdd{ "Moon", "", 12, 34, -1 };
	Astronomer astronomer{ "Seeker", "abc" };

	// Act
	try
	{
		service.addStar(startToAdd, astronomer);

		// Assert
		assert(false);
	}
	catch (...) {}
}

void AddStar_WhenDiameterIsZero_ThrowsException()
{
	// Arrange
	Service service{ "testAstronomers.txt", "testStars.txt" };
	Star startToAdd{ "Moon", "", 12, 34, 0 };
	Astronomer astronomer{ "Seeker", "abc" };

	// Act
	try
	{
		service.addStar(startToAdd, astronomer);

		// Assert
		assert(false);
	}
	catch (...) {}
}

void AddStar_WhenDataIsValid_AddedStarWithCorrectConstellation()
{
	// Arrange
	auto expectedName = "Moon";
	Service service{ "testAstronomers.txt", "testStars.txt" };
	Star startToAdd{ expectedName, "", 12, 34, 56 };
	Astronomer astronomer{ "Seeker", "abc" };

	// Act
	service.addStar(startToAdd, astronomer);
	auto stars = service.filterStars(astronomer, "", true);
	auto savedName = stars.back().name;

	// Assert
	assert(expectedName == savedName);

	// Cleanup
	service.removeStarByName(expectedName);
}

void testAll()
{
	AddStar_WhenGivenDefaultValues_ThrowsException();
	AddStar_WhenNameIsEmpty_ThrowsException();
	AddStar_WhenNameAlreadyExists_ThrowsException();
	AddStar_WhenDiameterIsNegative_ThrowsException();
	AddStar_WhenDiameterIsZero_ThrowsException();
	AddStar_WhenDataIsValid_AddedStarWithCorrectConstellation();
}
