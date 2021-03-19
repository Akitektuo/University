package ro.ubb.truckers.domain.validator;

import ro.ubb.truckers.domain.entity.Truck;

public class TruckValidator implements Validator<Truck> {
    @Override
    public void validate(Truck entity) throws ValidatorException {
        if (entity.getId() < 1) {
            throw new ValidatorException("Id must not be less than 1");
        }
        if (entity.getModel().isEmpty()) {
            throw new ValidatorException("Model must not be empty");
        }
        if (entity.getLicensePlate().isEmpty()) {
            throw new ValidatorException("License Plate must not be empty");
        }
        if (entity.getMileage() < 0) {
            throw new ValidatorException("Mileage must not be less than 0");
        }
        if (entity.getDriverId() < 0) {
            throw new ValidatorException("Driver Id must not be less than 0");
        }
        if (entity.getGarageId() < 1) {
            throw new ValidatorException("Garage Id must not be less than 1");
        }
    }
}
