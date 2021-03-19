package ro.ubb.truckers.domain.entity;

import org.json.JSONObject;

public class Company extends BaseEntity<Integer> {
    private String name;

    public Company(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Company(id=%s, name=%s)",
                getId(), name);
    }

    @Override
    public String toJSON() {
        return new JSONObject(this).toString();
    }

    public static Company fromJSON(String jsonString) {
        var json = new JSONObject(jsonString);

        var id = json.getInt("id");
        var name = json.getString("name");

        return new Company(id, name);
    }
}