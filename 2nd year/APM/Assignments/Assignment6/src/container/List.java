package container;

import kotlin.jvm.functions.Function1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class List<T> implements ListInterface<T> {
    private final java.util.List<T> list;

    public List() {
        list = Collections.synchronizedList(new ArrayList<>());
    }

    public List(T... initialValues) {
        list = Collections.synchronizedList(Arrays.asList(initialValues));
    }

    public List(Collection<T> fromCollection) {
        list = Collections.synchronizedList(new ArrayList<>(fromCollection));
    }

    public List(List<T> listToCopy) {
        list = Collections.synchronizedList(listToCopy.list);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public T add(T value) {
        list.add(value);

        return value;
    }

    @Override
    public ListInterface<T> add(ListInterface<T> another) {
        list.addAll(another.toCollection());

        return another;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        return list.get(index);
    }

    @Override
    public boolean set(int index, T value) {
        try {
            list.set(index, value);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Override
    public boolean remove(int index) {
        try {
            list.remove(index);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Override
    public boolean remove(T value) {
        return list.remove(value);
    }

    @Override
    public T getLast() {
        if (isEmpty()) {
            return null;
        }
        return get(getSize() - 1);
    }

    @Override
    public ListInterface<T> filter(Function1<T, Boolean> selector) {
        return new List<>(list.stream()
                .filter(selector::invoke)
                .collect(Collectors.toList()));
    }

    @Override
    public void forEach(Consumer<T> action) {
        list.forEach(action);
    }

    @Override
    public Collection<T> toCollection() {
        return list;
    }

    @Override
    public boolean contains(T item) {
        return list.contains(item);
    }

    @Override
    public ListInterface<T> clone() {
        return new List<>(this);
    }

    @Override
    public <R> ListInterface<R> map(Function<T, R> mapper) {
        return new List<>(list.stream().map(mapper).collect(Collectors.toList()));
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T first() {
        return isEmpty() ? null : get(0);
    }

    @Override
    public <R> ListInterface<R> flatMap(Function<T, ListInterface<R>> mapper) {
        var mappedList = new List<R>();

        list.stream().map(mapper).forEach(mappedList::add);

        return mappedList;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
