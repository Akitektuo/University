package ro.ubb.truckers.domain.report;

import org.json.JSONObject;
import ro.ubb.truckers.domain.entity.Garage;
import ro.ubb.truckers.domain.entity.Truck;

import java.util.List;
import java.util.stream.Collectors;

public class GarageReport {
    private final int garageId;
    private final String garageLocation;
    private final int truckMinimumMileage;
    private final double truckAverageMileage;
    private final int truckMaximumMileage;

    public GarageReport(int garageId,
                        String garageLocation,
                        int truckMinimumMileage,
                        int truckAverageMileage,
                        int truckMaximumMileage) {
        this.garageId = garageId;
        this.garageLocation = garageLocation;
        this.truckMinimumMileage = truckMinimumMileage;
        this.truckAverageMileage = truckAverageMileage;
        this.truckMaximumMileage = truckMaximumMileage;
    }

    public GarageReport(Garage garage, List<Truck> allTrucks) {
        var relevantTrucks = getRelevantTrucks(allTrucks, garage);
        garageId = garage.getId();
        garageLocation = garage.getLocation();
        truckMinimumMileage = relevantTrucks.stream()
                .mapToInt(Truck::getMileage)
                .min()
                .orElse(0);
        truckMaximumMileage = relevantTrucks.stream()
                .mapToInt(Truck::getMileage)
                .max()
                .orElse(0);
        truckAverageMileage = relevantTrucks.stream()
                .mapToInt(Truck::getMileage)
                .average()
                .orElse(0);
    }

    public int getGarageId() {
        return garageId;
    }

    public String getGarageLocation() {
        return garageLocation;
    }

    public int getTruckMinimumMileage() {
        return truckMinimumMileage;
    }

    public double getTruckAverageMileage() {
        return truckAverageMileage;
    }

    public int getTruckMaximumMileage() {
        return truckMaximumMileage;
    }

    private List<Truck> getRelevantTrucks(List<Truck> allTrucks, Garage garage) {
        return allTrucks.stream()
                .filter(truck -> truck.getGarageId() == garage.getId())
                .collect(Collectors.toList());
    }

    public String toJSON() {
        return new JSONObject(this).toString();
    }

    public static GarageReport fromJSON(String jsonString) {
        var json = new JSONObject(jsonString);

        var garageId = json.getInt("garageId");
        var garageLocation = json.getString("garageLocation");
        var truckMinimumMileage = json.getInt("truckMinimumMileage");
        var truckAverageMileage = json.getInt("truckAverageMileage");
        var truckMaximumMileage = json.getInt("truckMaximumMileage");

        return new GarageReport(garageId, garageLocation, truckMinimumMileage, truckAverageMileage, truckMaximumMileage);
    }
}
