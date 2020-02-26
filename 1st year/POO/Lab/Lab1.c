#include <stdio.h>

int readArray(int arr[])
{
	int len = 0, result = 1;

	while (result)
	{
		result = scanf("%d", &arr[len++]);
	}

	return --len;
}

void printArray(int arr[], int from, int until)
{
	for (int i = from; i < until; i++)
	{
		printf("%d ", arr[i]);
	}
	printf("\n");
}

int arePointsCollinear(int x1, int y1, int x2, int y2, int x3, int y3)
{
	if (x1 == x3 && y1 == y3)
	{
		return 0;
	}

	int detSum = x1 * y2 + x2 * y3 + x3 * y1;
	int detDif = y1 * x2 + y2 * x3 + y3 * x1;
	
	return detSum == detDif;
}



int main()
{
	int arr[100] = { 0 }, len = 0;

	len = readArray(arr);

	if (len < 4)
	{
		return 0;
	}

	if (len < 6)
	{
		printArray(arr, 0, 4);
	}

	int maxLength = 4, maxStart = 0, maxEnd = 4, start = 0, end = 0;

	for (int i = 4; i < len - 1; i += 2)
	{
		if (!arePointsCollinear(arr[i - 4], arr[i - 3], arr[i - 2], arr[i - 1], arr[i], arr[i + 1]))
		{
			continue;
		}

		if (end == i)
		{
			end += 2;
		}
		else 
		{
			start = i - 4;
			end = i + 2;
		}

		int currentLength = end - start;

		if (currentLength > maxLength)
		{
			maxLength = currentLength;
			maxStart = start;
			maxEnd = end;
		}
	}

	printArray(arr, maxStart, maxEnd);
}