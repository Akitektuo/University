#include "MultiMapIterator.h"
#include "MultiMap.h"

// Theta(1)
MultiMapIterator::MultiMapIterator(const MultiMap& c): col(c) {
    currentIndex = col.keysHead;
    valuesIndex = 0;
    iterated = 0;
}

// Theta(1)
TElem MultiMapIterator::getCurrent() const {
    if (!valid()) {
        throw std::exception();
    }

    auto currentElement = col.array[currentIndex];
    return {currentElement.key, currentElement.values.at(valuesIndex)};
}

// Theta(1)
bool MultiMapIterator::valid() const {
    return iterated < col.size() && currentIndex > -1;
}

// Theta(1)
void MultiMapIterator::next() {
    if (!valid()) {
        throw std::exception();
    }

    iterated++;

    if (++valuesIndex < col.array[currentIndex].values.size()) {
        return;
    }
    valuesIndex = 0;
    currentIndex = col.array[currentIndex].next;
}

// Theta(1)
void MultiMapIterator::first() {
    currentIndex = col.keysHead;
    valuesIndex = 0;
    iterated = 0;
}

