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
    auto firstEmptyPosition = emptyHead;
    length++;

    if (keysHead < 0) {
        keysHead = emptyHead;

        array[firstEmptyPosition].key = k;
        array[firstEmptyPosition].values.push_back(v);
        array[firstEmptyPosition].next = -1;

        emptyHead = array[firstEmptyPosition].next;
        return;
    }

    for (auto i = keysHead; i >= 0; i = array[i].next) {
        
    }
}

bool MultiMap::remove(TKey k, TValue v) {
    //TODO - Implementation
    return false;
}

std::vector<TValue> MultiMap::search(TKey k) const {
    //TODO - Implementation
    return std::vector<TValue>();
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
