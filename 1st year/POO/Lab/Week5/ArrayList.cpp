#include "ArrayList.h"

template<class E>
void ArrayList<E>::resizeIfNeeded(int newSize)
{
	if (newSize > memorySize)
	{
		memorySize *= 2;
	}
	else if (newSize * 4 < memorySize && memorySize > MINIMUM_MEMORY_SIZE)
	{
		memorySize /= 2;
	}
	else
	{
		return;
	}

	auto temporaryElements = new E[memorySize];
	forEachIndexed([&](const E& element, int index) {
		temporaryElements[index] = element;
	});

	delete[] elements;
	elements = temporaryElements;
}

template<class E>
bool ArrayList<E>::isIndexValid(int index) const
{
	return 0 <= index && index < size;
}

template <class E>
ArrayList<E>::ArrayList()
{
	size = 0;
	memorySize = MINIMUM_MEMORY_SIZE;
	elements = new E[memorySize];
}

template <class E>
ArrayList<E>::~ArrayList()
{
	delete[] elements;
}

template<class E>
ArrayList<E>::ArrayList(const ArrayList<E>& arrayList)
{
	size = arrayList.size;
	memorySize = arrayList.memorySize;

	elements = new E[memorySize];
	arrayList.forEachIndexed([&](const E& element, int index) {
		elements[index] = element;
	});
}

template<class E>
void ArrayList<E>::add(const E& element)
{
	resizeIfNeeded(size + 1);

	elements[size++] = element;
}

template<class E>
void ArrayList<E>::addAt(const int& index, const E& element)
{
	if (!isIndexValid(index))
	{
		throw std::exception("Invalid index, index out of range");
	}
	resizeIfNeeded(size + 1);

	for (auto i = size - 1; i >= 0; i--)
	{
		elements[i + 1] = elements[i];
		if (i == index)
		{
			elements[i] = element;
			break;
		}
	}

	size++;
}

template<class E>
void ArrayList<E>::addAll(const ArrayList<E>& arrayList)
{
	arrayList.forEach([&](const E& element) {
		add(element);
	});
}

template<class E>
bool ArrayList<E>::any(const std::function<bool(const E&)>& selection) const
{
	for (auto i = 0; i < size; i++)
	{
		if (selection(elements[i]))
		{
			return true;
		}
	}
	return false;
}

template<class E>
void ArrayList<E>::clear() const
{
	size = 0;
	memorySize = MINIMUM_MEMORY_SIZE;
	delete[] elements;
	elements = new E[memorySize];
}

template<class E>
bool ArrayList<E>::isEmpty() const
{
	return size < 1;
}

template<class E>
int ArrayList<E>::getSize() const
{
	return size;
}

template<class E>
bool ArrayList<E>::hasIndex(int index) const
{
	return isIndexValid(index);
}

template<class E>
bool ArrayList<E>::contains(const E& element) const
{
	for (auto i = 0; i < size; i++)
	{
		if (elements[i] == element)
		{
			return true;
		}
	}
	return false;
}

template<class E>
bool ArrayList<E>::equals(const ArrayList<E>& arrayList) const
{
	for (auto i = 0; i < size; i++)
	{
		if (elements[i] != arrayList.get(i))
		{
			return false;
		}
	}
	return true;
}

template<class E>
int ArrayList<E>::indexOf(const E& element) const
{
	for (auto i = 0; i < size; i++)
	{
		if (elements[i] == element)
		{
			return i;
		}
	}
	return -1;
}

template<class E>
int ArrayList<E>::firstIndexOf(const E& element) const
{
	return indexOf(element);
}

template<class E>
int ArrayList<E>::lastIndexOf(const E& element) const
{
	for (auto i = size - 1; i >= 0; i--)
	{
		if (elements[i] == element)
		{
			return i;
		}
	}
	return -1;
}

template<class E>
int ArrayList<E>::findIndexOf(const std::function<bool(const E&)>& selection) const
{
	for (auto i = 0; i < size; i++)
	{
		if (selection(elements[i]))
		{
			return i;
		}
	}
	return -1;
}

