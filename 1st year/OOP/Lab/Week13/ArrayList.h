#pragma once
#include <vector>
#include <functional>
#include <algorithm>

constexpr auto MINIMUM_ARRAY_CAPACITY = 16;
constexpr auto MIN_INT = -2147483648;
constexpr auto MAX_INT = 2147483647;

template <class E>
class ArrayList
{
private:
	std::vector<E> elements;

public:
	ArrayList() {};
	~ArrayList() {};
	ArrayList(const ArrayList<E>& fromArrayList);

	void add(const E& element);
	void addAt(const int& index, const E& element);
	void addAll(const ArrayList<E>& fromArrayList);

	bool any(const std::function<bool(const E&)>& selection) const;

	void clear();
	bool isEmpty() const;
	int getSize() const;
	bool hasIndex(int index) const;

	bool contains(const E& element) const;

	bool equals(const ArrayList<E>& toArrayList) const;

	int indexOf(const E& element) const;
	int firstIndexOf(const E& element) const;
	int lastIndexOf(const E& element) const;
	int findIndexOf(const std::function<bool(const E&)>& selection) const;

	void set(int index, const E& element);
	const E& get(int index) const;
	const E& getFirst() const;
	const E& getLast() const;
	const E& find(const std::function<bool(const E&)>& selection) const;

	E pop();

	void forEach(const std::function<void(const E&)>& action) const;
	void forEachIndexed(const std::function<void(const E&, int)>& action) const;

	bool remove(const E& element);
	bool removeAt(int index);
	void removeAll(const std::function<bool(const E&)>& selection);

	int countBy(const std::function<bool(const E&)>& selection) const;
	template <typename T>
	const E& maxBy(const std::function<T(const E&)>& selection) const;
	template <typename T>
	const E& minBy(const std::function<T(const E&)>& selection) const;
	template <typename T>
	int sumBy(const std::function<T(const E&)>& selection) const;

	const ArrayList<E>& reversed();
	template <typename T>
	const ArrayList<E>& sortBy(const std::function<T(const E&)>& selection);
	const ArrayList<E>& filter(const std::function<bool(const E&)>& selection);

	void clearAndFree();
};

template<class E>
ArrayList<E>::ArrayList(const ArrayList<E>& fromArrayList)
{
	elements = fromArrayList.elements;
}

template<class E>
void ArrayList<E>::add(const E& element)
{
	elements.push_back(element);
}

template<class E>
void ArrayList<E>::addAt(const int& index, const E& element)
{
	if (!hasIndex(index))
	{
		throw std::exception("Invalid index, index out of range");
	}
	
	elements.insert(index, element);
}

template<class E>
void ArrayList<E>::addAll(const ArrayList<E>& fromArrayList)
{
	elements.insert(elements.end(), fromArrayList.elements.begin(), fromArrayList.elements.end());
}

template<class E>
bool ArrayList<E>::any(const std::function<bool(const E&)>& selection) const
{
	return std::any_of(elements.begin(), elements.end(), selection);
}

template<class E>
void ArrayList<E>::clear()
{
	elements.clear();
}

template<class E>
bool ArrayList<E>::isEmpty() const
{
	return elements.empty();
}

template<class E>
int ArrayList<E>::getSize() const
{
	return elements.size();
}

template<class E>
bool ArrayList<E>::hasIndex(int index) const
{
	return 0 <= index && index < elements.size();
}

template<class E>
bool ArrayList<E>::contains(const E& element) const
{
	return std::find(elements.begin(), elements.end(), element) != elements.end();
}

template<class E>
bool ArrayList<E>::equals(const ArrayList<E>& toArrayList) const
{
	return elements == toArrayList.elements;
}

template<class E>
int ArrayList<E>::indexOf(const E& element) const
{
	auto iteratorPosition = std::find(elements.begin(), elements.end(), element);
	if (iteratorPosition == elements.end())
	{
		return -1;
	}
	return std::distance(elements.begin(), iteratorPosition);
}

template<class E>
int ArrayList<E>::firstIndexOf(const E& element) const
{
	return indexOf(element);
}

template<class E>
int ArrayList<E>::lastIndexOf(const E& element) const
{
	auto iteratorPosition = std::find(elements.rbegin(), elements.rend(), element);
	if (iteratorPosition == elements.rend())
	{
		return -1;
	}
	return std::distance(elements.rbegin(), iteratorPosition);
}

template<class E>
int ArrayList<E>::findIndexOf(const std::function<bool(const E&)>& selection) const
{
	auto iteratorPosition = std::find_if(elements.begin(), elements.end(), selection);
	if (iteratorPosition == elements.end())
	{
		return -1;
	}
	return std::distance(elements.begin(), iteratorPosition);
}

