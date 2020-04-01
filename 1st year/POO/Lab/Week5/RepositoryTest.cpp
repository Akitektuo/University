#include "RepositoryTest.h"

void Add_ValidTrenchCoat_ReturnsTrue()
{
	Repository repository;

	assert(repository.add({ "abc", "def", 100, "ghi" }));
}

void Add_DuplicateTrenchCoat_ReturnsFalse()
{
	Repository repository;

	repository.add({ "abc", "def", 100, "ghi" });

	assert(!repository.add({ "abc", "def", 100, "ghi" }));
}

void Update_ValidTrenchCoat_ReturnsTrue()
{
	Repository repository;

	repository.add({ "abc", "def", 100, "ghi" });

	assert(repository.update({ "abc", "jkl", 200, "mno" }));
}

void Update_NotExistingTrenchCoat_ReturnsFalse()
{
	Repository repository;

	assert(!repository.update({ "abc", "jkl", 200, "mno" }));
}

void Remove_ValidTrenchCoat_ReturnsTrue()
{
	Repository repository;

	repository.add({ "abc", "def", 100, "ghi" });

	assert(repository.remove("abc"));
}

void Remove_NotExistingTrenchCoat_ReturnsFalse()
{
	Repository repository;

	assert(!repository.remove("abc"));
}

void GetTrenchCoatsAsArrayList_EmptyRepository_ReturnsEmptyArrayList()
{
	Repository repository;

	assert(repository.getTrenchCoatsAsArrayList().isEmpty());
}

void GetTrenchCoatsAsArrayList_RepositoryWithSomeTrenchCoats_ReturnsArrayListOfTrenchCoats()
{
	Repository repository;

	repository.add({ "abc", "def", 100, "ghi" });
	repository.add({ "jkl", "mno", 200, "pqr" });
	repository.add({ "stu", "vwx", 100, "yza" });

	assert(repository.getTrenchCoatsAsArrayList().getSize() == 3);
}

void runAllRepositoryTests()
{
	Add_ValidTrenchCoat_ReturnsTrue();
	Add_DuplicateTrenchCoat_ReturnsFalse();
	Update_ValidTrenchCoat_ReturnsTrue();
	Update_NotExistingTrenchCoat_ReturnsFalse();
	Remove_ValidTrenchCoat_ReturnsTrue();
	Remove_NotExistingTrenchCoat_ReturnsFalse();
	GetTrenchCoatsAsArrayList_EmptyRepository_ReturnsEmptyArrayList();
	GetTrenchCoatsAsArrayList_RepositoryWithSomeTrenchCoats_ReturnsArrayListOfTrenchCoats();
}
