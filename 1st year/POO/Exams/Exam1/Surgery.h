#pragma once
#include "HospitalDepartment.h"
class Surgery : public HospitalDepartment
{
private:
	int numberOfPatients;
public:
	Surgery(std::string hospitalName="", int numberOfDoctors=0, int numberOfPatients=0);

	void setNumberOfPatients(int numberOfPatients);
	int getNumberOfPatients();

	bool isEfficient();
	std::string toString();
};

