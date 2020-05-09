#include "NeonatalUnit.h"

NeonatalUnit::NeonatalUnit(std::string hospitalName, int numberOfDoctors, int numberOfMothers, int numberOfNewborns, double averageGrade)
{
	setHospitalName(hospitalName);
	setNumberOfDoctors(numberOfDoctors);
	setNumberOfMothers(numberOfMothers);
	setNumberOfNewborns(numberOfNewborns);
	setAverageGrade(averageGrade);
}

void NeonatalUnit::setNumberOfMothers(int numberOfMothers)
{
	this->numberOfMothers = numberOfMothers;
}

int NeonatalUnit::getNumberOfMothers()
{
	return numberOfMothers;
}

void NeonatalUnit::setNumberOfNewborns(int numberOfNewborns)
{
	this->numberOfNewborns = numberOfNewborns;
}

int NeonatalUnit::getNumberOfNewborns()
{
	return numberOfNewborns;
}

void NeonatalUnit::setAverageGrade(double averageGrade)
{
	this->averageGrade = averageGrade;
}

double NeonatalUnit::getAverageGrade()
{
	return averageGrade;
}

bool NeonatalUnit::isEfficient()
{
	return getAverageGrade() >= 8.5 && getNumberOfNewborns() >= getNumberOfMothers();
}

std::string NeonatalUnit::toString()
{
	return getHospitalName() + "\nNeonatalUnit\n" + std::to_string(getNumberOfDoctors()) + "\n" + std::to_string(getNumberOfMothers()) + "\n" + std::to_string(getNumberOfNewborns()) + "\n" + std::to_string(getAverageGrade()) + "\n";
}