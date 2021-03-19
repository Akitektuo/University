package ro.ubb.truckers.domain.validator;

import ro.ubb.truckers.domain.entity.Company;

public class CompanyValidator implements Validator<Company> {
    @Override
    public void validate(Company entity) throws ValidatorException {
        if (entity.getId() < 1) {
            throw new ValidatorException("Id must not be less than 1");
        }

        if (entity.getName().isEmpty()) {
            throw new ValidatorException("Name must not be empty");
        }
    }
}