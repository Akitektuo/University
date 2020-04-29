#include <iostream>
#include "SortedMultiMap.h"
#include "SMMIterator.h"

int main() {
    SortedMultiMap map {[](TKey k1, TKey k2) { return k1 <= k2; }};
    map.add(1, 2);
    map.add(101, 3);
    map.add(80, 4);
    map.add(159, 5);
    map.add(80, 6);

//    auto res = map.search(80);
//    std::cout << "Size: " << map.size() << "\n";
//    for (auto r : res) {
//        std::cout << r << " ";
//    }
    auto res = map.removeKey(80);
    for (auto r: res) {
        cout << r << " ";
    }
    cout << "\n";
    for (auto it = map.iterator(); it.valid(); it.next()) {
        cout << it.getCurrent().first << " -> " << it.getCurrent().second << "\n";
    }
    return 0;
}
