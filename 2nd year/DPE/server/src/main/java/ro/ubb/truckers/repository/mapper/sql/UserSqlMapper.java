package ro.ubb.truckers.repository.mapper.sql;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSqlMapper implements SqlMapper<Integer, User> {
    @Override
    public User mapResultSetToEntity(ResultSet resultSet) {
        try {
            var id = resultSet.getInt("id");
            var firstName = resultSet.getString("firstname");
            var lastName = resultSet.getString("lastname");
            var email = resultSet.getString("email");
            var password = resultSet.getString("password");
            var dateOfBirth = resultSet.getDate("dateOfBirth").toLocalDate();
            var truckId = resultSet.getInt("truckId");

            return new User(id, firstName, lastName, email, password, dateOfBirth, truckId);
        } catch (SQLException exception) {
            throw new TruckersException("The result set is not a user.");
        }
    }

    @Override
    public String mapIdToString(Integer id) {
        return "id=" + id;
    }

    @Override
    public String mapAttributesToString() {
        return "(id, firstName, lastName, email, password, dateOfBirth, truckId)";
    }

    @Override
    public String mapAttributesAndValuesToString(User entity) {
        return String.format("(%s, '%s', '%s', '%s', '%s', CAST('%s' AS DATE), %s)", entity.getId(),
                entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPassword(),
                entity.getDateOfBirth().toString(), entity.getTruckId());
    }

    @Override
    public String mapEntityToUpdateString(User entity) {
        return String.format("firstName='%s', lastName='%s', email='%s', password='%s', " +
                        "dateOfBirth=CAST('%s' AS DATE), truckId='%s'",
                entity.getFirstName(), entity.getLastName(), entity.getEmail(),
                entity.getPassword(), entity.getDateOfBirth().toString(), entity.getTruckId());
    }
}
