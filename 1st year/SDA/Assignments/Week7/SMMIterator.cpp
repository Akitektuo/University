#include "SMMIterator.h"
#include "SortedMultiMap.h"

SMMIterator::SMMIterator(const SortedMultiMap &d) : map(d) {
    indexed = 0;
    currentHash = 0;
    for (auto node : map.addresses) {
        if (node->next == nullptr) {
            currentHash++;
            continue;
        }
        currentNode = node->next;
        return;
    }
}

void SMMIterator::first() {
    indexed = 0;
    currentHash = 0;
    for (auto node : map.addresses) {
        if (node->next == nullptr) {
            currentHash++;
            continue;
        }
        currentNode = node->next;
        return;
    }
}

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

bool SMMIterator::valid() const {
    return indexed < map.size();
}

TElem SMMIterator::getCurrent() const {
    if (indexed >= map.size() || currentNode == nullptr) {
        throw exception {};
    }
    return { currentNode->key, currentNode->value };
}


