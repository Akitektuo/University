#include "SMMIterator.h"
#include "SortedMultiMap.h"

/// T(n)
SMMIterator::SMMIterator(const SortedMultiMap &d) : map(d) {
    indexed = 0;
    currentHash = 0;
    for (auto i = 0; i < map.maxSize; i++) {
        auto node = map.addresses[i];
        if (node->next == nullptr) {
            currentHash++;
            continue;
        }
        currentNode = node->next;
        return;
    }
}

/// O(n)
void SMMIterator::first() {
    indexed = 0;
    currentHash = 0;
    for (auto i = 0; i < map.maxSize; i++) {
        auto node = map.addresses[i];
        if (node->next == nullptr) {
            currentHash++;
            continue;
        }
        currentNode = node->next;
        return;
    }
}

/// O(n)
void SMMIterator::next() {
    if (!valid()) {
        throw exception {};
    }

    if (++indexed >= map.size()) {
        return;
    }

    if (currentNode->next != nullptr) {
        currentNode = currentNode->next;
        return;
    }

    currentHash++;
    while (map.addresses[currentHash]->next == nullptr) {
        currentHash++;
    }
    currentNode = map.addresses[currentHash]->next;
}

/// T(1)
bool SMMIterator::valid() const {
    return indexed < map.size();
}

/// T(1)
TElem SMMIterator::getCurrent() const {
    if (indexed >= map.size() || currentNode == nullptr) {
        throw exception {};
    }
    return { currentNode->key, currentNode->value };
}


