#pragma once

#include "SortedMultiMap.h"
#include <algorithm>

class SMMIterator {
    friend class SortedMultiMap;

private:
    //DO NOT CHANGE THIS PART
    const SortedMultiMap &map;

    SMMIterator(const SortedMultiMap &map);

//    SortedMultiMap::Node *currentNode;
//    int currentHash;
//    int indexed = 0;

//    vector<TElem> elements;
//    vector<TElem>::iterator iterator;
    int size;
    SortedMultiMap::Node *addresses;
    int currentPosition;

public:
    void first();

    void next();

    bool valid() const;

    TElem getCurrent() const;

    ~SMMIterator();
};

