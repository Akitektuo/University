#pragma once

#include "SortedMultiMap.h"


class SMMIterator {
    friend class SortedMultiMap;

private:
    //DO NOT CHANGE THIS PART
    const SortedMultiMap &map;

    SMMIterator(const SortedMultiMap &map);

    SortedMultiMap::Node *currentNode;
    int currentHash;
    int indexed = 0;

public:
    void first();

    void next();

    bool valid() const;

    TElem getCurrent() const;
};

