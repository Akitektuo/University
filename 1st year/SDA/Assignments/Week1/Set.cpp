#include <cstdlib>
#include <cstring>
#include "Set.h"
#include "SetIterator.h"

//O(1)
Set::Set() {
    array = nullptr;
    length = 0;
    maxSize = 0;
}

//O(n)
bool Set::add(TElem elem) {
    if (maxSize < 1) {
        maxSize = 2;
        array = new TElem[maxSize];
        array[length++] = elem;
        return true;
    }

    if (length + 1 > maxSize) {
        maxSize *= 2;
        auto *tempArray = new TElem[maxSize];
        for (int i = 0; i < length; i++) {
            if (elem == array[i]) {
                delete[] tempArray;
                maxSize /= 2;
                return false;
            }
            tempArray[i] = array[i];
        }
        tempArray[length++] = elem;
        delete[] array;
        array = new TElem[maxSize];
        std::memcpy(array, tempArray, sizeof(TElem) * maxSize);
        delete[] tempArray;
        return true;
    }

    if (search(elem)) {
        return false;
    }
    array[length++] = elem;
    return true;
}

//O(n)
bool Set::remove(TElem elem) {
    if (length < 1) {
        return false;
    }

    if (length < 2) {
        if (array[0] != elem) {
            return false;
        }
        delete[] array;
        maxSize = length = 0;
        return true;
    }

    if (length - 1 <= maxSize / 2) { // 8 maxSize, length = 5
        maxSize /= 2;
    }

    auto *tempArray = new TElem[maxSize];
    for (int i = 0; i < length; i++) {
        if (array[i] != elem) {
            tempArray[i] = array[i];
            continue;
        }
        for (int j = i + 1; j < length; j++) {
            tempArray[j - 1] = array[j];
        }
        delete[] array;
        array = new TElem[maxSize];
        std::memcpy(array, tempArray, sizeof(TElem) * maxSize);
        delete[] tempArray;
        length--;
        return true;
    }
    delete[] tempArray;
    maxSize *= 2;
    return false;
}

//O(n)
bool Set::search(TElem elem) const {
    for (int i = 0; i < length; i++) {
        if (array[i] == elem) {
            return true;
        }
    }
    return false;
}

//O(1)
int Set::size() const {
    return length;
}

//O(1)
bool Set::isEmpty() const {
    return length < 1;
}

//O(1)
Set::~Set() {
    free(array);
}


SetIterator Set::iterator() const {
    return SetIterator(*this);
}