template<class E>
void ArrayList<E>::set(int index, const E& element)
{
	if (!hasIndex(index))
	{
		throw std::exception("Index out of range");
	}
	elements[index] = element;
}

template<class E>
const E& ArrayList<E>::get(int index) const
{
	if (!hasIndex(index))
	{
		throw std::exception("Index out of range");
	}
	return elements[index];
}

template<class E>
const E& ArrayList<E>::getFirst() const
{
	if (getSize() < 1)
	{
		throw std::exception("Empty array");
	}
	return elements[0];
}

template<class E>
const E& ArrayList<E>::getLast() const
{
	if (getSize() < 1)
	{
		throw std::exception("Empty array");
	}
	return elements[elements.size() - 1];
}

template<class E>
const E& ArrayList<E>::find(const std::function<bool(const E&)>& selection) const
{
	auto iteratorPosition = std::find_if(elements.begin(), elements.end(), selection);
	if (iteratorPosition == elements.end())
	{
		throw std::exception("No element found");
	}
	return *iteratorPosition;
}

template<class E>
inline E ArrayList<E>::pop()
{
	auto removedElement = elements.back();
	elements.pop_back();
	return removedElement;
}

template<class E>
void ArrayList<E>::forEach(const std::function<void(const E&)>& action) const
{
	for (const auto& element : elements)
	{
		action(element);
	}
}

template<class E>
void ArrayList<E>::forEachIndexed(const std::function<void(const E&, int)>& action) const
{
	auto i = 0;
	for (const auto& element : elements)
	{
		action(element, i++);
	}
}

template<class E>
bool ArrayList<E>::remove(const E& element)
{
	auto elementIndex = std::remove(elements.begin(), elements.end(), element);
	elements.erase(elementIndex, elements.end());
	return elementIndex == elements.end();
}

template<class E>
bool ArrayList<E>::removeAt(int index)
{
	if (!hasIndex(index))
	{
		return false;
	}

	elements.erase(elements.begin() + index);
	return true;
}

template<class E>
void ArrayList<E>::removeAll(const std::function<bool(const E&)>& selection)
{
	auto elementIndex = std::remove_if(elements.begin(), elements.end(), selection);
	elements.erase(elementIndex, elements.end());
}

template<class E>
int ArrayList<E>::countBy(const std::function<bool(const E&)>& selection) const
{
	return std::count_if(elements.begin(), elements.end(), selection);
}

template<class E>
template<typename T>
const E& ArrayList<E>::maxBy(const std::function<T(const E&)>& selection) const
{
	if (getSize() < 1)
	{
		return {};
	}

	auto maxIndex = 0;
	auto maxValue = MIN_INT;

	for (auto i = 0; i < getSize(); i++)
	{
		auto selectionValue = selection(elements[i]);
		if (selectionValue > maxValue)
		{
			maxIndex = i;
			maxValue = selectionValue;
		}
	}

	return elements[maxIndex];

}

template<class E>
template<typename T>
const E& ArrayList<E>::minBy(const std::function<T(const E&)>& selection) const
{
	if (getSize() < 1)
	{
		return {};
	}

	auto minIndex = 0;
	auto minValue = MAX_INT;

	for (auto i = 0; i < getSize(); i++)
	{
		auto selectionValue = selection(elements[i]);
		if (selectionValue < minValue)
		{
			minIndex = i;
			minValue = selectionValue;
		}
	}

	return elements[minIndex];
}

template<class E>
template<typename T>
int ArrayList<E>::sumBy(const std::function<T(const E&)>& selection) const
{
	auto sum = 0;

	for (auto element : elements)
	{
		sum += selection(element);
	}

	return sum;
}

template<class E>
const ArrayList<E>& ArrayList<E>::reversed()
{
	std::reverse(elements.begin(), elements.end());
	return *this;
}

template<class E>
template<typename T>
const ArrayList<E>& ArrayList<E>::sortBy(const std::function<T(const E&)>& selection)
{
	std::sort(elements.begin(), elements.end(), [&](const E& firstElement, const E& lastElement) {
		return selection(firstElement) < selection(lastElement);
	});
	return *this;
}

template<class E>
const ArrayList<E>& ArrayList<E>::filter(const std::function<bool(const E&)>& selection)
{
	removeAll([&](const E& element) {
		return !selection(element);
	});
	return *this;
}

template<class E>
inline void ArrayList<E>::clearAndFree()
{
	for (auto& element : elements)
	{
		delete element;
	}
	clear();
}
