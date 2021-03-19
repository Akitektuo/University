package ro.ubb.truckers.repository.mapper.sql;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Company;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanySqlMapper implements SqlMapper<Integer, Company> {

    @Override
    public Company mapResultSetToEntity(ResultSet resultSet) {
        try {
            var id = resultSet.getInt("id");
            var name = resultSet.getString("name");

            return new Company(id, name);
        } catch (SQLException exception) {
            throw new TruckersException("The result set is not a company");
        }
    }

    @Override
    public String mapIdToString(Integer id) {
        return "id=" + id;
    }

    @Override
    public String mapAttributesToString() {
        return "(id, name)";
    }

    @Override
    public String mapAttributesAndValuesToString(Company entity) {
        return String.format("(%s, '%s')", entity.getId(), entity.getName());
    }

    @Override
    public String mapEntityToUpdateString(Company entity) {
        return String.format("name='%s'", entity.getName());
    }

}
