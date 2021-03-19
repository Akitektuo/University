package ro.ubb.truckers.repository.mapper.sql;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Delivery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliverySqlMapper implements SqlMapper<Integer, Delivery> {
    @Override
    public Delivery mapResultSetToEntity(ResultSet resultSet) {
        try {
            var id = resultSet.getInt("id");
            var origin = resultSet.getString("origin");
            var destination = resultSet.getString("destination");
            var distance = resultSet.getInt("distance");
            var delivered = resultSet.getBoolean("delivered");
            var load = resultSet.getString("load");
            var truckId = resultSet.getInt("truckId");
            var companyId = resultSet.getInt("companyId");

            return new Delivery(id, origin, destination, distance, delivered, load, truckId, companyId);
        } catch (SQLException exception) {
            throw new TruckersException("The result set is not a delivery.");
        }
    }

    @Override
    public String mapIdToString(Integer id) {
        return "id=" + id;
    }

    @Override
    public String mapAttributesToString() {
        return "(id, origin, destination, distance, delivered, load, truckId, companyId)";
    }

    @Override
    public String mapAttributesAndValuesToString(Delivery entity) {
        return String.format("(%s, '%s', '%s', '%s', '%s', '%s', %s, %s)", entity.getId(),
                entity.getOrigin(), entity.getDestination(),
                entity.getDistance(), entity.isDelivered(), entity.getLoad(),
                entity.getTruckId(), entity.getCompanyId());
    }

    @Override
    public String mapEntityToUpdateString(Delivery entity) {
        return String.format("origin='%s', destination='%s', distance='%s', delivered='%s', " +
                        "load='%s', truckId='%s', companyId='%s'", entity.getOrigin(), entity.getDestination(),
                entity.getDistance(), entity.isDelivered(), entity.getLoad(),
                entity.getTruckId(), entity.getCompanyId());
    }
}