template<class E>
void ArrayList<E>::set(int index, const E& element)
{
	if (!isIndexValid(index))
	{
		throw std::exception("Index out of range");
	}
	elements[index] = element;
}

template<class E>
const E& ArrayList<E>::get(int index) const
{
	if (!isIndexValid(index))
	{
		throw std::exception("Index out of range");
	}
	return elements[index];
}

template<class E>
const E& ArrayList<E>::getFirst() const
{
	if (size < 1)
	{
		throw std::exception("Empty array");
	}
	return elements[0];
}

template<class E>
const E& ArrayList<E>::getLast() const
{
	if (size < 1)
	{
		throw std::exception("Empty array");
	}
	return elements[0];
}

template<class E>
const E& ArrayList<E>::find(const std::function<bool(const E&)>& selection) const
{
	for (auto i = 0; i < size; i++)
	{
		if (selection(elements[i]))
		{
			return elements[i];
		}
	}
	throw std::exception("No element found");
}

template<class E>
void ArrayList<E>::forEach(const std::function<void(const E&)>& action) const
{
	for (auto i = 0; i < size; i++)
	{
		action(elements[i]);
	}
}

template<class E>
void ArrayList<E>::forEachIndexed(const std::function<void(const E&, int)>& action) const
{
	for (auto i = 0; i < size; i++)
	{
		action(elements[i], i);
	}
}

template<class E>
bool ArrayList<E>::remove(const E& element)
{
	for (auto i = 0; i < size; i++)
	{
		if (elements[i] != element)
		{
			continue;
		}
		for (auto j = i; j < size - 1; j++)
		{
			elements[j] = elements[j + 1];
		}
		resizeIfNeeded(--size);
		return true;
	}
	return false;
}

template<class E>
bool ArrayList<E>::removeAt(int index)
{
	if (!isIndexValid(index))
	{
		return false;
	}

	for (auto i = index; i < size - 1; i++)
	{
		elements[i] = elements[i + 1];
		
	}
	resizeIfNeeded(--size);
	return true;
}

template<class E>
void ArrayList<E>::removeAll(const std::function<bool(const E&)>& selection)
{
	for (auto i = 0; i < size; i++)
	{
		if (selection(elements[i]))
		{
			removeAt(i--);
		}
	}
}

template<class E>
int ArrayList<E>::countBy(const std::function<bool(const E&)>& selection) const
{
	auto count = 0;

	for (auto i = 0; i < size; i++)
	{
		if (selection(elements[i]))
		{
			count++;
		}
	}

	return count;
}

template<class E>
template<typename T>
const E& ArrayList<E>::maxBy(const std::function<T(const E&)>& selection) const
{
	if (size < 1)
	{
		return nullptr;
	}

	auto maxIndex = 0;
	auto maxValue = MIN_INT;

	for (auto i = 0; i < size; i++)
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
	if (size < 1)
	{
		return nullptr;
	}

	auto minIndex = 0;
	auto minValue = MAX_INT;

	for (auto i = 0; i < size; i++)
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

	for (auto i = 0; i < size; i++)
	{
		sum += selection(elements[i]);
	}

	return sum;
}

template<class E>
const ArrayList<E>& ArrayList<E>::reversed()
{
	for (auto i = 0; i < size / 2; i++)
	{
		elements[i] ^= elements[size - i - 1];
		elements[size - i - 1] ^= elements[i];
		elements[i] ^= elements[size - i - 1];
	}
	return *this;
}

template<class E>
template<typename T>
const ArrayList<E>& ArrayList<E>::sortBy(const std::function<T(const E&)>& selection)
{
	std::sort(elements, elements + size, [&](const E& firstElement, const E& lastElement) {
		return selection(firstElement) < selection(lastElement);
	});
	return *this;
}

template<class E>
const ArrayList<E>& ArrayList<E>::filter(const std::function<bool(const E&)>& selection)
{
	for (auto i = 0; i < size; i++)
	{
		if (!selection(elements[i]))
		{
			removeAt(i--);
		}
	}
	return *this;
}