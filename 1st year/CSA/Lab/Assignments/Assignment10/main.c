#include <stdio.h>

void mapString(char initial[], char result[]);

char table[] = "OPQRSTUVWXYZABCDEFGHIJKLMN";

int main()
{
    char strInitial[101] = "";
    char strRes[101] = "";

    printf("Give array (without spaces) to map:\n");

    scanf("%s", strInitial);

    mapString(strInitial, strRes);

    printf("The mapped array is: %s\n", strRes);
    return 0;
}