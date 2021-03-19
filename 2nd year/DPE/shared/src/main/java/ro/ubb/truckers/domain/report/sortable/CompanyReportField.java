package ro.ubb.truckers.domain.report.sortable;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.report.CompanyReport;

import java.io.Serializable;
import java.util.Arrays;

public enum CompanyReportField {
    COUNT("count"),
    AVERAGE("average");

    private final String name;

    CompanyReportField(String name) { this.name = name; }

    /**
     * Returns the formatted name of the enum.
     *
     * @return a {@code String} representing the formatted name.
     */
    public String getName() { return name; }

    /**
     * Returns the found enum based on the formatted name.
     *
     * @param  name the formatted name of the enum.
     * @return the found @{code CompanyReportField} enum with the given name.
     * @throws TruckersException in case no enum was found with the given name.
     */
    public static CompanyReportField enumOf(String name) {
        return Arrays.stream(CompanyReportField.values())
                .filter(value -> value.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new TruckersException("No enum value with name '%s' found", name));
    }

    /**
     * Returns the provided object's property based on the current enum.
     *
     * @param companyReport with properties.
     * @return the property of the given object.
     */
    @SuppressWarnings("unchecked")
    public Comparable<Object> getField(CompanyReport companyReport) {
        Serializable comparable = switch (this) {
            case COUNT -> companyReport.getNumberOfDeliveries();
            case AVERAGE -> companyReport.getAverageOfDistance();
        };

        return (Comparable<Object>) comparable;
    }
}
