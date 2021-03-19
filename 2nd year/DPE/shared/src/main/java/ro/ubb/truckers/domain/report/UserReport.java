package ro.ubb.truckers.domain.report;

import org.json.JSONObject;
import ro.ubb.truckers.domain.entity.Company;
import ro.ubb.truckers.domain.entity.Delivery;
import ro.ubb.truckers.domain.entity.User;
import ro.ubb.truckers.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class UserReport {
    private final int userId;
    private final String userFullName;
    private final String companyName;
    private final int totalDistance;
    private final int numberOfDeliveries;
    private final float averageOfDistance;

    public UserReport(int userId,
                      String userFullName,
                      String companyName,
                      int totalDistance,
                      int numberOfDeliveries,
                      float averageOfDistance) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.companyName = companyName;
        this.totalDistance = totalDistance;
        this.numberOfDeliveries = numberOfDeliveries;
        this.averageOfDistance = averageOfDistance;
    }

    public UserReport(Pair<User, Company> pair, List<Delivery> completedDeliveries) {
        var user = pair.getFirst();
        var company = pair.getSecond();

        var deliveries = getRelevantDeliveries(completedDeliveries, user, company);

        userId = user.getId();
        userFullName = user.getFullName();
        companyName = company.getName();

        numberOfDeliveries = deliveries.size();
        totalDistance = deliveries.stream()
                .mapToInt(Delivery::getDistance)
                .sum();
        averageOfDistance = (float) totalDistance / numberOfDeliveries;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public int getNumberOfDeliveries() {
        return numberOfDeliveries;
    }

    public float getAverageOfDistance() {
        return averageOfDistance;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    private List<Delivery> getRelevantDeliveries(List<Delivery> completedDeliveries, User user, Company company) {
        return completedDeliveries.stream().
                filter(delivery -> delivery.getTruckId() == user.getTruckId() &&
                        delivery.getCompanyId() == company.getId())
                .collect(Collectors.toList());
    }

    public String toJSON() {
        return new JSONObject(this).toString();
    }

    public static UserReport fromJSON(String jsonString) {
        var json = new JSONObject(jsonString);

        var userId = json.getInt("userId");
        var userFullName = json.getString("userFullName");
        var companyName = json.getString("companyName");
        var totalDistance = json.getInt("totalDistance");
        var numberOfDeliveries = json.getInt("numberOfDeliveries");
        var averageOfDistance = json.getInt("averageOfDistance");

        return new UserReport(userId, userFullName, companyName, totalDistance, numberOfDeliveries, averageOfDistance);
    }
}
