#include <stdio.h>

typedef struct
{
	int x, y;
} Point;

int readArrayOfNumbers(int array[])
{
	int length = 0, isScanSuccessful = 1;

	while (isScanSuccessful)
	{
		isScanSuccessful = scanf("%d", &array[length++]);
	}

	return --length;
}

void printArrayOfNumbers(int array[], int from, int until)
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
	// If point1 equals point3, then the 3 points will form only one line because the two equal points form a single point
	// P1 (P1 == P3)
	//  *----------------* P2
	// P3 (P3 == P1)
	if (arePointsEqual(point1, point3))
	{
		return 0;
	}

	// Using the determinant method to check if 3 points are collinear
	// | x1 y1 1 |          'determinantSum'   'determinantDiference'
	// | x2 y2 1 | = 0 <=> x1y2 + x2y3 + x3y1 - (y1x2 + y2x3 + y3x1) = 0
	// | x3 y3 1 |            
	int determinantSum = point1->x * point2->y + point2->x * point3->y + point3->x * point1->y;
	int determinantDiference = point1->y * point2->x + point2->y * point3->x + point3->y * point1->x;
	
	// 'determinantSum'    'determinantDiference'
	// x1y2 + x2y3 + x3y1 = y1x2 + y2x3 + y3x1
	return determinantSum == determinantDiference;
}

void getLongestSequenceOfCollinearPoints(int arrayOfPoints[], int lengthOfPointArray, int* maxStart, int* maxEnd)
{
	int START_OF_3RD_POINT = 4, SIZE_OF_POINT = 2;
	*maxStart = 0;

	// If the array contains less than 2 points, no line is formed, so return the start-end interval [0, 0)
	if (lengthOfPointArray < START_OF_3RD_POINT)
	{
		*maxEnd = 0;
		return;
	}
	
	*maxEnd = START_OF_3RD_POINT;

	// If the array contains less than 3 points, one line is formed, so return the start-end interval [0, 4)
	if (lengthOfPointArray < START_OF_3RD_POINT + SIZE_OF_POINT)
	{
		return;
	}

	int maxLength = START_OF_3RD_POINT, start = 0, end = 0;

	// Itterate through points
	for (int i = START_OF_3RD_POINT; i < lengthOfPointArray - 1; i += SIZE_OF_POINT)
	{
		Point point1 = createPoint(arrayOfPoints[i - START_OF_3RD_POINT], arrayOfPoints[i - START_OF_3RD_POINT + 1]);
		Point point2 = createPoint(arrayOfPoints[i - SIZE_OF_POINT], arrayOfPoints[i - SIZE_OF_POINT + 1]);
		Point point3 = createPoint(arrayOfPoints[i], arrayOfPoints[i + 1]);

		// If points are not collinear, continue the loop
		if (!arePointsCollinear(&point1, &point2, &point3))
		{
			continue;
		}

		// If the last end position is the current index, increment end
		if (end == i)
		{
			end += SIZE_OF_POINT;
		}
		else // Else, reset start and end by pointing to the last 3 points
		{
			start = i - START_OF_3RD_POINT;
			end = i + SIZE_OF_POINT;
		}

		int currentLength = end - start;

		// Set the maximum length
		if (currentLength > maxLength)
		{
			maxLength = currentLength;
			*maxStart = start;
			*maxEnd = end;
		}
	}
}

int main()
{
	int pointArray[100] = { 0 }, lengthOfPointArray = 0;

	lengthOfPointArray = readArrayOfNumbers(pointArray);

	int maxStart = 0, maxEnd = 0;

	getLongestSequenceOfCollinearPoints(pointArray, lengthOfPointArray, &maxStart, &maxEnd);

	printArrayOfNumbers(pointArray, maxStart, maxEnd);
}