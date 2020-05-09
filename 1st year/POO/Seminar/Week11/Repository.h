#pragma once

#include <vector>
#include <functional>
#include "Song.h"

class Repository
{
private:
	std::vector<Song> songs;

public:
	bool addSong(Song song, std::function<void(const Song&)> beforeAdd = {});
	bool removeSong(int id, std::function<void(const Song&)> beforeRemove = {});
	bool updateSong(const Song& song, std::function<void(const Song&)> beforeUpdate = {});
	const std::vector<Song>& getAllSongs() const;
};

