#pragma once

#include <string>

class HospitalDepartment
{
private:
	std::string hospitalName;
	int numberOfDoctors;
public:
	virtual void setHospitalName(std::string hospitalName);
	virtual std::string getHospitalName();

	virtual void setNumberOfDoctors(int numberOfDoctors);
	virtual int getNumberOfDoctors();

	virtual bool isEfficient() = 0;
	virtual std::string toString() = 0;

	~HospitalDepartment() {};
};

