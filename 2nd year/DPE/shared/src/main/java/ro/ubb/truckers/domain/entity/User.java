package ro.ubb.truckers.domain.entity;

import org.json.JSONObject;
import ro.ubb.truckers.util.StringValidator;

import java.time.LocalDate;

public class User extends BaseEntity<Integer> {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private int truckId;

    public User(int id, String firstName, String lastName, String email, String password, LocalDate dateOfBirth, int truckId) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.truckId = truckId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getTruckId() {
        return truckId;
    }

    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }

    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        return String.format("User(id=%s, firstName=%s, lastName=%s, email=%s, password=%s, dateOfBirth=%s, truckId=%s)",
                getId(), getFirstName(), getLastName(), getEmail(), getPassword(), getDateOfBirth(), getTruckId());
    }

    @Override
    public String toJSON() {
        var json = new JSONObject();

        json.put("id", getId())
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("email", email)
                .put("password", password)
                .put("dateOfBirth", dateOfBirth.toString())
                .put("truckId", truckId);

        return json.toString();
    }

    public static User fromJSON(String jsonString) {
        var json = new JSONObject(jsonString);

        var id = json.getInt("id");
        var firstName = json.getString("firstName");
        var lastName = json.getString("lastName");
        var email = json.getString("email");
        var password = json.getString("password");
        var dateOfBirth = StringValidator.convertToDate(json.getString("dateOfBirth"));
        var truckId = json.getInt("truckId");

        return new User(id, firstName, lastName, email, password, dateOfBirth, truckId);
    }
}
