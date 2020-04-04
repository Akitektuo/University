#pragma once
#include "MultiMap.h"
#include <exception>

class MultiMap;

class MultiMapIterator {
    friend class MultiMap;

private:
    const MultiMap &col;
    int currentIndex;
    int valuesIndex;
    int iterated;

    MultiMapIterator(const MultiMap &c);

public:
    TElem getCurrent() const;

    bool valid() const;

    void next();

    void first();
};

