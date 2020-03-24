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

// O(n)
void SortedBagIterator::previous() {
    if (index-- > 0) {
        return;
    }

    auto searchElementLink = bag.elementsHead->linkedTo;
    auto searchFrequencyLink = bag.frequencyHead->linkedTo;

    if (searchElementLink == nullptr || currentElementLink == searchElementLink) {
        throw std::exception("No previous element");
    }

    while (searchElementLink->linkedTo != currentElementLink) {
        searchElementLink = searchElementLink->linkedTo;
        searchFrequencyLink = searchFrequencyLink->linkedTo;
    }

    currentElementLink = searchElementLink;
    currentFrequencyLink = searchFrequencyLink;
    index = currentFrequencyLink->value;
}

// O(1)
void SortedBagIterator::first() {
    index = 0;

    currentElementLink = bag.elementsHead->linkedTo;
    currentFrequencyLink = bag.frequencyHead->linkedTo;
}


