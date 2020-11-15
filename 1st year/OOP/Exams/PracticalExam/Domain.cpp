#include "Domain.h"

Astronomer::Astronomer(std::string name, std::string constellation) : name{ name }, constellation{constellation} {}

std::string Astronomer::getName() const
{
    return name;
}

std::string Astronomer::getConstellation() const
{
    return constellation;
}

Star::Star(std::string name, std::string constellation, int ascension, int declination, int diameter) : name{ name }, constellation{ constellation }, ascension{ ascension }, declination{declination}, diameter{diameter}
{}
