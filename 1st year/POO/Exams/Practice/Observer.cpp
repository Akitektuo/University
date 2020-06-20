#include "Observer.h"

void Subject::addObserver(Observer* observer)
{
	observers.push_back(observer);
}

void Subject::removeObserver(Observer* observer)
{
	auto observerToRemove = std::find(observers.begin(), observers.end(), observer);
	if (observerToRemove != observers.end())
	{
		observers.erase(observerToRemove);
	}
}

void Subject::notify()
{
	for (const auto& observer : observers)
	{
		observer->update();
	}
}
