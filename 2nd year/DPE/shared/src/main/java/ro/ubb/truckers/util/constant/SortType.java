package ro.ubb.truckers.util.constant;

import ro.ubb.truckers.domain.TruckersException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

public enum SortType {
    ASCENDING("asc"),
    DESCENDING("desc"),
    NOT_SET("none");

    private final String name;

    SortType(String name) {
        this.name = name;
    }

    /**
     * Returns the found enum based on the formatted name.
     *
     * @param name the formatted name of the enum.
     * @return the found {@code RepositoryType} enum with the given name.
     * @throws TruckersException in case no enum was found with the given name.
     */
    public static SortType enumOf(String name) {
        return Arrays.stream(SortType.values())
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
     * Returns a comparator based on the selected enum and extracts a property based on the given lambda function.
     *
     * @param retrieveProperty lambda function for extracting the property.
     * @param <T>              type of the object that is being compared
     * @return a comparator for the selected enum.
     */
    @SuppressWarnings("unchecked")
    public <T> Comparator<T> getComparator(Function<T, Comparable<Object>> retrieveProperty) {
        var comparator = Comparator.comparing(retrieveProperty);

        return switch (this) {
            case ASCENDING -> comparator;
            case DESCENDING -> comparator.reversed();
            case NOT_SET -> (Comparator<T>) Comparator.naturalOrder();
        };
    }

    public String getVisualRepresentation() {
        return switch (this) {
            case ASCENDING -> Constants.UP_ARROW;
            case DESCENDING -> Constants.DOWN_ARROW;
            case NOT_SET -> Constants.DOWN_ARROW + Constants.UP_ARROW;
        };
    }
}
