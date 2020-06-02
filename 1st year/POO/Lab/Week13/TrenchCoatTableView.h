#pragma once

#include <QtCore/QAbstractTableModel>
#include <QtWidgets/QTableView>
#include "ArrayList.h"
#include "TrenchCoat.h"

class TrenchCoatTableView : public QAbstractTableModel
{
private:
	ArrayList<TrenchCoat> trenchCoats;

public:
	TrenchCoatTableView(ArrayList<TrenchCoat> trenchCoats, QObject* parent = nullptr);

	int rowCount(const QModelIndex& parent) const override;

	int columnCount(const QModelIndex& parent) const override;

	QVariant data(const QModelIndex& index, int role) const override;

	void propagate();
};

