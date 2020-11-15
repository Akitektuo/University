#pragma once
#include "Offer.h"

#define ACTION_ADD 0
#define ACTION_EDIT 1
#define ACTION_DELETE 2

#define MINIMUM_UNDO_ACTIONS_SIZE 16
#define MINIMUM_REDO_ACTIONS_SIZE 8

typedef struct
{
	Offer* offer;
	int type;
} Action;

Action* createAction(Offer* offer, int type);

Offer* getOfferFromAction(Action* action);

int getTypeFromAction(Action* action);

void destroyAction(Action* action);

typedef struct
{
	int undoActionsSize;
	int undoActionsMaxSize;
	Action** undoActions;

	int redoActionsSize;
	int redoActionsMaxSize;
	Action** redoActions;
} Tracker;

Tracker* createTracker();

void trackAdd(Tracker* tracker, Offer* offerToBeAdded);

void trackEdit(Tracker* tracker, Offer* offerToBeEdited);

void trackDelete(Tracker* tracker, Offer* offerToBeDeleted);

Action* getAvailableUndo(Tracker* tracker);

void trackUndoAdd(Tracker* tracker, Offer* offerToBeAdded);

void trackUndoEdit(Tracker* tracker, Offer* offerToBeEdited);

void trackUndoDelete(Tracker* tracker, Offer* offerToBeDeleted);

Action* getAvailableRedo(Tracker* tracker);

void resetRedoActions(Tracker* tracker);

void destroyTracker(Tracker* tracker);