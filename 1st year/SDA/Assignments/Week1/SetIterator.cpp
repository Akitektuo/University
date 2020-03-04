#include "SetIterator.h"
#include "Set.h"


SetIterator::SetIterator(const Set& m) : set(m)
{
    index = 0;
}


void SetIterator::first() {
	index = 0;
}


void SetIterator::next() {
	if (valid()) {
	    index++;
	}
}


TElem SetIterator::getCurrent()
{
	return set.array[index];
}

bool SetIterator::valid() const {
	return 0 <= index && index < set.length;
}



