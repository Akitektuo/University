package ro.ubb.truckers.repository.mapper.file;

import ro.ubb.truckers.domain.entity.Delivery;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.StringValidator;

public class DeliveryFileMapper implements FileMapper<Delivery> {
    @Override
    public String[] mapEntityToStringArray(Delivery entity) {
        return Extensions.asStringArray(
                entity.getId(),
                entity.getOrigin(),
                entity.getDestination(),
                entity.getDistance(),
                entity.isDelivered(),
                entity.getLoad(),
                entity.getTruckId(),
                entity.getCompanyId());
    }

    @Override
    public Delivery mapStringArrayToEntity(String[] listOfProperties) {
        var id = StringValidator.convertToInt(listOfProperties[0]);
        var origin = listOfProperties[1];
        var destination = listOfProperties[2];
        var distance = StringValidator.convertToInt(listOfProperties[3]);
        var delivered = StringValidator.convertToBoolean(listOfProperties[4]);
        var load = listOfProperties[5];
        var truckId = StringValidator.convertToInt(listOfProperties[6]);
        var companyId = StringValidator.convertToInt(listOfProperties[7]);

        return new Delivery(id, origin, destination, distance, delivered, load, truckId, companyId);
    }
}
