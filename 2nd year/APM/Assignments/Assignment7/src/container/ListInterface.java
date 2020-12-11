package container;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ListInterface<T> {
    boolean isEmpty();

    int getSize();

    T add(T value);

    ListInterface<T> add(ListInterface<T> another);

    T get(int index) throws IndexOutOfBoundsException;

    boolean set(int index, T value);

    boolean remove(int index);

    boolean remove(T value);

    T getLast();

    ListInterface<T> filter(Function1<T, Boolean> selector);

    T find(Function1<T, Boolean> selector);

    void forEach(Consumer<T> action);

    Collection<T> toCollection();

    boolean contains(T item);

    ListInterface<T> clone();

    <R> ListInterface<R> map(Function<T, R> mapper);

    <R> ListInterface<R> mapIndexed(Function2<T, Integer, R> mapper);

    void clear();

    T first();

    <R> ListInterface<R> flatMap(Function<T, ListInterface<R>> mapper);
}
