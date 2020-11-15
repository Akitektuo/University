#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <iostream>
#include <vector>
#include <exception>

using namespace std;

/// O(sqrt(n))
bool SortedMultiMap::isNumberPrime(int number) {
    if (number < 2) {
        return false;
    }
    if (number < 4) {
        return true;
    }
    if (number % 2 == 0 || number % 3 == 0) {
        return false;
    }
    for (auto i = 5; i * i <= number; i += 6) {
        if (number % i == 0 || number % (i + 2) == 0) {
            return false;
        }
    }
    return true;
}

/// O(sqrt(n))
void SortedMultiMap::computeNextSize() {
    maxSize *= 2;
    while (!isNumberPrime(maxSize)) {
        maxSize++;
    }
}

/// T(1)
int SortedMultiMap::hashElement(TKey key) const {
    return abs(key) % maxSize;
}

/// T(1)
SortedMultiMap::Node *SortedMultiMap::createNode(TKey key = 0, TValue value = 0, Node *nextNode = nullptr) {
    auto node = new Node;

    node->key = key;
    node->value = value;
    node->next = nextNode;

    return node;
}

/// O(m)
void SortedMultiMap::addNodeBack(Node* node) {
    auto hash = hashElement(node->key);
    auto currentNode = addresses[hash];

    while (currentNode->next != nullptr) {
        auto nextNode = currentNode->next;

        if (!relation(nextNode->key, node->key)) {
            node->next = nextNode;
            currentNode->next = node;
            return;
        }

        currentNode = currentNode->next;
    }

    currentNode->next = node;
}

/// O(n sqrt(n))
void SortedMultiMap::resizeIfNeeded() {
    if (totalSize * 1.0 / maxSize < 0.7) {
        return;
    }
    auto oldMaxSize = maxSize;
    computeNextSize();

    vector<Node *> nodesToAdd;
    auto tempAddresses = new Node *[maxSize];
    for (auto i = 0; i < maxSize; i++) {
        if (i < oldMaxSize) {
            auto currentNode = addresses[i];
            tempAddresses[i] = currentNode;

            while (currentNode->next != nullptr) {
                auto nextNode = currentNode->next;

                if (hashElement(nextNode->key) == i) {
                    currentNode = nextNode;
                    continue;
                }

                currentNode->next = nextNode->next;
                nextNode->next = nullptr;
                nodesToAdd.push_back(nextNode);
            }
        } else {
            tempAddresses[i] = createNode();
        }
    }

    delete[] addresses;
    addresses = tempAddresses;
    for (auto node : nodesToAdd) {
        addNodeBack(node);
    }
}

/// T(n)
SortedMultiMap::SortedMultiMap(Relation r) {
    totalSize = 0;
    relation = r;
    maxSize = HASH_KEY;
    addresses = new Node *[maxSize];
    for (auto i = 0; i < maxSize; i++) {
        addresses[i] = createNode();
    }
}

/// O(n * sqrt(n))
void SortedMultiMap::add(TKey c, TValue v) {
    resizeIfNeeded();
    totalSize++;

    auto hash = hashElement(c);
    auto currentNode = addresses[hash];

    while (currentNode->next != nullptr) {
        auto nextNode = currentNode->next;

        if (!relation(nextNode->key, c)) {
            currentNode->next = createNode(c, v, nextNode);
            return;
        }

        currentNode = currentNode->next;
    }

    currentNode->next = createNode(c, v);
}

/// T(1)
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

/// T(1)
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

/// O(1)
vector<TValue> SortedMultiMap::removeKey(TKey key) {
    vector<TValue> removed;

    auto hash = hashElement(key);
    auto currentNode = addresses[hash];

    while (currentNode->next != nullptr) {
        auto nextNode = currentNode->next;

        if (!relation(nextNode->key, key)) {
            break;
        }

        if (nextNode->key != key) {
            currentNode = nextNode;
            continue;
        }

        removed.push_back(nextNode->value);
        currentNode->next = nextNode->next;
        delete nextNode;
        totalSize--;
    }

    return removed;
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

/// T(n + m) where: n - number of addresses; m - number of nodes/address
SortedMultiMap::~SortedMultiMap() {
    for (auto i = 0; i < maxSize; i++) {
        auto node = addresses[i];

        while (node->next != nullptr) {
            auto next = node->next;
            delete node;
            node = next;
        }

        delete node;
    }
    delete[] addresses;
}
