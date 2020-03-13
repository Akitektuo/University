#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>
#include <iostream>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag &b) : bag(b) {
    index = 0;

    currentElementLink = bag.elementsHead->linkedTo;
    currentFrequencyLink = bag.frequencyHead->linkedTo;
}

TComp SortedBagIterator::getCurrent() {
    if (currentElementLink == nullptr) {
        throw exception("No element");
    }
    return currentElementLink->value;
}

bool SortedBagIterator::valid() {
    return currentElementLink != nullptr &&
           (index < currentFrequencyLink->value || currentElementLink->linkedTo != nullptr);
}

void SortedBagIterator::next() {
    if (!valid()) {
        throw exception("No next element");
    }
    if (++index < currentFrequencyLink->value) {
        return;
    }
    currentElementLink = currentElementLink->linkedTo;
    currentFrequencyLink = currentFrequencyLink->linkedTo;
    index = 0;
}

void SortedBagIterator::first() {
    index = 0;

    currentElementLink = bag.elementsHead->linkedTo;
    currentFrequencyLink = bag.frequencyHead->linkedTo;
}

