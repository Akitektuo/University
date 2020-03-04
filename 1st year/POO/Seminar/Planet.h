#pragma once

typedef struct
{
	char name[50];
	char type[50];
	double distanceFromEarth;
} Planet;

Planet createPlanet(char name[], char type[], double distance);

double getDistance(Planet planet);

char* getName(Planet* planet);

void testPlanet();