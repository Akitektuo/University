package ro.ubb.truckers.domain.entity;

import org.json.JSONObject;

import java.util.Objects;

public class Delivery extends BaseEntity<Integer> {

    private String origin;
    private String destination;
    private int distance;
    private boolean delivered;
    private String load;
    private int truckId;
    private int companyId;

    public Delivery(
        int id,
        String origin,
        String destination,
        int distance,
        boolean delivered,
        String load,
        int truckId,
        int companyId) {
        super(id);
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.delivered = delivered;
        this.load = load;
        this.truckId = truckId;
        this.companyId = companyId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public int getTruckId() {
        return truckId;
    }

    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Delivery delivery = (Delivery) o;
        return distance == delivery.distance &&
            delivered == delivery.delivered &&
            origin.equals(delivery.origin) &&
            destination.equals(delivery.destination) &&
            load.equals(delivery.load);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, distance, delivered, load);
    }

    @Override
    public String toString() {
        return String.format(
            "Delivery(id=%s, origin=%s, destination=%s, distance=%s, delivered=%s, load=%s, truckId=%s, companyId=%s)",
            getId(), origin, destination, distance, delivered, load, truckId, companyId);
    }

    @Override
    public String toJSON() {
        return new JSONObject(this).toString();
    }

    public static Delivery fromJSON(String jsonString) {
        var json = new JSONObject(jsonString);

        var id = json.getInt("id");
        var origin = json.getString("origin");
        var destination = json.getString("destination");
        var distance = json.getInt("distance");
        var delivered = json.getBoolean("delivered");
        var load = json.getString("load");
        var truckId = json.getInt("truckId");
        var companyId = json.getInt("companyId");

        return new Delivery(id, origin, destination, distance, delivered, load, truckId, companyId);
    }
}
