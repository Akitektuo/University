#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <exception>
#include <iostream>

MultiMap::MultiMap() {
    capacity = INITIAL_CAPACITY;
    array = new ListElement[capacity];
    for (int i = 0; i < capacity - 1; i++) {
        array[i].next = i + 1;
    }
    keysHead = -1;
    emptyHead = 0;
    length = 0;
}

void MultiMap::add(TKey k, TValue v) {
    /**
     * Steps for adding new element:
     * ### Update Links ###
     * 1. Point empty head to a new empty position
     * 2. Set the previous key to point to the current one
     * 3. Mark the new key as the last one (next = -1)
     * ### Set Data ###
     * 4. Set the key
     * 5. Add the value to the array
     */
    auto firstEmptyPosition = emptyHead;
    length++;

    if (keysHead < 0) {
        emptyHead = array[firstEmptyPosition].next;
        keysHead = firstEmptyPosition;
        array[firstEmptyPosition].next = -1;

        array[firstEmptyPosition].key = k;
        array[firstEmptyPosition].values.push_back(v);
        return;
    }

    auto lastKeyPosition = -1;

    for (auto i = keysHead; i > -1; i = array[i].next) {
        if (array[i].key != k) {
            lastKeyPosition = i;
            continue;
        }
        array[i].values.push_back(v);
        return;
    }

    if (firstEmptyPosition < 0) {
        auto oldCapacity = capacity;
        capacity *= 2;

        auto tempArray = new ListElement[capacity];
        for (int i = 0; i < capacity - 1; i++) {
            if (i < oldCapacity) {
                tempArray[i] = array[i];
            } else {
                tempArray[i].next = i + 1;
            }
        }
        delete[] array;
        array = tempArray;

        emptyHead = oldCapacity + 1;
        array[lastKeyPosition].next = oldCapacity;
        array[oldCapacity].next = -1;

        array[oldCapacity].key = k;
        array[oldCapacity].values.push_back(v);
        return;
    }

    emptyHead = array[firstEmptyPosition].next;
    array[lastKeyPosition].next = firstEmptyPosition;
    array[firstEmptyPosition].next = -1;

    array[firstEmptyPosition].key = k;
    array[firstEmptyPosition].values.push_back(v);
}

bool MultiMap::remove(TKey k, TValue v) {
    auto previousIndex = -1;

    for (auto i = keysHead; i > -1; i = array[i].next) {
        if (array[i].key != k) {
            previousIndex = i;
            continue;
        }

        auto position = std::find(array[i].values.begin(), array[i].values.end(), v);

        if (position == array[i].values.end()) {
            return false;
        }

        array[i].values.erase(position);
        length--;

        if (!array[i].values.empty()) {
            return true;
        }

        /// Update previous key ///
        array[previousIndex].next = array[i].next;

        /// Search for and update last empty position ///
        auto lastEmptyIndex = emptyHead;
        while (lastEmptyIndex > -1) {
            lastEmptyIndex = array[lastEmptyIndex].next;
        }
        array[lastEmptyIndex].next = i;

        return true;
    }
    return false;
}

std::vector<TValue> MultiMap::search(TKey k) const {
    for (auto i = keysHead; i > -1; i = array[i].next) {
        if (array[i].key == k) {
            return array[i].values;
        }
    }
    return {};
}

int MultiMap::size() const {
    return length;
}

bool MultiMap::isEmpty() const {
    return length < 0;
}

MultiMapIterator MultiMap::iterator() const {
    return MultiMapIterator(*this);
}

MultiMap::~MultiMap() {
    delete[] array;
}