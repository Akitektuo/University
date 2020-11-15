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

void AddTrenchCoat_InvalidTrenchCoat_ReturnsFalse()
{
	Service service;

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");

	assert(!service.addTrenchCoat("abc", "jkl", 200, "mno"));
}

void AddTrenchCoat_ValidTrenchCoatWithoutPermissions_ReturnsFalse()
{
	Service service;

	service.setMode('B');

	assert(!service.addTrenchCoat("abc", "def", 100, "ghi"));
}

void AddTrenchCoat_ValidTrenchCoatWithPermissions_ReturnsTrue()
{
	Service service;

	service.setMode('A');

	assert(service.addTrenchCoat("abc", "def", 100, "ghi"));
}

void UpdateTrenchCoat_InvalidTrenchCoat_ReturnsFalse()
{
	Service service;

	service.setMode('A');

	assert(!service.updateTrenchCoat("abc", "def", 100, "ghi"));
}

void UpdateTrenchCoat_ValidTrenchCoatWithoutPermissions_ReturnsFalse()
{
	Service service;

	service.setMode('B');
	service.addTrenchCoat("abc", "def", 100, "ghi");

	assert(!service.updateTrenchCoat("abc", "jkl", 200, "mno"));
}

void UpdateTrenchCoat_ValidTrenchCoatWithPermissions_ReturnsTrue()
{
	Service service;

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");

	assert(service.updateTrenchCoat("abc", "jkl", 200, "mno"));
}

void DeleteTrenchCoat_InvalidTrenchCoat_ReturnsFalse()
{
	Service service;

	service.setMode('A');

	assert(!service.deleteTrenchCoat("abc"));
}

void DeleteTrenchCoat_ValidTrenchCoatWithoutPermissions_ReturnsFalse()
{
	Service service;

	service.setMode('B');
	service.addTrenchCoat("abc", "def", 100, "ghi");

	assert(!service.deleteTrenchCoat("abc"));
}

void DeleteTrenchCoat_ValidTrenchCoatWithPermissions_ReturnsTrue()
{
	Service service;

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");

	assert(service.deleteTrenchCoat("abc"));
}

void GetListOfTrenchCoats_NoTrenchCoats_ReturnsEmptyArrayList()
{
	Service service;

	assert(service.getListOfTrenchCoats().isEmpty());
}

void GetListOfTrenchCoats_SomeTrenchCoatsWithoutPermissions_ReturnsArrayListOfTrenchCoats()
{
	Service service;

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");
	service.addTrenchCoat("jkl", "mno", 200, "pqr");
	service.addTrenchCoat("stu", "vqw", 300, "xya");
	service.setMode('B');

	assert(service.getListOfTrenchCoats().getSize() == 3);
}

void GetListOfTrenchCoats_SomeTrenchCoatsWithPermissions_ReturnsArrayListOfTrenchCoats()
{
	Service service;

	service.setMode('A');
	service.addTrenchCoat("abc", "def", 100, "ghi");
	service.addTrenchCoat("jkl", "mno", 200, "pqr");
	service.addTrenchCoat("stu", "vqw", 300, "xya");

	assert(service.getListOfTrenchCoats().getSize() == 3);
}

void runAllServiceTests()
{
	SetMode_InvalidMode_SetsDefaultMode();
	SetMode_AdminMode_SetsAdminMode();
	SetMode_UserMode_SetsUserMode();
	AddTrenchCoat_InvalidTrenchCoat_ReturnsFalse();
	AddTrenchCoat_ValidTrenchCoatWithoutPermissions_ReturnsFalse();
	AddTrenchCoat_ValidTrenchCoatWithPermissions_ReturnsTrue();
	UpdateTrenchCoat_InvalidTrenchCoat_ReturnsFalse();
	UpdateTrenchCoat_ValidTrenchCoatWithoutPermissions_ReturnsFalse();
	UpdateTrenchCoat_ValidTrenchCoatWithPermissions_ReturnsTrue();
}
