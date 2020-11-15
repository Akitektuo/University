#include "Surgery.h"

Surgery::Surgery(std::string hospitalName, int numberOfDoctors, int numberOfPatients)
{
	setHospitalName(hospitalName);
	setNumberOfDoctors(numberOfDoctors);
	setNumberOfPatients(numberOfPatients);
}

void Surgery::setNumberOfPatients(int numberOfPatients)
{
	this->numberOfPatients = numberOfPatients;
}

int Surgery::getNumberOfPatients()
{
	return numberOfPatients;
}

bool Surgery::isEfficient()
{
	return getNumberOfPatients() * 1.0 / getNumberOfDoctors() >= 2;
}

std::string Surgery::toString()
{
	return getHospitalName() + "\nSurgery\n" + std::to_string(getNumberOfDoctors()) + "\n" + std::to_string(getNumberOfPatients()) + "\n";
}
