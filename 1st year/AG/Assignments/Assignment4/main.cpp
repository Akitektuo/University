#include <iostream>

constexpr auto NUMBER_OF_VERTICES = 5;

int graph[NUMBER_OF_VERTICES][NUMBER_OF_VERTICES] = {
    {0,  9,  75, 0,  0},
    {9,  0,  95, 19, 42},
    {75, 95, 0,  51, 66},
    {0,  19, 51, 0,  31},
    {0,  42, 66, 31, 0}
};

int main() {
    int numberOfEdge = 0;
    bool selected[NUMBER_OF_VERTICES] = { false };

    selected[0] = true;

    std::cout << "Edge : Weight\n";
    while (numberOfEdge < NUMBER_OF_VERTICES - 1) {

        int min = 9999999;
        int row = 0;
        int column = 0;

        for (int i = 0; i < NUMBER_OF_VERTICES; i++) {
            if (!selected[i]) {
                continue;
            }
            for (int j = 0; j < NUMBER_OF_VERTICES; j++) {
                if (!selected[j] && graph[i][j] && min > graph[i][j]) {
                    min = graph[i][j];
                    row = i;
                    column = j;
                }
            }
        }
        std::cout << row <<  " - " << column << " :  " << graph[row][column] << "\n";
        selected[column] = true;
        numberOfEdge++;
    }

    return 0;
}
