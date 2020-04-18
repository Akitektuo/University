#include "ServiceTest.h"

void SetMode_InvalidMode_SetsDefaultMode()
{
	Service service;

	assert(!service.setMode(' '));
}

void SetMode_UserMode_SetsUserMode()
{
	Service service;

	assert(!service.setMode('B'));
}

void SetMode_AdminMode_SetsAdminMode()
{
	Service service;

	assert(service.setMode('A'));
}

void AddTrenchCoat_DuplicateTrenchCoat_ReturnsFalse()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");

	assert(!service.addTrenchCoat("abc", "jkl", 200, "mno"));

	std::remove(SERVICE_TEST_FILE_NAME);
}

void AddTrenchCoat_ValidTrenchCoatWithoutPermissions_ReturnsFalse()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('B');

	assert(!service.addTrenchCoat("abc", "def", 100, "ghi"));

	std::remove(SERVICE_TEST_FILE_NAME);
}

void AddTrenchCoat_ValidTrenchCoatWithPermissions_ReturnsTrue()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('A');

	assert(service.addTrenchCoat("abc", "def", 100, "ghi"));

	std::remove(SERVICE_TEST_FILE_NAME);
}

void UpdateTrenchCoat_NotExistingTrenchCoat_ReturnsFalse()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('A');

	assert(!service.updateTrenchCoat("abc", "def", 100, "ghi"));

	std::remove(SERVICE_TEST_FILE_NAME);
}

void UpdateTrenchCoat_ValidTrenchCoatWithoutPermissions_ReturnsFalse()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('B');
	service.addTrenchCoat("abc", "def", 100, "ghi");

	assert(!service.updateTrenchCoat("abc", "jkl", 200, "mno"));

	std::remove(SERVICE_TEST_FILE_NAME);
}

void UpdateTrenchCoat_ValidTrenchCoatWithPermissions_ReturnsTrue()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");

	assert(service.updateTrenchCoat("abc", "jkl", 200, "mno"));

	std::remove(SERVICE_TEST_FILE_NAME);
}

void DeleteTrenchCoat_NotExistingTrenchCoat_ReturnsFalse()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('A');

	assert(!service.deleteTrenchCoat("abc"));

	std::remove(SERVICE_TEST_FILE_NAME);
}

void DeleteTrenchCoat_ValidTrenchCoatWithoutPermissions_ReturnsFalse()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('B');
	service.addTrenchCoat("abc", "def", 100, "ghi");

	assert(!service.deleteTrenchCoat("abc"));

	std::remove(SERVICE_TEST_FILE_NAME);
}

void DeleteTrenchCoat_ValidTrenchCoatWithPermissions_ReturnsTrue()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");

	assert(service.deleteTrenchCoat("abc"));

	std::remove(SERVICE_TEST_FILE_NAME);
}

void GetListOfTrenchCoats_NoTrenchCoats_ReturnsEmptyArrayList()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	assert(service.getListOfTrenchCoats().isEmpty());

	std::remove(SERVICE_TEST_FILE_NAME);
}

void GetListOfTrenchCoats_SomeTrenchCoatsWithoutPermissions_ReturnsArrayListOfTrenchCoats()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");
	service.addTrenchCoat("jkl", "mno", 200, "pqr");
	service.addTrenchCoat("stu", "vqw", 300, "xya");
	service.setMode('B');

	assert(service.getListOfTrenchCoats().getSize() == 3);

	std::remove(SERVICE_TEST_FILE_NAME);
}

void GetListOfTrenchCoats_SomeTrenchCoatsWithPermissions_ReturnsArrayListOfTrenchCoats()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");
	service.addTrenchCoat("jkl", "mno", 200, "pqr");
	service.addTrenchCoat("stu", "vqw", 300, "xya");

	assert(service.getListOfTrenchCoats().getSize() == 3);

	std::remove(SERVICE_TEST_FILE_NAME);
}

void GetNextTrenchCoat_SomeTrenchCoat_ReturnsTrenchCoat()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");
	service.getNextTrenchCoat();

	assert(service.getNextTrenchCoat().getName() == "abc");

	std::remove(SERVICE_TEST_FILE_NAME);
}

void SaveTrenchCoat_InvalidTrenchCoatName_ReturnsFalse()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");

	assert(!service.saveTrenchCoat("def"));

	std::remove(SERVICE_TEST_FILE_NAME);
}

