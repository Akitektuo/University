#include "Repository.h"

bool Repository::addSong(Song song, std::function<void(const Song&)> beforeAdd)
{
	for (const auto& savedSong : songs)
	{
		if (savedSong.getId() == song.getId())
		{
			return false;
		}
	}
	beforeAdd(song);
	songs.push_back(song);
}

bool Repository::removeSong(int id, std::function<void(const Song&)> beforeRemove)
{
	for (auto iterator = songs.begin(); iterator != songs.end(); iterator++)
	{
		if (iterator->getId() == id) {
			beforeRemove(*iterator);
			songs.erase(iterator);
			return true;
		}
	}
	return false;
}

bool Repository::updateSong(const Song& song, std::function<void(const Song&)> beforeUpdate)
{
	for (auto& savedSong : songs)
	{
		if (savedSong.getId() == song.getId())
		{
			beforeUpdate(savedSong);
			savedSong.setArtist(song.getArtist());
			savedSong.setDuration(song.getDuration());
			savedSong.setLink(song.getLink());
			savedSong.setTitle(song.getTitle());
			return true;
		}
	}
	return false;
}

const std::vector<Song>& Repository::getAllSongs() const
{
	return songs;
}
