package ro.ubb.truckers.console.util.table;

import ro.ubb.truckers.util.Extensions;

import java.util.function.Function;

/**
 * Defines a column in the {@code TableBuilder}.
 *
 * @param <T> type of row data.
 */
class Column<T> {
    private final String name;
    private final int minimumSize;
    private final Function<T, Object> withValue;

    private int size = 0;

    /**
     * Creates a new column with a header name, minimum width size and a lambda function for retrieving the data's
     * properties for the respective column.
     *
     * @param name        the header name of the column.
     * @param minimumSize the minimum column width measured in character units.
     * @param withValue   the lambda function that tells the column what property to get from the data.
     */
    Column(String name, int minimumSize, Function<T, Object> withValue) {
        this.name = name;
        this.minimumSize = minimumSize;
        this.withValue = withValue;
    }

    /**
     * @return the header name of the column.
     */
    String getName() {
        return name;
    }

    /**
     * @return the minimum column width measured in character units.
     */
    int getMinimumSize() {
        return minimumSize;
    }

    /**
     * @return the lambda function that tells the column what property to get from the data.
     */
    Function<T, Object> getWithValue() {
        return withValue;
    }

    /**
     * Returns the property from the given object according to the given {@code withValue} lambda function.
     *
     * @param object to extract property from.
     * @return the extracted property.
     */
    String getValue(T object) {
        return withValue.apply(object).toString();
    }

    /**
     * @return the computed column width measured in character units.
     */
    public int getSize() {
        return size;
    }

    /**
     * Computes the column with the given size based on the set properties with the constructor.
     *
     * @param size representing the column width measured in character units.
     */
    public void setSize(int size) {
        this.size = Extensions.max(size, name.length(), minimumSize);
    }
}
