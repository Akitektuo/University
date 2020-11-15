#include "Controller.h"

void Controller::addDepartment(HospitalDepartment* hospitalDepartment)
{
	hospitalDepartments.push_back(hospitalDepartment);
}

std::vector<HospitalDepartment*> Controller::getAllDepartments()
{
	return hospitalDepartments;
}

std::vector<HospitalDepartment*> Controller::getAllEfficientDepartments()
{
	std::vector<HospitalDepartment*> filteredHospitalDepartments;
	for (auto hospitalDepartment : hospitalDepartments)
	{
		if (hospitalDepartment->isEfficient())
		{
			filteredHospitalDepartments.push_back(hospitalDepartment);
		}
	}
	return filteredHospitalDepartments;
}

Controller::~Controller()
{
	for (auto hospitalDepartment : hospitalDepartments)
	{
		delete hospitalDepartment;
	}
}
