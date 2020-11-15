#pragma once
//DO NOT INCLUDE SORTEDBAGITERATOR

#include <functional>

//DO NOT CHANGE THIS PART
typedef int TComp;
typedef TComp TElem;
typedef bool(*Relation)(TComp, TComp);
#define NULL_TCOMP -11111;

struct Node {
    TComp value = 0;
    Node* left = nullptr;
    Node* right = nullptr;
};

class SortedBagIterator;

class SortedBag {
	friend class SortedBagIterator;

private:
    Relation relation;
    Node* tree;
    int length;

    Node* createNode(int value = 0, Node* left = nullptr, Node* right = nullptr);

    void addValueToTree(Node* root, TComp value);
    bool doesContain(Node* root, TComp value) const;
    Node* findParent(Node* root, TComp value) const;
    Node* findNode(Node* root, TComp value) const;
    Node* findMin(Node* root);
    void countValue(Node* root, TComp value, int& count) const;
    void removeNode(Node*& node);
    void destroyTree(Node* root);

public:
	//constructor
	SortedBag(Relation r);

	//adds an element to the sorted bag
	void add(TComp e);

	//removes one occurrence of an element from a sorted bag
	//returns true if an element was removed, false otherwise (if e was not part of the sorted bag)
	bool remove(TComp e);

	//checks if an element appearch is the sorted bag
	bool search(TComp e) const;

	//returns the number of occurrences for an element in the sorted bag
	int nrOccurrences(TComp e) const;

	//returns the number of elements from the sorted bag
	int size() const;

	//returns an iterator for this sorted bag
	SortedBagIterator iterator() const;

	//checks if the sorted bag is empty
	bool isEmpty() const;

	//destructor
	~SortedBag();
};