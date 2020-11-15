#pragma once

#include <vector>
#include <utility>
#include <algorithm>
#include <functional>
//DO NOT INCLUDE MultiMapIterator

//DO NOT CHANGE THIS PART
typedef int TKey;
typedef int TValue;
typedef std::pair<TKey, TValue> TElem;

constexpr auto INITIAL_CAPACITY = 16;

typedef struct {
    std::vector<TValue> values = {};
    TKey key = {};
    int next = -1;
} ListElement;

template<typename T>
struct SLLAElement {
    T payload = {};
    int next = -1;
};

template<typename T>
struct SLLA {
    SLLAElement<T> *elements = nullptr;
    int capacity = INITIAL_CAPACITY;
    int size = 0;
    int valueHead = -1;
    int freeHead = 0;
};

typedef struct {
    SLLA<TValue> values = {};
    TKey key = {};
} KeyElement;

#define NULL_TVALUE -11111;
#define NULL_TELEM pair<int,int>(-11111, -11111);

class MultiMapIterator;

class MultiMap {
    friend class MultiMapIterator;

private:
//    ListElement *array;
//    int capacity, keysHead, emptyHead, length;
    SLLA<KeyElement> keyList;
    int totalSize;

    template<typename T>
    static SLLA<T> createSLLA() {
        SLLA<T> list;
        list.elements = new SLLAElement<T>[list.capacity];
        for (auto i = 1; i < list.capacity; i++) {
            list.elements[i - 1].next = i;
        }
        return list;
    }

    template<typename T>
    static void destroySLLA(SLLA<T> &list) {
        if (list.elements) {
            delete[] list.elements;
            list.elements = nullptr;
        }
        list.capacity = INITIAL_CAPACITY;
        list.size = 0;
        list.valueHead = 0;
        list.freeHead = 0;
    }

    template<typename T>
    static bool isSLLANull(const SLLA<T> &list) {
        return list.elements == nullptr;
    }

    template <typename T>
    static void addPayload(SLLA<T>& list, T payload) {
        auto firstEmptyIndex = list.freeHead;
        list.size++;

        /// If empty ///
        if (list.valueHead < 0) {
            list.freeHead = list.elements[firstEmptyIndex].next;
            list.valueHead = firstEmptyIndex;
            list.elements[firstEmptyIndex].next = -1;

            list.elements[firstEmptyIndex].payload = payload;
            return;
        }

        /// Get last value index ///
        auto lastValueIndex = -1;

        for (auto i = list.valueHead; i > -1; i = list.elements[i].next) {
            lastValueIndex = i;
        }

        /// If resize needed ///
        if(firstEmptyIndex < 0) {
            auto oldCapacity = list.capacity;
            list.capacity *= 2;

            auto tempElements = new SLLAElement<T>[list.capacity];
            for (int i = 0; i < list.capacity - 1; i++) {
                if (i < oldCapacity) {
                    tempElements[i] = list.elements[i];
                } else {
                    tempElements[i].next = i + 1;
                }
            }
            delete[] list.elements;
            list.elements = tempElements;

            list.freeHead = oldCapacity + 1;
            list.elements[lastValueIndex].next = oldCapacity;
            list.elements[oldCapacity].next = -1;
            list.elements[oldCapacity].payload = payload;
            return;
        }

        list.freeHead = list.elements[firstEmptyIndex].next;
        list.elements[lastValueIndex].next = firstEmptyIndex;
        list.elements[firstEmptyIndex].next = -1;
        list.elements[firstEmptyIndex].payload = payload;
    }

    template <typename T>
    static int getElementIndex(const SLLA<T>& list, const std::function<bool(const SLLAElement<T>&)>& selection, int* previousIndex = nullptr) {
        for (auto i = list.valueHead; i > -1; i = list.elements[i].next) {
            if (selection(list.elements[i])) {
                return i;
            }
            if (previousIndex != nullptr) {
                *previousIndex = i;
            }
        }
        return -1;
    }

    template <typename T>
    static void relink(SLLA<T>& list, int previous, int current, int next) {
        list.size--;
        if (previous > -1) {
            list.elements[previous].next = next;
        } else {
            list.valueHead = next;
        }

        list.elements[current].next = list.freeHead;
        list.freeHead = current;
    }

public:
    //constructor
    MultiMap();

    //adds a key value pair to the multimap
    void add(TKey c, TValue v);

    //removes a key value pair from the multimap
    //returns true if the pair was removed (if it was in the multimap) and false otherwise
    bool remove(TKey c, TValue v);

    std::vector<TValue> removeKey(TKey key);

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

