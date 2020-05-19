#pragma once
#include "FileRepository.h"
class HtmlRepository : public FileRepository
{
private:
	const std::string DELIMITER = "</td><td>";

	const int START_OF_DATA = 8;
	const int END_OF_DATA = 18;

	const int START_OF_IMAGE_DATA = 10;
	const int END_OF_IMAGE_DATA = 12;

	const std::string START_OF_HTML = "<table border=\"1\"><tr><th>Name</th><th>Size</th><th>Price</th><th>Image</th></tr>\n";
	const std::string END_OF_HTML = "</table>";
	const std::string START_OF_LINE = "<tr><td>";
	const std::string END_OF_LINE = "\"></td></tr>\n";
	const std::string START_OF_IMAGE = "<img src=\"";

	bool endsWith(std::string string, std::string withString) const;

public:
	virtual ArrayList<TrenchCoat> getTrenchCoatArrayListFromFile() const override;
	virtual void saveTrenchCoatArrayListToFile(ArrayList<TrenchCoat> trenchCoats) const override;
};

