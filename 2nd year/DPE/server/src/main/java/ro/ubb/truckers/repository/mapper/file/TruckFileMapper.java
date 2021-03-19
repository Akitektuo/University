package ro.ubb.truckers.repository.mapper.file;

import ro.ubb.truckers.domain.entity.Truck;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.StringValidator;

public class TruckFileMapper implements FileMapper<Truck> {
    @Override
    public String[] mapEntityToStringArray(Truck entity) {
        return Extensions.asStringArray(
                entity.getId(),
                entity.getModel(),
                entity.getLicensePlate(),
                entity.getMileage(),
                entity.getDriverId(),
                entity.getGarageId());
    }

    @Override
    public Truck mapStringArrayToEntity(String[] listOfProperties) {
        var id = StringValidator.convertToInt(listOfProperties[0]);
        var model = listOfProperties[1];
        var licensePlate = listOfProperties[2];
        var mileage = StringValidator.convertToInt(listOfProperties[3]);
        var driverId = StringValidator.convertToInt(listOfProperties[4]);
        var garageId = StringValidator.convertToInt(listOfProperties[5]);

        return new Truck(id, model, licensePlate, mileage, driverId, garageId);
    }
}
