#include <iostream>
#include "SortedMultiMap.h"
#include "SMMIterator.h"

int main() {
//    SortedMultiMap map{[](TKey k1, TKey k2) { return k1 <= k2; }};
//    map.add(1, 2);
//    map.add(101, 3);
//    map.add(80, 4);
//    map.add(159, 5);
//    map.add(80, 6);

//    for (int i = 0; i < 100; i++) {
//        map.add(i * 10, i + 1);
//    }

//    auto res = map.search(80);
//    std::cout << "Size: " << map.size() << "\n";
//    for (auto r : res) {
//        std::cout << r << " ";
//    }
//    auto res = map.removeKey(80);
//    for (auto r : res) {
//        cout << r << " ";
//    }
//    cout << "\n";
//    auto count = 0;
//    for (auto it = map.iterator(); it.valid(); it.next()) {
//        count++;
//        if (count == 85) {
//            map.size();
//        }
//        cout << it.getCurrent().first << " -> " << it.getCurrent().second << " => " << count << "\n";
//    }
    auto a = 1;
    auto b = &a;
    auto c = b;
    (*c)++;
    cout << a << " " << *b << " " << *c;
    return 0;
}
