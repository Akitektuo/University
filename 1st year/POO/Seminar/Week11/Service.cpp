#include "Service.h"

bool Service::addSong(int id, std::string artist, std::string title, int duration, std::string link)
{
	return repository.addSong({ id, artist, title, duration, link }, [&](const Song& songBeforeAdd) {
		auto actionAdd = std::make_unique<ActionAdd>(repository);

		actionAdd->setAddedSong(songBeforeAdd);

		undoStack.push_back(std::move(actionAdd));
		redoStack.clear();
	});
}

bool Service::removeSong(int id)
{
	return repository.removeSong(id, [&](const Song& songBeforeRemove) {
		auto actionRemove = std::make_unique<ActionRemove>(repository);

		actionRemove->setDeletedSong(songBeforeRemove);

		undoStack.push_back(std::move(actionRemove));
		redoStack.clear();
	});
}

bool Service::updateSong(int oldId, std::string newArtist, std::string newTitle, int newDuration, std::string newLink)
{
	Song songToUpdate { oldId, newArtist, newTitle, newDuration, newLink };

	return repository.updateSong(songToUpdate, [&](const Song& songBeforeEdit) {
		auto actionUpdate = std::make_unique<ActionUpdate>(repository);

		actionUpdate->setSongBeforeUpdate(songBeforeEdit);
		actionUpdate->setSongAfterUpdate(songToUpdate);

		undoStack.push_back(std::move(actionUpdate));
		redoStack.clear();
	});
}

const std::vector<Song>& Service::getAllSongs() const
{
	return repository.getAllSongs();
}

void Service::undo()
{
	if (undoStack.empty())
	{
		return;
	}
	auto undoAction = std::move(undoStack.back());
	undoStack.pop_back();

	undoAction->executeUndo();

	redoStack.push_back(std::move(undoAction));
	
}

void Service::redo()
{
	if (redoStack.empty())
	{
		return;
	}
	auto redoAction = std::move(redoStack.back());
	redoStack.pop_back();

	redoAction->executeUndo();

	undoStack.push_back(std::move(redoAction));
}
