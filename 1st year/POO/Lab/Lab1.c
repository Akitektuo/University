#include <stdio.h>

typedef struct
{
	int x, y;
} Point;

int readArray(int array[])
{
	int length = 0, isScanSuccessful = 1;

	while (isScanSuccessful)
	{
		isScanSuccessful = scanf("%d", &array[length++]);
	}

	return --length;
}

void printArray(int array[], int from, int until)
{
	for (int i = from; i < until; i++)
	{
		printf("%d ", array[i]);
	}
	printf("\n");
}

Point createPoint(int x, int y)
{
	Point newPoint;
	newPoint.x = x;
	newPoint.y = y;
	return newPoint;
}

int arePointsEqual(Point* pointA, Point* pointB)
{
	return pointA->x == pointB->x && pointA->y == pointB->y;
}

int arePointsCollinear(Point* point1, Point* point2, Point* point3)
{
	if (arePointsEqual(point1, point3))
	{
		return 0;
	}

	// Using the determinant to check if 3 points are collinear
	int determinantSum = point1->x * point2->y + point2->x * point3->y + point3->x * point1->y;
	int determinantDiference = point1->y * point2->x + point2->y * point3->x + point3->y * point1->x;
	
	return determinantSum == determinantDiference;
}

int main()
{
	int pointArray[100] = { 0 }, lengthOfPointArray = 0;

	lengthOfPointArray = readArray(pointArray);

	int START_OF_3RD_POINT = 4, SIZE_OF_POINT = 2;

	if (lengthOfPointArray < START_OF_3RD_POINT)
	{
		return 0;
	}

	if (lengthOfPointArray < START_OF_3RD_POINT + SIZE_OF_POINT)
	{
		printArray(pointArray, 0, START_OF_3RD_POINT);
	}

	
	int maxLength = START_OF_3RD_POINT, maxStart = 0, maxEnd = START_OF_3RD_POINT, start = 0, end = 0;

	for (int i = START_OF_3RD_POINT; i < lengthOfPointArray - 1; i += SIZE_OF_POINT)
	{
		Point point1 = createPoint(pointArray[i - START_OF_3RD_POINT], pointArray[i - START_OF_3RD_POINT + 1]);
		Point point2 = createPoint(pointArray[i - SIZE_OF_POINT], pointArray[i - SIZE_OF_POINT + 1]);
		Point point3 = createPoint(pointArray[i], pointArray[i + 1]);

		if (!arePointsCollinear(&point1, &point2, &point3))
		{
			continue;
		}

		if (end == i)
		{
			end += SIZE_OF_POINT;
		}
		else 
		{
			start = i - START_OF_3RD_POINT;
			end = i + SIZE_OF_POINT;
		}

		int currentLength = end - start;

		if (currentLength > maxLength)
		{
			maxLength = currentLength;
			maxStart = start;
			maxEnd = end;
		}
	}

	printArray(pointArray, maxStart, maxEnd);
}