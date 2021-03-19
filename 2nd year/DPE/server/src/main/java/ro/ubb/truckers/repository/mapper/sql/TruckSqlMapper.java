package ro.ubb.truckers.repository.mapper.sql;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Truck;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TruckSqlMapper implements SqlMapper<Integer, Truck> {
    @Override
    public Truck mapResultSetToEntity(ResultSet resultSet) {
        try {
            var id = resultSet.getInt("id");
            var model = resultSet.getString("model");
            var licensePlate = resultSet.getString("licensePlate");
            var mileage = resultSet.getInt("mileage");
            var driverId = resultSet.getInt("driverId");
            var garageId = resultSet.getInt("garageId");

            return new Truck(id, model, licensePlate, mileage, driverId, garageId);
        } catch (SQLException exception) {
            throw new TruckersException("The result set is not a truck");
        }
    }

    @Override
    public String mapIdToString(Integer id) {
        return "id=" + id;
    }

    @Override
    public String mapAttributesToString() {
        return "(id, model, licensePlate, mileage, driverId, garageId)";
    }

    @Override
    public String mapAttributesAndValuesToString(Truck entity) {
        return String.format("(%s, '%s', '%s', '%s', '%s', '%s')", entity.getId(),
                entity.getModel(), entity.getLicensePlate(),
                entity.getMileage(), entity.getDriverId(), entity.getGarageId());
    }

    @Override
    public String mapEntityToUpdateString(Truck entity) {
        return String.format("model='%s', licensePlate='%s', mileage='%s', driverId='%s', garageId='%s'",
                entity.getModel(), entity.getLicensePlate(), entity.getMileage(),
                entity.getDriverId(), entity.getGarageId());
    }
}
