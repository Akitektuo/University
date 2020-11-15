#include "Song.h"

Song::Song(int id, std::string artist, std::string title, int duration, std::string link)
{
	this->id = id;
	setArtist(artist);
	setTitle(title);
	setDuration(duration);
	setLink(link);
}

int Song::getId() const
{
	return id;
}

void Song::setArtist(std::string artist)
{
	this->artist = artist;
}

std::string Song::getArtist() const
{
	return artist;
}

void Song::setTitle(std::string title)
{
	this->title = title;
}

std::string Song::getTitle() const
{
	return title;
}

void Song::setDuration(int duration)
{
	this->duration = duration;
}

int Song::getDuration() const
{
	return duration;
}

void Song::setLink(std::string link)
{
	this->link = link;
}

std::string Song::getLink() const
{
	return link;
}

std::string Song::toString()
{
	return "Song(id=" + std::to_string(getId()) + ", artist=" + getArtist() + ", title=" + getTitle() + ", duration=" + std::to_string(getDuration()) + ", link=" + getLink() + ")";
}