void SaveTrenchCoat_ValidTrenchCoatName_ReturnsTrue()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");

	assert(service.saveTrenchCoat("abc"));

	std::remove(SERVICE_TEST_FILE_NAME);
}

void GetListOfTrenchCoatsBySizeAndPrice_ListOfTrenchCoats_ReturnsAListWithOneTrenchCoatMatchingFilters()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);

	service.setMode('A');
	service.addTrenchCoat("trenchCoat0", "abc", 100, "ghi");
	service.addTrenchCoat("trenchCoat1", "abc", 50, "ghi");
	service.addTrenchCoat("trenchCoat2", "def", 100, "ghi");
	service.addTrenchCoat("trenchCoat3", "def", 50, "ghi");

	assert(service.getListOfTrenchCoatsBySizeAndPrice("def", 75).getFirst().getName() == "trenchCoat3");

	std::remove(SERVICE_TEST_FILE_NAME);
}

void GetShoppingListOfTrenchCoats_EmptyListOfTrenchCoats_ReturnsAnEmptyList()
{
	Service service;
	service.setShoppingCartLocation(SERVICE_TEST_CART_FILE_NAME);

	assert(service.getShoppingListOfTrenchCoats().isEmpty());

	std::remove(SERVICE_TEST_CART_FILE_NAME);
}

void GetShoppingListOfTrenchCoats_ListOfTrenchCoats_ReturnsList()
{
	Service service;
	service.setFileLocation(SERVICE_TEST_FILE_NAME);
	service.setShoppingCartLocation(SERVICE_TEST_CART_FILE_NAME);

	service.setMode('A');
	service.addTrenchCoat("trenchCoat0", "abc", 100, "ghi");
	service.addTrenchCoat("trenchCoat1", "abc", 50, "ghi");
	service.addTrenchCoat("trenchCoat2", "def", 100, "ghi");
	service.addTrenchCoat("trenchCoat3", "def", 50, "ghi");
	service.saveTrenchCoat("trenchCoat0");
	service.saveTrenchCoat("trenchCoat1");
	service.saveTrenchCoat("trenchCoat2");
	service.saveTrenchCoat("trenchCoat3");

	assert(service.getShoppingListOfTrenchCoats().getSize() == 4);

	std::remove(SERVICE_TEST_FILE_NAME);
	std::remove(SERVICE_TEST_CART_FILE_NAME);
}

void runAllServiceTests()
{
	SetMode_InvalidMode_SetsDefaultMode();
	SetMode_AdminMode_SetsAdminMode();
	SetMode_UserMode_SetsUserMode();
	AddTrenchCoat_DuplicateTrenchCoat_ReturnsFalse();
	AddTrenchCoat_ValidTrenchCoatWithoutPermissions_ReturnsFalse();
	AddTrenchCoat_ValidTrenchCoatWithPermissions_ReturnsTrue();
	UpdateTrenchCoat_NotExistingTrenchCoat_ReturnsFalse();
	UpdateTrenchCoat_ValidTrenchCoatWithoutPermissions_ReturnsFalse();
	UpdateTrenchCoat_ValidTrenchCoatWithPermissions_ReturnsTrue();
	DeleteTrenchCoat_NotExistingTrenchCoat_ReturnsFalse();
	DeleteTrenchCoat_ValidTrenchCoatWithoutPermissions_ReturnsFalse();
	DeleteTrenchCoat_ValidTrenchCoatWithPermissions_ReturnsTrue();
	GetListOfTrenchCoats_NoTrenchCoats_ReturnsEmptyArrayList();
	GetListOfTrenchCoats_SomeTrenchCoatsWithoutPermissions_ReturnsArrayListOfTrenchCoats();
	GetListOfTrenchCoats_SomeTrenchCoatsWithPermissions_ReturnsArrayListOfTrenchCoats();
	GetNextTrenchCoat_SomeTrenchCoat_ReturnsTrenchCoat();
	SaveTrenchCoat_InvalidTrenchCoatName_ReturnsFalse();
	SaveTrenchCoat_ValidTrenchCoatName_ReturnsTrue();
	GetListOfTrenchCoatsBySizeAndPrice_ListOfTrenchCoats_ReturnsAListWithOneTrenchCoatMatchingFilters();
	GetShoppingListOfTrenchCoats_EmptyListOfTrenchCoats_ReturnsAnEmptyList();
	GetShoppingListOfTrenchCoats_ListOfTrenchCoats_ReturnsList();
}
