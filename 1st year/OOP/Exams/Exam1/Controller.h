#pragma once

#include <vector>
#include <sstream>
#include "HospitalDepartment.h"
#include "Surgery.h"
#include "NeonatalUnit.h"

class Controller
{
private:
	std::vector<HospitalDepartment*> hospitalDepartments;

public:
	void addDepartment(HospitalDepartment* hospitalDepartment);
	std::vector<HospitalDepartment*> getAllDepartments();
	std::vector<HospitalDepartment*> getAllEfficientDepartments();

	~Controller();
};

