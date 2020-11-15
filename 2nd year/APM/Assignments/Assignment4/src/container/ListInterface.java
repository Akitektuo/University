package container;

import kotlin.jvm.functions.Function1;

import java.util.Collection;
import java.util.function.Consumer;

public interface ListInterface<T> {
    boolean isEmpty();

    int getSize();

    void add(T value);

    void add(ListInterface<T> another);

    T get(int index) throws IndexOutOfBoundsException;

    boolean set(int index, T value);

    boolean remove(int index);

    boolean remove(T value);

    T getLast();

    ListInterface<T> filter(Function1<T, Boolean> selector);

    void forEach(Consumer<T> action);

    Collection<T> toCollection();

    boolean contains(T item);
}
