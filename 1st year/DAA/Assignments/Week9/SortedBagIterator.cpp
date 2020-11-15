#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

/// O(n)
void SortedBagIterator::iterateTree(Node *root, const function<void(Node *&)> &action, bool &shouldStop) {
    if (root == nullptr || shouldStop) {
        return;
    }
    iterateTree(root->left, action, shouldStop);
    action(root);
    iterateTree(root->right, action, shouldStop);
}

/// O(n)
SortedBagIterator::SortedBagIterator(const SortedBag &b) : bag(b) {
    first();
}

/// O(n)
void SortedBagIterator::first() {
    iterated = 0;
    auto stop = false;
    auto count = 0;
    iterateTree(bag.tree, [&](Node* &node) {
        if (iterated == count) {
            current = node->value;
            stop = true;
        }
        count++;
    }, stop);
}

/// T(1)
bool SortedBagIterator::valid() {
    return iterated < bag.size();
}

/// T(1)
TComp SortedBagIterator::getCurrent() {
    if (!valid()) {
        throw std::exception {};
    }
    return current;
}

/// O(n)
void SortedBagIterator::next() {
    if (!valid()) {
        throw std::exception {};
    }
    iterated++;
    auto stop = false;
    auto count = 0;
    iterateTree(bag.tree, [&](Node* &node) {
        if (iterated == count) {
            current = node->value;
            stop = true;
        }
        count++;
    }, stop);
}
