package ro.ubb.truckers.repository.mapper.sql;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.UserCompany;
import ro.ubb.truckers.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCompanySqlMapper implements SqlMapper<Pair<Integer, Integer>, UserCompany> {
    @Override
    public UserCompany mapResultSetToEntity(ResultSet resultSet) {
        try {
            var userId = resultSet.getInt("userId");
            var companyId = resultSet.getInt("companyId");
            var manager = resultSet.getBoolean("manager");

            return new UserCompany(userId, companyId, manager);
        } catch (SQLException exception) {
            throw new TruckersException("The result set is not a UserCompany.");
        }
    }

    @Override
    public String mapIdToString(Pair<Integer, Integer> id) {
        return String.format("userId=%s AND companyId=%s", id.getFirst(), id.getSecond());
    }

    @Override
    public String mapAttributesToString() {
        return "(userId, companyId, manager)";
    }

    @Override
    public String mapAttributesAndValuesToString(UserCompany entity) {
        return String.format("('%s', '%s','%s')", entity.getUserId(), entity.getCompanyId(), entity.isManager());
    }

    @Override
    public String mapEntityToUpdateString(UserCompany entity) {
        return String.format("(userId='%s', companyId='%s', manager='%s')", entity.getUserId(),
                entity.getCompanyId(), entity.isManager());
    }
}
