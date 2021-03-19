package ro.ubb.truckers.repository.mapper.sql;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Garage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GarageSqlMapper implements SqlMapper<Integer, Garage> {
    @Override
    public Garage mapResultSetToEntity(ResultSet resultSet) {
        try {
            var id = resultSet.getInt("id");
            var location = resultSet.getString("location");
            var capacity = resultSet.getInt("capacity");
            var allocatedTrucks = resultSet.getInt("allocatedTrucks");
            var companyId = resultSet.getInt("companyId");

            return new Garage(id, location, capacity, allocatedTrucks, companyId);
        } catch (SQLException exception) {
            throw new TruckersException("The result set is not a garage.");
        }
    }

    @Override
    public String mapIdToString(Integer id) {
        return "id=" + id;
    }


    @Override
    public String mapAttributesToString() {
        return "(id, location, capacity, allocatedTrucks, companyId)";
    }

    @Override
    public String mapAttributesAndValuesToString(Garage entity) {
        return String.format("(%s, '%s', '%s', '%s', '%s')", entity.getId(), entity.getLocation(), entity.getCapacity(),
                entity.getAllocatedTrucks(), entity.getCompanyId());
    }

    @Override
    public String mapEntityToUpdateString(Garage entity) {
        return String.format("location='%s', capacity='%s', allocatedTrucks='%s', companyId='%s'",
                entity.getLocation(), entity.getCapacity(), entity.getAllocatedTrucks(), entity.getCompanyId());
    }
}
