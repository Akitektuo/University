#pragma once

#include <string>

class Song
{
private:
	int id;
	std::string artist;
	std::string title;
	int duration; // In seconds
	std::string link;

public:
	Song(int id, std::string artist="", std::string title="", int duration=0, std::string link="");

	int getId() const;

	void setArtist(std::string artist);
	std::string getArtist() const;

	void setTitle(std::string title);
	std::string getTitle() const;

	void setDuration(int duration);
	int getDuration() const;

	void setLink(std::string link);
	std::string getLink() const;

	std::string toString();
};

