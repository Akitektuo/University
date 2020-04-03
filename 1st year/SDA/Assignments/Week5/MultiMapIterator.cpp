#include "MultiMapIterator.h"
#include "MultiMap.h"


MultiMapIterator::MultiMapIterator(const MultiMap& c): col(c) {
	this->currentPos = 0;
}

TElem MultiMapIterator::getCurrent() const{
	pair<TKey, TValue> elem;
	elem.first = col.elems[this->currentPos].first;
	elem.second =col.elems[this->currentPos].second;
	return elem;
}

bool MultiMapIterator::valid() const {
	return this->currentPos < this->col.len;
}

void MultiMapIterator::next() {
	this->currentPos++;
}

void MultiMapIterator::first() {
	this->currentPos = 0;
}

