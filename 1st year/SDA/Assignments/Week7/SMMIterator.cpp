#include "SMMIterator.h"
#include "SortedMultiMap.h"

/// O(n log^2 n)
SMMIterator::SMMIterator(const SortedMultiMap &d) : map(d) {
    for (auto i = 0; i < map.maxSize; i++) {
        auto node = map.addresses[i];

        while (node->next != nullptr) {
            auto next = node->next;
            elements.emplace_back(next->key, next->value);
            node = next;
        }
    }
    sort(elements.begin(), elements.end(), [&](const TElem& pair1, const TElem& pair2) {
        return !map.relation(pair1.first, pair2.first);
    });
    iterator = elements.begin();

}

/// T(1)
void SMMIterator::first() {
    iterator = elements.begin();
}

/// T(1)
void SMMIterator::next() {
    if (iterator == elements.end()) {
        throw exception {};
    }
    iterator++;
}

/// T(1)
bool SMMIterator::valid() const {
    return iterator != elements.end();
}

/// T(1)
TElem SMMIterator::getCurrent() const {
    if (iterator == elements.end()) {
        throw exception {};
    }
    return *iterator;
}


