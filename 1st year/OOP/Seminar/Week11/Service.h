#pragma once

#include <memory>
#include <utility>
#include "Repository.h"
#include "Action.h"
#include "ActionAdd.h"
#include "ActionRemove.h"
#include "ActionUpdate.h"

class Service
{
private:
	Repository repository;
	std::vector<std::unique_ptr<Action>> undoStack;
	std::vector<std::unique_ptr<Action>> redoStack;

public:
	bool addSong(int id, std::string artist, std::string title, int duration, std::string link);
	bool removeSong(int id);
	bool updateSong(int oldId, std::string newArtist, std::string newTitle, int newDuration, std::string newLink);
	const std::vector<Song>& getAllSongs() const;
	void undo();
	void redo();
};
