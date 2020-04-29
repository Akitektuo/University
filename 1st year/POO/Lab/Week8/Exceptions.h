#pragma once

#include <exception>

class FileRepositoryException : public std::exception
{
	virtual const char* what() const throw()
	{
		return "File repository error!";
	}
};

class NoTrenchCoatException : public std::exception
{
	virtual const char* what() const throw()
	{
		return "No trench coats available error!";
	}
};