#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

// O(1)
SortedBagIterator::SortedBagIterator(const SortedBag &b) : bag(b) {
    index = 0;

    currentElementLink = bag.elementsHead->linkedTo;
    currentFrequencyLink = bag.frequencyHead->linkedTo;
}

// O(1)
TComp SortedBagIterator::getCurrent() {
    if (currentElementLink == nullptr) {
        throw std::exception("No element");
    }
    return currentElementLink->value;
}

// O(1)
bool SortedBagIterator::valid() {
    return currentElementLink != nullptr &&
           (index < currentFrequencyLink->value || currentElementLink->linkedTo != nullptr);
}

// O(1)
void SortedBagIterator::next() {
    if (!valid()) {
        throw std::exception("No next element");
    }
    if (++index < currentFrequencyLink->value) {
        return;
    }
    currentElementLink = currentElementLink->linkedTo;
    currentFrequencyLink = currentFrequencyLink->linkedTo;
    index = 0;
}

// O(1)
void SortedBagIterator::first() {
    index = 0;

    currentElementLink = bag.elementsHead->linkedTo;
    currentFrequencyLink = bag.frequencyHead->linkedTo;
}

