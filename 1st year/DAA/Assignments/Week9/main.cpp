#include <iostream>

#include "SortedBag.h"
#include "SortedBagIterator.h"

using namespace std;

int main() {
    SortedBag sortedBag { [](TComp a, TComp b) { return a <= b; } };
    for (int i = 10; i > 0; i--) {
        sortedBag.add(i);
    }
    cout << sortedBag.size() << "\n";
    for (auto it = sortedBag.iterator(); it.valid(); it.next()) {
        cout << it.getCurrent() << " ";
    }
    return 0;
}
