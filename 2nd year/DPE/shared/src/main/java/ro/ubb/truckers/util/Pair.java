package ro.ubb.truckers.util;

import java.util.Map;
import java.util.Objects;

public class Pair<K, V> implements Map.Entry<K, V> {
    private K first;
    private V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public K getKey() {
        return first;
    }

    @Override
    public V getValue() {
        return second;
    }

    @Override
    public V setValue(V value) {
        return second = value;
    }

    public K getFirst() {
        return first;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return String.format("Pair(first=%s, second=%s)", first, second);
    }
}
