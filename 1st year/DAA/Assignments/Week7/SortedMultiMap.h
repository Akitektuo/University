#pragma once
//DO NOT INCLUDE SMMITERATOR

//DO NOT CHANGE THIS PART
#include <vector>
#include <utility>

typedef int TKey;
typedef int TValue;
typedef std::pair<TKey, TValue> TElem;
#define NULL_TVALUE -11111
#define NULL_TELEM pair<TKey, TValue>(-11111, -11111);
using namespace std;

class SMMIterator;

typedef bool(*Relation)(TKey, TKey);


class SortedMultiMap {
    friend class SMMIterator;

private:
    static constexpr auto HASH_KEY = 79;
//    static constexpr auto HASH_KEY = 7;

    struct Node {
        TKey key{};
        TValue value{};
        Node *next = nullptr;
    };

    int totalSize;
    int maxSize;
    Relation relation;
    Node **addresses;

    Node *createNode(TKey key, TValue value, Node* nextNode);

    bool isNumberPrime(int number);

    void computeNextSize();

    void addNodeBack(Node* node);

    void resizeIfNeeded();

    int hashElement(TKey key) const;

public:

    // constructor
    SortedMultiMap(Relation r);

    //adds a new key value pair to the sorted multi map
    void add(TKey c, TValue v);

    //returns the values belonging to a given key
    vector<TValue> search(TKey c) const;

    //removes a key value pair from the sorted multimap
    //returns true if the pair was removed (it was part of the multimap), false if nothing is removed
    bool remove(TKey c, TValue v);

    //removes a key together with all its values
    //returns a vector with the values that were previously associated to this value (and were removed)
    vector<TValue> removeKey(TKey key);

    //returns the number of key-value pairs from the sorted multimap
    int size() const;

    //verifies if the sorted multi map is empty
    bool isEmpty() const;

    // returns an iterator for the sorted multimap. The iterator will returns the pairs as required by the relation (given to the constructor)	
    SMMIterator iterator() const;

    // destructor
    ~SortedMultiMap();
};
