#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <exception>
#include <iostream>

// O(n)
MultiMap::MultiMap() {
//    capacity = INITIAL_CAPACITY;
//    array = new ListElement[capacity];
//    for (int i = 0; i < capacity - 1; i++) {
//        array[i].next = i + 1;
//    }
//    keysHead = -1;
//    emptyHead = 0;
//    length = 0;
    totalSize = 0;
    keyList = createSLLA<KeyElement>();
}

// O(n)
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
//    auto firstEmptyPosition = emptyHead;
//    length++;
//
//    if (keysHead < 0) {
//        emptyHead = array[firstEmptyPosition].next;
//        keysHead = firstEmptyPosition;
//        array[firstEmptyPosition].next = -1;
//
//        array[firstEmptyPosition].key = k;
//        array[firstEmptyPosition].values.push_back(v);
//        return;
//    }
//
//    auto lastKeyPosition = -1;
//
//    for (auto i = keysHead; i > -1; i = array[i].next) {
//        if (array[i].key != k) {
//            lastKeyPosition = i;
//            continue;
//        }
//        array[i].values.push_back(v);
//        return;
//    }
//
//    if (firstEmptyPosition < 0) {
//        auto oldCapacity = capacity;
//        capacity *= 2;
//
//        auto tempArray = new ListElement[capacity];
//        for (int i = 0; i < capacity - 1; i++) {
//            if (i < oldCapacity) {
//                tempArray[i] = array[i];
//            } else {
//                tempArray[i].next = i + 1;
//            }
//        }
//        delete[] array;
//        array = tempArray;
//
//        emptyHead = oldCapacity + 1;
//        array[lastKeyPosition].next = oldCapacity;
//        array[oldCapacity].next = -1;
//
//        array[oldCapacity].key = k;
//        array[oldCapacity].values.push_back(v);
//        return;
//    }
//
//    emptyHead = array[firstEmptyPosition].next;
//    array[lastKeyPosition].next = firstEmptyPosition;
//    array[firstEmptyPosition].next = -1;
//
//    array[firstEmptyPosition].key = k;
//    array[firstEmptyPosition].values.push_back(v);
    totalSize++;

    auto index = getElementIndex<KeyElement>(keyList, [&](const SLLAElement<KeyElement> &element) {
        return element.payload.key == k;
    });

    if (index > -1) {
        addPayload(keyList.elements[index].payload.values, v);
        return;
    }

    /// No element found with the given key ///
    auto newValuesList = createSLLA<TValue>();
    addPayload(newValuesList, v);

    KeyElement newPayload;
    newPayload.key = k;
    newPayload.values = newValuesList;

    addPayload(keyList, newPayload);
}

// O(n)
bool MultiMap::remove(TKey k, TValue v) {
//    auto previousIndex = -1;
//
//    for (auto i = keysHead; i > -1; i = array[i].next) {
//        if (array[i].key != k) {
//            previousIndex = i;
//            continue;
//        }
//
//        auto position = std::find(array[i].values.begin(), array[i].values.end(), v);
//
//        if (position == array[i].values.end()) {
//            return false;
//        }
//
//        array[i].values.erase(position);
//        length--;
//
//        if (!array[i].values.empty()) {
//            return true;
//        }
//
//        /// Update previous key ///
//        array[previousIndex].next = array[i].next;
//
//        /// Search for and update last empty position ///
//        auto lastEmptyIndex = emptyHead;
//        while (lastEmptyIndex > -1) {
//            lastEmptyIndex = array[lastEmptyIndex].next;
//        }
//        array[lastEmptyIndex].next = i;
//
//        return true;
//    }
//    return false;
    auto keyPreviousIndex = -1;
    auto keyIndex = getElementIndex<KeyElement>(keyList, [&](const SLLAElement<KeyElement>& element) {
        return element.payload.key == k;
    }, &keyPreviousIndex);

    if (keyIndex < 0) {
        return false;
    }

    auto valuePreviousIndex = -1;
    auto valueIndex = getElementIndex<TValue>(keyList.elements[keyIndex].payload.values, [&](const SLLAElement<TValue>& element) {
        return element.payload == v;
    }, &valuePreviousIndex);

    if (valueIndex < 0) {
        return false;
    }

    totalSize--;

    if (keyList.elements[keyIndex].payload.values.size < 2) {
        destroySLLA(keyList.elements[keyIndex].payload.values);
        relink(keyList, keyPreviousIndex, keyIndex, keyList.elements[keyIndex].next);
        return true;
    }
    relink(keyList.elements[keyIndex].payload.values, valuePreviousIndex, valueIndex, keyList.elements[keyIndex].payload.values.elements[valueIndex].next);
    return true;
}

// O(n)
std::vector<TValue> MultiMap::search(TKey k) const {
//    for (auto i = keysHead; i > -1; i = array[i].next) {
//        if (array[i].key == k) {
//            return array[i].values;
//        }
//    }
//    return {};
    auto keyIndex = getElementIndex<KeyElement>(keyList, [&](const SLLAElement<KeyElement>& element) {
        return element.payload.key == k;
    });

    if (keyIndex < 0) {
        return {};
    }

    std::vector<TValue> result;
    for (auto i = keyList.elements[keyIndex].payload.values.valueHead; i > -1; i = keyList.elements[keyIndex].payload.values.elements[i].next) {
        result.push_back(keyList.elements[keyIndex].payload.values.elements[i].payload);
    }
    return result;
}

// Theta(1)
int MultiMap::size() const {
//    return length;
    return totalSize;
}

// Theta(1)
bool MultiMap::isEmpty() const {
//    return length < 1;
    return totalSize < 1;
}

// Theta(1)
MultiMapIterator MultiMap::iterator() const {
    return MultiMapIterator(*this);
}

// Theta(1)
// O(n)
MultiMap::~MultiMap() {
//    delete[] array;
    for (auto i = 0; i < keyList.capacity; i++) {
        if (!isSLLANull(keyList.elements[i].payload.values)) {
            destroySLLA(keyList.elements[i].payload.values);
        }
    }
    destroySLLA(keyList);
}
