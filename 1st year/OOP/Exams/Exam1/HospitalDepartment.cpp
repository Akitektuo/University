#include "HospitalDepartment.h"

void HospitalDepartment::setHospitalName(std::string hospitalName)
{
	this->hospitalName = hospitalName;
}

std::string HospitalDepartment::getHospitalName()
{
	return hospitalName;
}

void HospitalDepartment::setNumberOfDoctors(int numberOfDoctors)
{
	this->numberOfDoctors = numberOfDoctors;
}

int HospitalDepartment::getNumberOfDoctors()
{
	return numberOfDoctors;
}
