#include <cstdlib>
#include "Set.h"
#include "SetIterator.h"

Set::Set() {
    array = nullptr;
    length = 0;
}


bool Set::add(TElem elem) {
    if (search(elem)) {
        return false;
    }
    array = (TElem *) realloc(array, (length + 1) * sizeof(TElem));
    if (array == nullptr) {
        return false;
    }
    array[length++] = elem;
    return true;
}


bool Set::remove(TElem elem) {
    for (int i = 0; i < length; i++) {
        if (array[i] == elem) {
            for (int j = i + 1; j < length; j++) {
                array[j - 1] = array[j];
            }
            array = (TElem *) realloc(array, --length * sizeof(TElem));
            return true;
        }
    }
    return false;
}

bool Set::search(TElem elem) const {
    for (int i = 0; i < length; i++) {
        if (array[i] == elem) {
            return true;
        }
    }
    return false;
}


int Set::size() const {
    return length;
}


bool Set::isEmpty() const {
    return length < 1;
}


Set::~Set() {
    free(array);
}


SetIterator Set::iterator() const {
    return SetIterator(*this);
}


