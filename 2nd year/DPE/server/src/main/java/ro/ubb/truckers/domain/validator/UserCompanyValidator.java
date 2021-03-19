package ro.ubb.truckers.domain.validator;

import ro.ubb.truckers.domain.entity.UserCompany;

public class UserCompanyValidator implements Validator<UserCompany> {
    @Override
    public void validate(UserCompany entity) throws ValidatorException {
        if (entity.getUserId() < 1) {
            throw new ValidatorException("User id must not be less than 1");
        }
        if (entity.getCompanyId() < 1) {
            throw new ValidatorException("User id must not be less than 1");
        }
    }
}
