#include "ActionTracker.h"
#include <stdlib.h>

Action* createAction(Offer* offer, int type)
{
	Action* newAction = (Action*) malloc(sizeof(Action));
	if (newAction == NULL)
	{
		return NULL;
	}
	newAction->type = type;
	newAction->offer = createOfferCopy(offer);
	return newAction;
}

Offer* getOfferFromAction(Action* action)
{
	return action->offer;
}

int getTypeFromAction(Action* action)
{
	return action->type;
}

void destroyAction(Action* action)
{
	free(action);
}

Tracker* createTracker()
{
	Tracker* tracker = (Tracker*) malloc(sizeof(Tracker));
	if (tracker == NULL)
	{
		return NULL;
	}

	tracker->undoActionsSize = 0;
	tracker->redoActionsSize = 0;

	tracker->undoActionsMaxSize = MINIMUM_UNDO_ACTIONS_SIZE;
	tracker->redoActionsMaxSize = MINIMUM_REDO_ACTIONS_SIZE;

	tracker->undoActions = (Action**) malloc(tracker->undoActionsMaxSize * sizeof(Action*));
	tracker->redoActions = (Action**) malloc(tracker->redoActionsMaxSize * sizeof(Action*));

	return tracker;
}

void assureUndoCapacity(Tracker* tracker)
{
	if (tracker->undoActionsSize < tracker->undoActionsMaxSize)
	{
		return;
	}

	tracker->undoActionsMaxSize *= 2;
	Action** reallocatedMemory = (Action**) realloc(tracker->undoActions, tracker->undoActionsMaxSize * sizeof(Action*));

	if (reallocatedMemory == NULL)
	{
		return;
	}

	tracker->undoActions = reallocatedMemory;
}

void trackAdd(Tracker* tracker, Offer* offerToBeAdded)
{
	assureUndoCapacity(tracker);
	tracker->undoActions[tracker->undoActionsSize++] = createAction(offerToBeAdded, ACTION_DELETE);
}

void trackEdit(Tracker* tracker, Offer* offerToBeEdited)
{
	assureUndoCapacity(tracker);
	tracker->undoActions[tracker->undoActionsSize++] = createAction(offerToBeEdited, ACTION_EDIT);
}

void trackDelete(Tracker* tracker, Offer* offerToBeDeleted)
{
	assureUndoCapacity(tracker);
	tracker->undoActions[tracker->undoActionsSize++] = createAction(offerToBeDeleted, ACTION_ADD);
}

Action* getAvailableUndo(Tracker* tracker)
{
	if (tracker->undoActionsSize < 1)
	{
		return NULL;
	}
	return tracker->undoActions[--tracker->undoActionsSize];
}

void assureRedoCapacity(Tracker* tracker)
{
	if (tracker->redoActionsSize < tracker->redoActionsMaxSize)
	{
		return;
	}

	tracker->redoActionsMaxSize *= 2;
	Action** reallocatedMemory = (Action**)realloc(tracker->redoActions, tracker->redoActionsMaxSize * sizeof(Action*));

	if (reallocatedMemory == NULL)
	{
		return;
	}

	tracker->redoActions = reallocatedMemory;
}

void trackUndoAdd(Tracker* tracker, Offer* offerToBeAdded)
{
	assureRedoCapacity(tracker);
	tracker->redoActions[tracker->redoActionsSize++] = createAction(offerToBeAdded, ACTION_DELETE);
}

void trackUndoEdit(Tracker* tracker, Offer* offerToBeEdited)
{
	assureRedoCapacity(tracker);
	tracker->redoActions[tracker->redoActionsSize++] = createAction(offerToBeEdited, ACTION_EDIT);
}

void trackUndoDelete(Tracker* tracker, Offer* offerToBeDeleted)
{
	assureRedoCapacity(tracker);
	tracker->redoActions[tracker->redoActionsSize++] = createAction(offerToBeDeleted, ACTION_ADD);
}

Action* getAvailableRedo(Tracker* tracker)
{
	if (tracker->redoActionsSize < 1)
	{
		return NULL;
	}
	return tracker->redoActions[--tracker->redoActionsSize];
}

void resetRedoActions(Tracker* tracker)
{
	for (int i = 0; i < tracker->redoActionsSize; i++)
	{
		destroyAction(tracker->redoActions[i]);
	}
	tracker->redoActionsSize = 0;
}

void destroyTracker(Tracker* tracker)
{
	for (int i = 0; i < tracker->undoActionsSize; i++)
	{
		destroyAction(tracker->undoActions[i]);
	}
	free(tracker->undoActions);

	resetRedoActions(tracker);
	free(tracker->redoActions);

	free(tracker);
}
