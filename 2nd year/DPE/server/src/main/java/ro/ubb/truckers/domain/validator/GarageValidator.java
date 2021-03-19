package ro.ubb.truckers.domain.validator;

import ro.ubb.truckers.domain.entity.Garage;

public class GarageValidator implements Validator<Garage> {
    @Override
    public void validate(Garage entity) throws ValidatorException {
        if (entity.getId() < 1) {
            throw new ValidatorException("Id must not be less than 1");
        }
        if (entity.getCompanyId() < 1) {
            throw new ValidatorException("Company Id must not be less than 1");
        }
        if (entity.getCapacity() < 1) {
            throw new ValidatorException("Capacity must be greater than 0");
        }
        if (entity.getAllocatedTrucks() < 0) {
            throw new ValidatorException("There are no more trucks in this garage");
        }
        if (entity.getAllocatedTrucks() > entity.getCapacity()) {
            throw new ValidatorException("No more available space");
        }
        if (entity.getLocation().isEmpty()) {
            throw new ValidatorException("Location must not be empty");
        }
    }
}
