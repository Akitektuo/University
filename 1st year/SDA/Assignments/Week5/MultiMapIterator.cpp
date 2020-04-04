#include "MultiMapIterator.h"
#include "MultiMap.h"


MultiMapIterator::MultiMapIterator(const MultiMap& c): col(c) {
    currentIndex = 0;
    valuesIndex = 0;
}

TElem MultiMapIterator::getCurrent() const {
    auto currentElement = col.array[currentIndex];

    return {
            currentElement.key,
            currentElement.values.at(valuesIndex)
    };
}

bool MultiMapIterator::valid() const {
    auto currentElement = col.array[currentIndex];
    return valuesIndex < currentElement.values.size() || currentElement.next != -1;
}

void MultiMapIterator::next() {
    if (!valid()) {
        throw std::exception("No valid next item");
    }

    if (++valuesIndex < col.array[currentIndex].values.size()) {
        return;
    }
    valuesIndex = 0;
    currentIndex = col.array[currentIndex].next;
}

void MultiMapIterator::first() {
    currentIndex = 0;
    valuesIndex = 0;
}

