package ro.ubb.truckers.domain.validator;


import ro.ubb.truckers.domain.entity.Delivery;

public class DeliveryValidator implements Validator<Delivery> {

    /**
     * Validates a {@code Delivery} given as parameter.
     *
     * @param entity {@code Delivery} to be validated
     * @throws ValidatorException if the {@code Delivery}'s properties do not respect the constraints.
     */
    @Override
    public void validate(Delivery entity) throws ValidatorException {
        validateId(entity);
        validateDistance(entity);
        validateLoad(entity);
        validateOrigin(entity);
        validateDestination(entity);
        validateTruckId(entity);
        validateCompanyId(entity);
    }

    private void validateId(Delivery entity) {
        if (entity.getId() < 1) {
            throw new ValidatorException("Id for the delivery must not be less than 1");
        }
    }

    private void validateDistance(Delivery entity) {
        if (entity.getDistance() < 1) {
            throw new ValidatorException("Distance for the delivery must not be less than 1 km");
        }
    }

    private void validateLoad(Delivery entity) {
        if (entity.getLoad().isEmpty()) {
            throw new ValidatorException("The load for the delivery must not be empty");
        }
    }

    private void validateOrigin(Delivery entity) {
        if (entity.getOrigin().isEmpty()) {
            throw new ValidatorException("Origin for the delivery must not be empty");
        }
    }

    private void validateDestination(Delivery entity) {
        if (entity.getDestination().isEmpty()) {
            throw new ValidatorException("Destination for the delivery must not be empty");
        }
    }

    private void validateTruckId(Delivery entity) {
        if (entity.getTruckId() < 1) {
            throw new ValidatorException("Truck Id for the delivery must not be less than 1");
        }
    }

    private void validateCompanyId(Delivery entity) {
        if (entity.getCompanyId() < 1) {
            throw new ValidatorException("Company Id for the delivery must not be less than 1");
        }
    }
}
