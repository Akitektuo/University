package ro.ubb.truckers.domain.entity;

import org.json.JSONObject;

public class Garage extends BaseEntity<Integer> {
    private String location;
    private int capacity;
    private int allocatedTrucks;
    private int companyId;

    public Garage(int id, String location, int capacity, int allocatedTrucks, int companyId) {
        super(id);
        this.location = location;
        this.capacity = capacity;
        this.allocatedTrucks = allocatedTrucks;
        this.companyId = companyId;
    }

    public Garage(int id, String location, int capacity, int companyId) {
        this(id, location, capacity, 0, companyId);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getAllocatedTrucks() {
        return allocatedTrucks;
    }

    public void setAllocatedTrucks(int allocatedTrucks) {
        this.allocatedTrucks = allocatedTrucks;
    }

    public void incrementAllocatedTrucks() {
        allocatedTrucks++;
    }

    public void decrementAllocatedTrucks() {
        allocatedTrucks--;
    }

    @Override
    public String toString() {
        return String.format("Garage(id=%s, location=%s, capacity=%s, allocatedTrucks=%s, companyId=%s)",
                getId(), location, capacity, allocatedTrucks, companyId);
    }

    @Override
    public String toJSON() {
        return new JSONObject(this).toString();
    }

    public static Garage fromJSON(String jsonString) {
        var json = new JSONObject(jsonString);

        var id = json.getInt("id");
        var location = json.getString("location");
        var capacity = json.getInt("capacity");
        var companyId = json.getInt("companyId");

        return new Garage(id, location, capacity, 0, companyId);
    }
}
