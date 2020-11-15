#include <iostream>
#include "Set.h"
#include "SetIterator.h"

using namespace std;

int main() {
    Set test;
    for (int i = 0; i < 100; i++) {
        test.add(i);
    }
//    SetIterator it = test.iterator();
//    while (it.valid()) {
//        cout << it.getCurrent() << " ";
//        it.next();
//    }

    for (SetIterator it = test.iterator(); it.valid(); it.next()) {
        cout << it.getCurrent() << " ";
    }
    return 0;
}
