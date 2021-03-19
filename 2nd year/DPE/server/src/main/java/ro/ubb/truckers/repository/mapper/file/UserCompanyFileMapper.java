package ro.ubb.truckers.repository.mapper.file;

import ro.ubb.truckers.domain.entity.UserCompany;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.StringValidator;

public class UserCompanyFileMapper implements FileMapper<UserCompany> {
    @Override
    public String[] mapEntityToStringArray(UserCompany entity) {
        return Extensions.asStringArray(entity.getUserId(), entity.getCompanyId(), entity.isManager());
    }

    @Override
    public UserCompany mapStringArrayToEntity(String[] listOfProperties) {
        var userId = StringValidator.convertToInt(listOfProperties[0]);
        var companyId = StringValidator.convertToInt(listOfProperties[1]);
        var manager = StringValidator.convertToBoolean(listOfProperties[2]);

        return new UserCompany(userId, companyId, manager);
    }
}
