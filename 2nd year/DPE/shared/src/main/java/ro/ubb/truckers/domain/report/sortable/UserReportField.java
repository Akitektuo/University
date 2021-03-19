package ro.ubb.truckers.domain.report.sortable;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.report.UserReport;

import java.io.Serializable;
import java.util.Arrays;

public enum UserReportField {
    TOTAL("total"),
    COUNT("count"),
    AVERAGE("average");

    private final String name;

    UserReportField(String name) {
        this.name = name;
    }

    /**
     * Returns the found enum based on the formatted name.
     *
     * @param name the formatted name of the enum.
     * @return the found {@code UserReportField} enum with the given name.
     * @throws TruckersException in case no enum was found with the given name.
     */
    public static UserReportField enumOf(String name) {
        return Arrays.stream(UserReportField.values())
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
     * @param userReport with properties.
     * @return the property of the given object.
     */
    @SuppressWarnings("unchecked")
    public Comparable<Object> getField(UserReport userReport) {
        Serializable comparable = switch (this) {
            case TOTAL -> userReport.getTotalDistance();
            case COUNT -> userReport.getNumberOfDeliveries();
            case AVERAGE -> userReport.getAverageOfDistance();
        };

        return (Comparable<Object>) comparable;
    }
}
