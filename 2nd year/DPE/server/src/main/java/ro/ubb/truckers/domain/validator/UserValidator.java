package ro.ubb.truckers.domain.validator;

import ro.ubb.truckers.domain.entity.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+._,-]+@(.+)$";

    @Override
    public void validate(User entity) throws ValidatorException {
        if (entity.getId() < 1) {
            throw new ValidatorException("Id must not be less than 1");
        }
        if (entity.getFirstName().isEmpty()) {
            throw new ValidatorException("First name must not be empty");
        }
        if (entity.getLastName().isEmpty()) {
            throw new ValidatorException("Last name must not be empty");
        }
        if (entity.getEmail().isEmpty()) {
            throw new ValidatorException("Email must not be empty");
        }
        if (!Pattern.compile(EMAIL_REGEX).matcher(entity.getEmail()).matches()) {
            throw new ValidatorException("Email is not valid");
        }
        if (entity.getPassword().isEmpty()) {
            throw new ValidatorException("Password must not be empty");
        }
        if (Period.between(entity.getDateOfBirth(), LocalDate.now()).getYears() < 18) {
            throw new ValidatorException("User must be at least 18 years old");
        }
        if (entity.getTruckId() < 0) {
            throw new ValidatorException("Truck id must not be less than 0");
        }
    }
}
