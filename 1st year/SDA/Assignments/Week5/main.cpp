#include <iostream>
#include "MultiMap.h"
#include "MultiMapIterator.h"

int main() {
    MultiMap multiMap;
    multiMap.add(1, 0);
//    multiMap.add(1, 0);
    multiMap.add(2, 0);
    multiMap.add(3, 0);
    multiMap.add(4, 0);
    multiMap.add(5, 0);
    multiMap.add(6, 0);
    multiMap.add(7, 0);
    multiMap.add(8, 0);
    multiMap.add(9, 0);
//    multiMap.add(10, 0);
//    multiMap.add(11, 0);
//    multiMap.add(12, 0);
//    multiMap.add(13, 0);
//    multiMap.add(14, 0);
//    multiMap.add(15, 0);
//    multiMap.add(16, 0);
//    multiMap.add(17, 0);

    auto count = 0;
    for (auto it = multiMap.iterator(); it.valid(); it.next()) {
        count++;
        auto val = it.getCurrent();
        std::cout << val.first << " " << val.second << "\n";
    }
    std::cout << count << "/" << multiMap.size();
    return 0;
}
