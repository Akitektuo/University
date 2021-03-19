package ro.ubb.truckers.console.util.table;

import ro.ubb.truckers.util.Extensions;

import java.util.List;
import java.util.function.Consumer;

/**
 * A tool for drawing characters in console, used as helper for {@code TableBuilder}.
 *
 * @param <T> the type of data that is drawn.
 */
public class Drawer<T> {
    private final StringBuilder builder = new StringBuilder();
    private final List<Column<T>> columns;
    private final VisualOptions options;

    /**
     * Creates a new drawer with data about the type of columns and visual options.
     *
     * @param columns for sketching headers and values.
     * @param options for different borders and/or padding size.
     */
    public Drawer(List<Column<T>> columns, VisualOptions options) {
        this.columns = columns;
        this.options = options;
    }

    /**
     * Turns all sketches into a drawing.
     *
     * @return a {@code String} consisting of the drawing of all sketches.
     */
    public String draw() {
        return builder.toString();
    }

    /**
     * Sketches a row of headers based on column data.
     *
     * @return {@code Drawer} for chaining.
     */
    public Drawer<T> sketchColumnNames() {
        sketchRow(this::sketchColumnName);

        return this;
    }

    /**
     * Sketches a row of data based on column data and given element.
     *
     * @param element for sketching its row.
     * @return {@code Drawer} for chaining.
     */
    public Drawer<T> sketchValues(T element) {
        sketchRow(column -> sketchValue(column, element));

        return this;
    }

    /**
     * Sketches a divider with the horizontal character from options.
     *
     * @return {@code Drawer} for chaining.
     */
    public Drawer<T> sketchDivider() {
        sketchDivider(options.getHorizontalCharacter());

        return this;
    }

    /**
     * Sketches a divider with the provided horizontal character.
     *
     * @param horizontalCharacter to use in the divider.
     * @return {@code Drawer} for chaining.
     */
    public Drawer<T> sketchDivider(String horizontalCharacter) {
        var padding = horizontalCharacter.repeat(options.getPaddingSize());
        var intersectionCharacter = options.getIntersectionCharacter();

        builder.append(intersectionCharacter);

        columns.forEach(column ->
                builder.append(padding)
                        .append(horizontalCharacter.repeat(column.getSize()))
                        .append(padding)
                        .append(intersectionCharacter));

        builder.append("\n");
        return this;
    }

    /**
     * Sketches a row and fills the grid with values provided by lambda function.
     *
     * @param onSketchValue for providing values.
     * @return {@code Drawer} for chaining.
     */
    public Drawer<T> sketchRow(Consumer<Column<T>> onSketchValue) {
        var padding = " ".repeat(options.getPaddingSize());
        var verticalCharacter = options.getVerticalCharacter();

        builder.append(verticalCharacter);

        columns.forEach(column -> {
            builder.append(padding);

            onSketchValue.accept(column);

            builder.append(padding)
                    .append(verticalCharacter);
        });

        builder.append("\n");
        return this;
    }

    /**
     * Sketches the column's name in the grid.
     *
     * @param column for value and size.
     * @return {@code Drawer} for chaining.
     */
    public Drawer<T> sketchColumnName(Column<T> column) {
        centerValue(column.getName(), column.getSize(), true);

        return this;
    }

    /**
     * Sketches the elements' value in the grid.
     *
     * @param column  for size.
     * @param element for value.
     * @return {@code Drawer} for chaining.
     */
    public Drawer<T> sketchValue(Column<T> column, T element) {
        centerValue(column.getValue(element), column.getSize());

        return this;
    }

    /**
     * Sketches the given value in the grid centering it based on the given size.
     *
     * @param value for filling the grid.
     * @param size  for centering the value.
     */
    private void centerValue(String value, int size) {
        centerValue(value, size, false);
    }

    /**
     * Sketches the given value in the grid centering it based on the given size and transforms value to upper case
     * if specified.
     *
     * @param value     for filling the grid.
     * @param size      for centering the value.
     * @param uppercase defines if the values should be transformed to upper case or not.
     */
    private void centerValue(String value, int size, boolean uppercase) {
        var valueSize = value.length();
        var space = (size - valueSize) / 2f;

        builder.append(" ".repeat(Extensions.floor(space)))
                .append(uppercase ? value.toUpperCase() : value)
                .append(" ".repeat(Extensions.ceil(space)));
    }
}
