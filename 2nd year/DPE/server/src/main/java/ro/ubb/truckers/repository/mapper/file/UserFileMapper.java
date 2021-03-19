package ro.ubb.truckers.repository.mapper.file;

import ro.ubb.truckers.domain.entity.User;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.StringValidator;

public class UserFileMapper implements FileMapper<User> {
    @Override
    public String[] mapEntityToStringArray(User entity) {
        return Extensions.asStringArray(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getDateOfBirth(),
                entity.getTruckId());
    }

    @Override
    public User mapStringArrayToEntity(String[] listOfProperties) {
        var id = StringValidator.convertToInt(listOfProperties[0]);
        var firstName = listOfProperties[1];
        var lastName = listOfProperties[2];
        var email = listOfProperties[3];
        var password = listOfProperties[4];
        var dateOfBirth = StringValidator.convertToDate(listOfProperties[5]);
        var truckId = StringValidator.convertToInt(listOfProperties[6]);

        return new User(id, firstName, lastName, email, password, dateOfBirth, truckId);
    }
}
