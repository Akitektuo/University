#include "TrenchCoatTableView.h"

TrenchCoatTableView::TrenchCoatTableView(ArrayList<TrenchCoat> trenchCoats, QObject* parent) : trenchCoats{ trenchCoats }, QAbstractTableModel{ parent } {}

int TrenchCoatTableView::rowCount(const QModelIndex& parent) const
{
	return trenchCoats.getSize();
}

int TrenchCoatTableView::columnCount(const QModelIndex& parent) const
{
	return 4;
}

QVariant TrenchCoatTableView::data(const QModelIndex& index, int role) const
{
    int row = index.row();
    int col = index.column();
    TrenchCoat trenchCoat = trenchCoats.get(row);
    if (role == Qt::DisplayRole)
    {
        switch (col)
        {
        case 0:
            return QString::fromStdString(trenchCoat.getName());
        case 1:
            return QString::fromStdString(trenchCoat.size);
        case 2:
            return QString::number(trenchCoat.price);
        case 3:
            return QString::fromStdString(trenchCoat.image);
        }
    }
    return {};
}

void TrenchCoatTableView::propagate()
{
	emit layoutChanged();
}
