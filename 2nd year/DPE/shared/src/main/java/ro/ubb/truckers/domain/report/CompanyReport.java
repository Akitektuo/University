package ro.ubb.truckers.domain.report;

import org.json.JSONObject;
import ro.ubb.truckers.domain.entity.Company;
import ro.ubb.truckers.domain.entity.Delivery;
import ro.ubb.truckers.domain.entity.Truck;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyReport {
    private final int companyId;
    private final String companyName;
    private final int numberOfDeliveries;
    private final float averageOfDistance;

    public CompanyReport(int companyId,
                         String companyName,
                         int numberOfDeliveries,
                         float averageOfDistance) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.numberOfDeliveries = numberOfDeliveries;
        this.averageOfDistance = averageOfDistance;
    }

    public CompanyReport(Company company, List<Delivery> completedDeliveries) {
        var deliveries = getRelevantDeliveries(completedDeliveries, company);

        companyId = company.getId();
        companyName = company.getName();
        numberOfDeliveries = deliveries.size();
        var totalDistance = deliveries.stream()
                .mapToInt(Delivery::getDistance)
                .sum();
        averageOfDistance = (float) totalDistance / numberOfDeliveries;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getNumberOfDeliveries() {
        return numberOfDeliveries;
    }

    public float getAverageOfDistance() {
        return averageOfDistance;
    }

    private List<Delivery> getRelevantDeliveries(List<Delivery> completedDeliveries, Company company) {
        return completedDeliveries.stream()
                .filter(delivery -> delivery.getCompanyId() == company.getId())
                .collect(Collectors.toList());
    }

    public String toJSON() {
        return new JSONObject(this).toString();
    }

    public static CompanyReport fromJSON(String jsonString) {
        var json = new JSONObject(jsonString);

        var companyId = json.getInt("companyId");
        var companyName = json.getString("companyName");
        var numberOfDeliveries = json.getInt("numberOfDeliveries");
        var averageOfDistance = json.getInt("averageOfDistance");

        return new CompanyReport(companyId, companyName, numberOfDeliveries, averageOfDistance);
    }
}