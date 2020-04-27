#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <iostream>
#include <vector>
#include <exception>
using namespace std;

/// T(1)
int SortedMultiMap::hashElement(TKey key) const {
    return abs(key) % HASH_KEY;
}

/// T(1)
SortedMultiMap::Node *SortedMultiMap::createNode(TKey key = 0, TValue value = 0, Node *nextNode = nullptr) {
    auto node = new Node;
    node->key = key;
    node->value = value;
    node->next = nextNode;
    return node;
}

/// O(n)
SortedMultiMap::SortedMultiMap(Relation r) {
    totalSize = 0;
    relation = r;
    for (auto &address : addresses) {
        address = createNode();
    }
}

/// O(m)
void SortedMultiMap::add(TKey c, TValue v) {
    auto hash = hashElement(c);
    auto currentNode = addresses[hash];

    while (currentNode->next != nullptr) {
        auto nextNode = currentNode->next;

        if (!relation(nextNode->key, c)) {
            currentNode->next = createNode(c, v, nextNode);
            totalSize++;
            return;
        }

        currentNode = currentNode->next;
    }

    currentNode->next = createNode(c, v);
    totalSize++;
}

/// O(m)
vector<TValue> SortedMultiMap::search(TKey c) const {
    vector<TValue> result;
    auto hash = hashElement(c);
    auto currentNode = addresses[hash];

    while (currentNode->next != nullptr) {
        auto nextNode = currentNode->next;

        if (!relation(nextNode->key, c)) {
            break;
        }

        if (nextNode->key == c) {
            result.push_back(nextNode->value);
        }

        currentNode = nextNode;
    }

    return result;
}

/// O(m)
bool SortedMultiMap::remove(TKey c, TValue v) {
    auto hash = hashElement(c);
    auto currentNode = addresses[hash];

    while (currentNode->next != nullptr) {
        auto nextNode = currentNode->next;

        if (!relation(nextNode->key, c)) {
            return false;
        }

        if (nextNode->key == c && nextNode->value == v) {
            currentNode->next = nextNode->next;
            delete nextNode;
            totalSize--;
            return true;
        }

        currentNode = nextNode;
    }

    return false;
}

/// T(1)
int SortedMultiMap::size() const {
    return totalSize;
}

/// T(1)
bool SortedMultiMap::isEmpty() const {
    return !totalSize;
}

/// T(1)
SMMIterator SortedMultiMap::iterator() const {
    return SMMIterator(*this);
}

/// O(n + m) where: n - number of addresses; m - number of nodes/address
SortedMultiMap::~SortedMultiMap() {
    for (auto node : addresses) {
        while (node->next != nullptr) {
            auto next = node->next;
            delete node;
            node = next;
        }

        delete node;
    }
}
