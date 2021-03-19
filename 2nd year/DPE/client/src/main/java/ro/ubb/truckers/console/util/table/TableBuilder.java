package ro.ubb.truckers.console.util.table;

import ro.ubb.truckers.util.constant.SortType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Used for creating a text-based table.
 *
 * @param <T> the type of data that the table will display.
 */
public class TableBuilder<T> {
    private final List<Column<T>> columns = new ArrayList<>();
    private final List<T> data;
    private final SortType sort;
    private final VisualOptions options;

    /**
     * Creates a {@code TableBuilder} with data.
     *
     * @param data the {@code List} of data that that will be displayed.
     */
    public TableBuilder(List<T> data) {
        this(data, SortType.NOT_SET);
    }

    /**
     * Creates a {@code TableBuilder} with data and sorting.
     *
     * @param data the {@code List} of data that that will be displayed.
     * @param sort the sorting reference that will be used for displaying sortable columns and sort states.
     */
    public TableBuilder(List<T> data, SortType sort) {
        this(data, sort, new VisualOptions());
    }

    /**
     * Creates a {@code TableBuilder} with data and predefined visual options.
     *
     * @param data    the {@code List} of data that that will be displayed.
     * @param options the predefined visual options that manipulate rendering.
     */
    public TableBuilder(List<T> data, VisualOptions options) {
        this(data, SortType.NOT_SET, options);
    }

    /**
     * Creates a {@code TableBuilder} with data, sorting and predefined visual options.
     *
     * @param data    the {@code List} of data that that will be displayed.
     * @param sort    the sorting reference that will be used for displaying sortable columns and sort states.
     * @param options the predefined visual options that manipulate rendering.
     */
    public TableBuilder(List<T> data, SortType sort, VisualOptions options) {
        this.data = data;
        this.sort = sort;
        this.options = options;
    }

    /**
     * Adds a column to the table with the given properties.
     *
     * @param name      of the column that will be displayed in the header.
     * @param withValue the lambda function that provides the property to render in the column for each row.
     * @return {@code TableBuilder} for chaining.
     */
    public TableBuilder<T> addColumn(String name, Function<T, Object> withValue) {
        return addColumn(name, 0, withValue);
    }

    /**
     * Adds a column to the table with the given properties.
     *
     * @param name        of the column that will be displayed in the header.
     * @param minimumSize that will be used if not exceeded by the header or data size.
     * @param withValue   the lambda function that provides the property to render in the column for each row.
     * @return {@code TableBuilder} for chaining.
     */
    public TableBuilder<T> addColumn(String name, int minimumSize, Function<T, Object> withValue) {
        columns.add(new Column<>(name, minimumSize, withValue));

        return this;
    }

    /**
     * Adds a column to the table with the given properties.
     *
     * @param name      of the column that will be displayed in the header.
     * @param applySort notifies that the column is sortable and, if true, applies the sorting indicator.
     * @param withValue the lambda function that provides the property to render in the column for each row.
     * @return {@code TableBuilder} for chaining.
     */
    public TableBuilder<T> addColumn(String name, boolean applySort, Function<T, Object> withValue) {
        return addColumn(name, 0, applySort, withValue);
    }

    /**
     * Adds a column to the table with the given properties.
     *
     * @param name        of the column that will be displayed in the header.
     * @param minimumSize that will be used if not exceeded by the header or data size.
     * @param applySort   notifies that the column is sortable and, if true, applies the sorting indicator.
     * @param withValue   the lambda function that provides the property to render in the column for each row.
     * @return {@code TableBuilder} for chaining.
     */
    public TableBuilder<T> addColumn(String name, int minimumSize, boolean applySort, Function<T, Object> withValue) {
        var sortType = applySort ? sort : SortType.NOT_SET;
        var suffix = " " + sortType.getVisualRepresentation();

        columns.add(new Column<>(name + suffix, minimumSize, withValue));

        return this;
    }

    /**
     * Prints and returns the built table.
     *
     * @return the table as {@code String}.
     */
    public String print() {
        computeColumnsSize();

        var table = drawTable();

        System.out.print(table);
        return table;
    }

    /**
     * Computes the size of each column.
     */
    private void computeColumnsSize() {
        columns.forEach(this::setColumnSize);
    }

    /**
     * Computes the size of the given column.
     *
     * @param column to compute and set its size.
     */
    private void setColumnSize(Column<T> column) {
        var size = data.stream()
                .mapToInt(element -> column.getValue(element).length())
                .max()
                .orElse(0);

        column.setSize(size);
    }

    /**
     * Draws the table and returns it as {@code String}.
     *
     * @return table as {@code String}.
     */
    private String drawTable() {
        var drawer = new Drawer<>(columns, options);

        sketchHeader(drawer);
        sketchContent(drawer);

        return drawer.draw();
    }

    /**
     * Uses drawer for sketching the header of the table.
     *
     * @param drawer used for sketching.
     */
    private void sketchHeader(Drawer<T> drawer) {
        drawer.sketchDivider()
                .sketchColumnNames()
                .sketchDivider(options.getHeaderCharacter());
    }

    /**
     * Uses drawer for sketching the content of the table.
     *
     * @param drawer used for sketching.
     */
    private void sketchContent(Drawer<T> drawer) {
        data.forEach(element -> drawer.sketchValues(element).sketchDivider());
    }
}
