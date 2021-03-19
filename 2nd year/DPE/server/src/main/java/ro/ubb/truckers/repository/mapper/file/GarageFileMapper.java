package ro.ubb.truckers.repository.mapper.file;

import ro.ubb.truckers.domain.entity.Garage;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.StringValidator;

public class GarageFileMapper implements FileMapper<Garage> {
    @Override
    public String[] mapEntityToStringArray(Garage entity) {
        return Extensions.asStringArray(
                entity.getId(),
                entity.getLocation(),
                entity.getCapacity(),
                entity.getAllocatedTrucks(),
                entity.getCompanyId());
    }

    @Override
    public Garage mapStringArrayToEntity(String[] listOfProperties) {
        var id = StringValidator.convertToInt(listOfProperties[0]);
        var location = listOfProperties[1];
        var capacity = StringValidator.convertToInt(listOfProperties[2]);
        var allocatedTrucks = StringValidator.convertToInt(listOfProperties[3]);
        var companyId = StringValidator.convertToInt(listOfProperties[4]);

        return new Garage(id, location, capacity, allocatedTrucks, companyId);
    }
}
