package ro.ubb.truckers.domain.entity;

import org.json.JSONObject;

public class Truck extends BaseEntity<Integer> {
    private String model;
    private String licensePlate;
    private int mileage;
    private int driverId;
    private int garageId;

    public Truck(int id, String model, String licensePlate, int mileage, int driverId, int garageId) {
        super(id);
        this.model = model;
        this.licensePlate = licensePlate;
        this.mileage = mileage;
        this.driverId = driverId;
        this.garageId = garageId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getGarageId() {
        return garageId;
    }

    public void setGarageId(int garageId) {
        this.garageId = garageId;
    }

    @Override
    public String toString() {
        return String.format("Truck(id=%s, model=%s, licensePlate=%s, mileage=%s, driverId=%s, garageId=%s)",
                getId(), model, licensePlate, mileage, driverId, garageId);
    }

    @Override
    public String toJSON() {
        return new JSONObject(this).toString();
    }

    public static Truck fromJSON(String jsonString) {
        var json = new JSONObject(jsonString);

        var id = json.getInt("id");
        var model = json.getString("model");
        var licensePlate = json.getString("licensePlate");
        var mileage = json.getInt("mileage");
        var driverId = json.getInt("driverId");
        var garageId = json.getInt("garageId");

        return new Truck(id, model, licensePlate, mileage, driverId, garageId);
    }
}
