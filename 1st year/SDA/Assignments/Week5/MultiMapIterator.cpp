#include "MultiMapIterator.h"
#include "MultiMap.h"

// Theta(1)
MultiMapIterator::MultiMapIterator(const MultiMap& c): col(c) {
//    currentIndex = col.keysHead;
//    valuesIndex = 0;
//    iterated = 0;
    keyIndex = col.keyList.valueHead;
    if (keyIndex > -1) {
        valueIndex = col.keyList.elements[keyIndex].payload.values.valueHead;
    }
    iterated = 0;
}

// Theta(1)
TElem MultiMapIterator::getCurrent() const {
    if (!valid()) {
        throw std::exception();
    }

//    auto currentElement = col.array[currentIndex];
//    return {currentElement.key, currentElement.values.at(valuesIndex)};
    auto currentKey = col.keyList.elements[keyIndex].payload.key;
    auto currentValue = col.keyList.elements[keyIndex].payload.values.elements[valueIndex].payload;
    return {currentKey, currentValue};
}

// Theta(1)
bool MultiMapIterator::valid() const {
//    return iterated < col.size() && currentIndex > -1;
    return iterated < col.size() && keyIndex > -1;
}

// Theta(1)
void MultiMapIterator::next() {
    if (!valid()) {
        throw std::exception();
    }

    iterated++;

//    if (++valuesIndex < col.array[currentIndex].values.size()) {
//        return;
//    }
//    valuesIndex = 0;
//    currentIndex = col.array[currentIndex].next;
    auto nextValueIndex = col.keyList.elements[keyIndex].payload.values.elements[valueIndex].next;
    if (nextValueIndex > -1) {
        valueIndex = nextValueIndex;
        return;
    }
    keyIndex = col.keyList.elements[keyIndex].next;
    valueIndex = col.keyList.elements[keyIndex].payload.values.valueHead;
}

// Theta(1)
void MultiMapIterator::first() {
//    currentIndex = col.keysHead;
//    valuesIndex = 0;
//    iterated = 0;
    keyIndex = col.keyList.valueHead;
    if (keyIndex > -1) {
        valueIndex = col.keyList.elements[keyIndex].payload.values.valueHead;
    }
    iterated = 0;
}

