#include "SMMIterator.h"
#include "SortedMultiMap.h"

/// Tn)
SMMIterator::SMMIterator(const SortedMultiMap &d) : map(d) {
//    for (auto i = 0; i < map.maxSize; i++) {
//        auto node = map.addresses[i];
//
//        while (node->next != nullptr) {
//            auto next = node->next;
//            elements.emplace_back(next->key, next->value);
//            node = next;
//        }
//    }
//    sort(elements.begin(), elements.end(), [&](const TElem& pair1, const TElem& pair2) {
//        return !map.relation(pair1.first, pair2.first);
//    });
//    iterator = elements.begin();
    addresses = nullptr;
    currentPosition = -1;
    size = map.size();
    if (map.isEmpty()) {
        return;
    }

    addresses = new SortedMultiMap::Node[map.maxSize];
    for (auto i = 0; i < map.maxSize; i++) {
        addresses[i] = *map.addresses[i];
        if (addresses[i].next == nullptr) {
            continue;
        }
        if (currentPosition < 0) {
            currentPosition = i;
            continue;
        }
        if (!map.relation(addresses[currentPosition].next->key, addresses[i].next->key)) {
            currentPosition = i;
        }
    }
}

/// T(1)
void SMMIterator::first() {
//    iterator = elements.begin();
    if (size == map.size()) {
        return;
    }
    currentPosition = -1;
    size = map.size();
    if (map.isEmpty()) {
        return;
    }
    delete[] addresses;

    addresses = new SortedMultiMap::Node[map.maxSize];
    for (auto i = 0; i < map.maxSize; i++) {
        addresses[i] = *map.addresses[i];
        if (addresses[i].next == nullptr) {
            continue;
        }
        if (currentPosition < 0) {
            currentPosition = i;
            continue;
        }
        if (!map.relation(addresses[currentPosition].next->key, addresses[i].next->key)) {
            currentPosition = i;
        }
    }
}

/// T(1)
void SMMIterator::next() {
    if (!valid()) {
        throw exception{};
    }
//    iterator++;
    addresses[currentPosition] = *addresses[currentPosition].next;
    currentPosition = -1;
    size--;
    for (auto i = 0; i < map.maxSize; i++) {
        if (addresses[i].next == nullptr) {
            continue;
        }
        if (currentPosition < 0) {
            currentPosition = i;
            continue;
        }
        if (!map.relation(addresses[currentPosition].next->key, addresses[i].next->key)) {
            currentPosition = i;
        }
    }
}

/// T(1)
bool SMMIterator::valid() const {
//    return iterator != elements.end();
    return size > 0;
}

/// T(1)
TElem SMMIterator::getCurrent() const {
    if (!valid()) {
        throw exception{};
    }
//    return *iterator;

    return {addresses[currentPosition].next->key, addresses[currentPosition].next->value};
}

SMMIterator::~SMMIterator() {
    delete[] addresses;
}


