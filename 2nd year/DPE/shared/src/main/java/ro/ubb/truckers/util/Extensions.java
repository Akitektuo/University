package ro.ubb.truckers.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Extensions {
    private Extensions() {
    }

    /**
     * Builds and returns a list form of the given iterable.
     *
     * @param fromIterable to transform into a list.
     * @param <T>          the type of list elements.
     * @return {@code List} of all the elements from the given iterable.
     */
    public static <T> List<T> toList(Iterable<T> fromIterable) {
        var list = new ArrayList<T>();

        fromIterable.forEach(list::add);

        return list;
    }

    /**
     * Maps each given object to a {@code String} and composes an array.
     *
     * @param args represent the objects to map and collect into a {@code String[]}.
     * @return a {@code String[]} consisting of the mapped objects.
     */
    public static String[] asStringArray(Object... args) {
        return Arrays.stream(args)
                .map(Object::toString)
                .toArray(String[]::new);
    }

    /**
     * Converts an iterable object into a stream.
     *
     * @param iterator to transform into a stream.
     * @param <T>      the type of stream elements.
     * @return a {@code Stream} of all elements from the given iterable.
     */
    public static <T> Stream<T> toStream(Iterable<T> iterator) {
        return StreamSupport.stream(iterator.spliterator(), false);
    }

    /**
     * Converts a {@code NodeList} object into a stream.
     *
     * @param nodeList to transform into a stream.
     * @return a {@code Stream} of all elements as {@code Node}.
     */
    public static Stream<Node> toStream(NodeList nodeList) {
        return IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item);
    }

    /**
     * Computes and returns the maximum value from the provided values.
     * In case there are no values, returns the minimum integer value
     *
     * @param values to compute the maximum.
     * @return the maximum of the values or minimum integer value.
     */
    public static int max(int... values) {
        return Arrays.stream(values)
                .max()
                .orElse(Integer.MIN_VALUE);
    }

    /**
     * Computes and returns the minimum value from the provided values.
     * In case there are no values, returns the maximum integer value
     *
     * @param values to compute the minimum.
     * @return the minimum of the values or maximum integer value.
     */
    public static int min(int... values) {
        return Arrays.stream(values)
                .min()
                .orElse(Integer.MAX_VALUE);
    }

    /**
     * Computes and returns the floor (rounding down) of the value as {@code int}.
     *
     * @param value to floor.
     * @return the floor as {@code int}.
     */
    public static int floor(double value) {
        return (int) Math.floor(value);
    }

    /**
     * Computes and returns the ceil (rounding up) of the value as {@code int}.
     *
     * @param value to ceil.
     * @return the ceil as {@code int}.
     */
    public static int ceil(double value) {
        return (int) Math.ceil(value);
    }

    public static <T> JSONArray toJSONArray(List<T> objects, Function<T, String> map) {
        return new JSONArray(objects.stream().map(map).toArray());
    }

    public static Stream<String> toJSONArrayStream(String jsonArray) {
        var array = new JSONArray(jsonArray);

        return IntStream.range(0, array.length())
                .mapToObj(array::getString);
    }
}
