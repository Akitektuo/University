package ro.ubb.truckers.console.util.table;


import ro.ubb.truckers.console.util.Constants;

/**
 * A structure that holds all the important options for rendering the table.
 */
public class VisualOptions {
    private int paddingSize = Constants.TABLE_PADDING_SIZE;
    private String intersectionCharacter = Constants.TABLE_INTERSECTION;
    private String horizontalCharacter = Constants.TABLE_HORIZONTAL;
    private String verticalCharacter = Constants.TABLE_VERTICAL;
    private String headerCharacter = Constants.TABLE_HEADER;

    /**
     * @return the defined padding size.
     */
    int getPaddingSize() {
        return paddingSize;
    }

    /**
     * Defines the padding size based on valid given size.
     *
     * @param paddingSize the new padding size, if non-negative integer.
     * @return {@code VisualOptions} for chaining setters.
     */
    public VisualOptions setPaddingSize(int paddingSize) {
        if (paddingSize >= 0) {
            this.paddingSize = paddingSize;
        }

        return this;
    }

    /**
     * @return the defined intersection character.
     */
    String getIntersectionCharacter() {
        return intersectionCharacter;
    }

    /**
     * Defines the intersection character based on a character given as {@code String}.
     *
     * @param intersectionCharacter the new intersection character, if length is 1.
     * @return {@code VisualOptions} for chaining setters.
     */
    public VisualOptions setIntersectionCharacter(String intersectionCharacter) {
        if (intersectionCharacter.length() == 1) {
            this.intersectionCharacter = intersectionCharacter;
        }

        return this;
    }

    /**
     * @return the defined horizontal character.
     */
    String getHorizontalCharacter() {
        return horizontalCharacter;
    }

    /**
     * Defines the horizontal character based on a character given as {@code String}.
     *
     * @param horizontalCharacter the new horizontal character, if length is 1.
     * @return {@code VisualOptions} for chaining setters.
     */
    public VisualOptions setHorizontalCharacter(String horizontalCharacter) {
        if (horizontalCharacter.length() == 1) {
            this.horizontalCharacter = horizontalCharacter;
        }

        return this;
    }

    /**
     * @return the defined vertical character.
     */
    String getVerticalCharacter() {
        return verticalCharacter;
    }

    /**
     * Defines the vertical character based on a character given as {@code String}.
     *
     * @param verticalCharacter the new vertical character, if length is 1.
     * @return {@code VisualOptions} for chaining setters.
     */
    public VisualOptions setVerticalCharacter(String verticalCharacter) {
        if (verticalCharacter.length() == 1) {
            this.verticalCharacter = verticalCharacter;
        }

        return this;
    }

    /**
     * @return the defined header character.
     */
    String getHeaderCharacter() {
        return headerCharacter;
    }

    /**
     * Defines the header character based on a character given as {@code String}.
     *
     * @param headerCharacter the new header character, if length is 1.
     * @return {@code VisualOptions} for chaining setters.
     */
    public VisualOptions setHeaderCharacter(String headerCharacter) {
        if (headerCharacter.length() == 1) {
            this.headerCharacter = headerCharacter;
        }

        return this;
    }
}
