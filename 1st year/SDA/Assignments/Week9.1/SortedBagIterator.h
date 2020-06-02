#pragma once
#include "SortedBag.h"

class SortedBag;

class SortedBagIterator
{
	friend class SortedBag;

private:
	const SortedBag& bag;
	SortedBagIterator(const SortedBag& b);
	int iterated;
	TComp current;

    void iterateTree(Node* root, const std::function<void(Node *&)>& action, bool &shouldStop);
//    void iterateTree(Node* root, const std::function<void(Node *&)>& action);

public:
	TComp getCurrent();
	bool valid();
	void next();
	void first();
};

