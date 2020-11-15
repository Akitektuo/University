#include "RepositoryTest.h"

void Add_NoFileGiven_ReturnsFalse()
{
	FileRepository repository;

	assert(!repository.add({ "abc", "def", 100, "ghi" }));
}

void Add_ValidTrenchCoat_ReturnsTrue()
{
	FileRepository repository;
	repository.addFilePath(REPOSITORY_TEST_FILE_NAME);

	assert(repository.add({ "abc", "def", 100, "ghi" }));

	std::remove(REPOSITORY_TEST_FILE_NAME);
}

void Add_DuplicateTrenchCoat_ReturnsFalse()
{
	FileRepository repository;
	repository.addFilePath(REPOSITORY_TEST_FILE_NAME);

	repository.add({ "abc", "def", 100, "ghi" });

	assert(!repository.add({ "abc", "def", 100, "ghi" }));

	std::remove(REPOSITORY_TEST_FILE_NAME);
}

void Update_NoFileGiven_ReturnsFalse()
{
	FileRepository repository;

	assert(!repository.update({ "abc", "def", 100, "ghi" }));
}

void Update_ValidTrenchCoat_ReturnsTrue()
{
	FileRepository repository;
	repository.addFilePath(REPOSITORY_TEST_FILE_NAME);

	repository.add({ "abc", "def", 100, "ghi" });

	assert(repository.update({ "abc", "jkl", 200, "mno" }));

	std::remove(REPOSITORY_TEST_FILE_NAME);
}

void Update_NotExistingTrenchCoat_ReturnsFalse()
{
	FileRepository repository;
	repository.addFilePath(REPOSITORY_TEST_FILE_NAME);

	assert(!repository.update({ "abc", "jkl", 200, "mno" }));

	std::remove(REPOSITORY_TEST_FILE_NAME);
}

void Remove_NoFileGiven_ReturnsFalse()
{
	FileRepository repository;

	assert(!repository.remove("abc"));
}

void Remove_ValidTrenchCoat_ReturnsTrue()
{
	FileRepository repository;
	repository.addFilePath(REPOSITORY_TEST_FILE_NAME);

	repository.add({ "abc", "def", 100, "ghi" });

	assert(repository.remove("abc"));

	std::remove(REPOSITORY_TEST_FILE_NAME);
}

void Remove_NotExistingTrenchCoat_ReturnsFalse()
{
	FileRepository repository;
	repository.addFilePath(REPOSITORY_TEST_FILE_NAME);

	assert(!repository.remove("abc"));

	std::remove(REPOSITORY_TEST_FILE_NAME);
}

void GetTrenchCoatsAsArrayList_NoFileGiven_ReturnsEmptyList()
{
	FileRepository repository;

	assert(repository.getTrenchCoatsAsArrayList().isEmpty());
}

void GetTrenchCoatsAsArrayList_EmptyRepository_ReturnsEmptyArrayList()
{
	FileRepository repository;
	repository.addFilePath(REPOSITORY_TEST_FILE_NAME);

	assert(repository.getTrenchCoatsAsArrayList().isEmpty());

	std::remove(REPOSITORY_TEST_FILE_NAME);
}

void GetTrenchCoatsAsArrayList_RepositoryWithSomeTrenchCoats_ReturnsArrayListOfTrenchCoats()
{
	FileRepository repository;
	repository.addFilePath(REPOSITORY_TEST_FILE_NAME);

	repository.add({ "abc", "def", 100, "ghi" });
	repository.add({ "jkl", "mno", 200, "pqr" });
	repository.add({ "stu", "vwx", 100, "yza" });

	assert(repository.getTrenchCoatsAsArrayList().getSize() == 3);

	std::remove(REPOSITORY_TEST_FILE_NAME);
}

void runAllRepositoryTests()
{
	Add_NoFileGiven_ReturnsFalse();
	Add_ValidTrenchCoat_ReturnsTrue();
	Add_DuplicateTrenchCoat_ReturnsFalse();
	Update_NoFileGiven_ReturnsFalse();
	Update_ValidTrenchCoat_ReturnsTrue();
	Update_NotExistingTrenchCoat_ReturnsFalse();
	Remove_NoFileGiven_ReturnsFalse();
	Remove_ValidTrenchCoat_ReturnsTrue();
	Remove_NotExistingTrenchCoat_ReturnsFalse();
	GetTrenchCoatsAsArrayList_NoFileGiven_ReturnsEmptyList();
	GetTrenchCoatsAsArrayList_EmptyRepository_ReturnsEmptyArrayList();
	GetTrenchCoatsAsArrayList_RepositoryWithSomeTrenchCoats_ReturnsArrayListOfTrenchCoats();
}
