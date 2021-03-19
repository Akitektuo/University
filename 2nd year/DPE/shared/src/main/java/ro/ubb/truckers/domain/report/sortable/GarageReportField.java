package ro.ubb.truckers.domain.report.sortable;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.report.GarageReport;

import java.io.Serializable;
import java.util.Arrays;

public enum GarageReportField {
    MIN("min"),
    AVERAGE("average"),
    MAX("max");

    private final String name;

    GarageReportField(String name) {
        this.name = name;
    }

    /**
     * Returns the found enum based on the formatted name.
     *
     * @param name the formatted name of the enum.
     * @return the found {@code GarageReportField} enum with the given name.
     * @throws TruckersException in case no enum was found with the given name.
     */
    public static GarageReportField enumOf(String name) {
        return Arrays.stream(GarageReportField.values())
                .filter(value -> value.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new TruckersException("No enum value with name '%s' found", name));
    }

    /**
     * Returns the formatted name of the enum.
     *
     * @return a {@code String} representing the formatted name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the provided object's property based on the current enum.
     *
     * @param garageReport with properties.
     * @return the property of the given object.
     */
    @SuppressWarnings("unchecked")
    public Comparable<Object> getField(GarageReport garageReport) {
        Serializable comparable = switch (this) {
            case MIN -> garageReport.getTruckMinimumMileage();
            case MAX -> garageReport.getTruckMaximumMileage();
            case AVERAGE -> garageReport.getTruckAverageMileage();
        };

        return (Comparable<Object>) comparable;
    }
}
