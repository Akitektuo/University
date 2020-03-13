#include "SortedBag.h"
#include "SortedBagIterator.h"

SortedBag::SortedBag(Relation r) {
    relation = r;
    length = 0;

    elementsHead = new Link;
    elementsHead->linkedTo = nullptr;

    frequencyHead = new Link;
    frequencyHead->linkedTo = nullptr;
}

void SortedBag::add(TComp e) {
    auto currentElementLink = elementsHead;
    auto currentFrequencyLink = frequencyHead;

    while (currentElementLink->linkedTo != nullptr) {
        auto nextElementLink = currentElementLink->linkedTo;
        auto nextFrequencyLink = currentFrequencyLink->linkedTo;

        // Update existing value
        if (nextElementLink->value == e) {
            nextFrequencyLink->value++;
            length++;
            return;
        }

        // Insert new element
        if (!relation(nextElementLink->value, e)) {
            auto newElementLink = new Link;
            currentElementLink->linkedTo = newElementLink;
            newElementLink->value = e;
            newElementLink->linkedTo = nextElementLink;

            auto newFrequencyLink = new Link;
            currentFrequencyLink->linkedTo = newFrequencyLink;
            newFrequencyLink->value = 1;
            newFrequencyLink->linkedTo = nextFrequencyLink;

            length++;
            return;
        }

        currentElementLink = nextElementLink;
        currentFrequencyLink = nextFrequencyLink;
    }

    auto newElementLink = new Link;
    currentElementLink->linkedTo = newElementLink;
    newElementLink->value = e;
    newElementLink->linkedTo = nullptr;

    auto newFrequencyLink = new Link;
    currentFrequencyLink->linkedTo = newFrequencyLink;
    newFrequencyLink->value = 1;
    newFrequencyLink->linkedTo = nullptr;
    length++;
}

bool SortedBag::remove(TComp e) {
    auto currentElementLink = elementsHead;
    auto currentFrequencyLink = frequencyHead;

    while (currentElementLink->linkedTo != nullptr) {
        auto nextElementLink = currentElementLink->linkedTo;
        auto nextFrequencyLink = currentFrequencyLink->linkedTo;

        if (nextElementLink->value == e) {
            if (nextFrequencyLink->value > 1) {
                nextFrequencyLink->value--;
            } else {
                currentElementLink->linkedTo = nextElementLink->linkedTo;
                currentFrequencyLink->linkedTo = nextFrequencyLink->linkedTo;
                delete nextElementLink;
                delete nextFrequencyLink;
            }

            length--;
            return true;
        }

        if (!relation(nextElementLink->value, e)) {
            return false;
        }

        currentElementLink = nextElementLink;
        currentFrequencyLink = nextFrequencyLink;
    }
    return false;
}


bool SortedBag::search(TComp elem) const {
    auto currentElementLink = elementsHead;
    auto currentFrequencyLink = frequencyHead;

    while (currentElementLink->linkedTo != nullptr) {
        auto nextElementLink = currentElementLink->linkedTo;
        auto nextFrequencyLink = currentFrequencyLink->linkedTo;

        if (nextElementLink->value == elem) {
            return true;
        }

        if (!relation(nextElementLink->value, elem)) {
            return false;
        }

        currentElementLink = nextElementLink;
        currentFrequencyLink = nextFrequencyLink;
    }
    return false;
}


int SortedBag::nrOccurrences(TComp elem) const {
    auto currentElementLink = elementsHead;
    auto currentFrequencyLink = frequencyHead;

    while (currentElementLink->linkedTo != nullptr) {
        auto nextElementLink = currentElementLink->linkedTo;
        auto nextFrequencyLink = currentFrequencyLink->linkedTo;

        if (nextElementLink->value == elem) {
            return nextFrequencyLink->value;
        }

        if (!relation(nextElementLink->value, elem)) {
            return 0;
        }

        currentElementLink = nextElementLink;
        currentFrequencyLink = nextFrequencyLink;
    }
    return 0;
}


int SortedBag::size() const {
    return length;
}


bool SortedBag::isEmpty() const {
    return length < 1;
}


SortedBagIterator SortedBag::iterator() const {
    return SortedBagIterator(*this);
}


SortedBag::~SortedBag() {
    auto currentElementLink = elementsHead;
    auto currentFrequencyLink = frequencyHead;

    while (currentElementLink->linkedTo != nullptr) {
        auto nextElementLink = currentElementLink->linkedTo;
        auto nextFrequencyLink = currentFrequencyLink->linkedTo;

        delete currentElementLink;
        delete currentFrequencyLink;

        currentElementLink = nextElementLink;
        currentFrequencyLink = nextFrequencyLink;
    }

    delete currentElementLink;
    delete currentFrequencyLink;
}
