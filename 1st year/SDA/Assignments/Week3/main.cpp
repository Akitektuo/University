#include <iostream>
#include "SortedBag.h"
#include "SortedBagIterator.h"

using namespace std;

int main() {
    SortedBag bag([](TElem e1, TElem e2) {
        return e1 <= e2;
    });
    bag.add(5);
    bag.add(6);
    bag.add(0);
    bag.add(5);
    bag.add(10);
    bag.add(8);

//    for (auto it = bag.iterator(); it.valid(); it.next()) {
//        cout << it.getCurrent() << " ";
//    }

    auto it = bag.iterator();
    while (it.valid()) {
        cout << it.getCurrent() << " ";
        it.next();
    }

    return 0;
}
