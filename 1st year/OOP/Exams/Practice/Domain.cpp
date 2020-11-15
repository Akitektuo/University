#include "Domain.h"

Student::Student(std::string id, std::string name, std::string email, int year, std::string thesis, std::string teacher) : id{ id }, name{ name }, email{ email }, year{ year }, thesis{ thesis }, teacher{teacher}
{}

std::string Student::getId() const
{
    return id;
}
