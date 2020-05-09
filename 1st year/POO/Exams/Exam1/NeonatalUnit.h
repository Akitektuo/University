#pragma once
#include "HospitalDepartment.h"
class NeonatalUnit : public HospitalDepartment
{
private:
	int numberOfMothers;
	int numberOfNewborns;
	double averageGrade;
public:
	NeonatalUnit(std::string hospitalName="", int numberOfDoctors=0, int numberOfMothers=0, int numberOfNewborns=0, double averageGrade=0.0);

	void setNumberOfMothers(int numberOfMothers);
	int getNumberOfMothers();

	void setNumberOfNewborns(int numberOfNewborns);
	int getNumberOfNewborns();

	void setAverageGrade(double averageGrade);
	double getAverageGrade();

	bool isEfficient();
	std::string toString();
};

