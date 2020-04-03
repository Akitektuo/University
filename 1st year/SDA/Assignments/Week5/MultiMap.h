#pragma once
#include<vector>
#include<utility>
//DO NOT INCLUDE MultiMapIterator

//DO NOT CHANGE THIS PART
typedef int TKey;
typedef int TValue;
typedef std::pair<TKey, TValue> TElem;

typedef struct {
    std::vector<TValue> values = {};
    TKey key = {};
    int next = -1;
} ListElement;

constexpr auto INITIAL_CAPACITY = 16;

#define NULL_TVALUE -11111;
#define NULL_TELEM pair<int,int>(-11111, -11111);

class MultiMapIterator;

class MultiMap {
	friend class MultiMapIterator;

private:
    ListElement* array;
    int capacity, keysHead, emptyHead, length;

public:
	//constructor
	MultiMap();

	//adds a key value pair to the multimap
	void add(TKey c, TValue v);

	//removes a key value pair from the multimap
	//returns true if the pair was removed (if it was in the multimap) and false otherwise
	bool remove(TKey c, TValue v);

	//returns the vector of values associated to a key. If the key is not in the MultiMap, the vector is empty
	std::vector<TValue> search(TKey c) const;

	//returns the number of pairs from the multimap
	int size() const;

	//checks whether the multimap is empty
	bool isEmpty() const;

	//returns an iterator for the multimap
	MultiMapIterator iterator() const;

	//destructor
	~MultiMap();


};

