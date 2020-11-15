package container;

import kotlin.jvm.functions.Function1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class List<T> implements ListInterface<T> {
    private final java.util.List<T> list;

    public List() {
        list = new ArrayList<>();
    }

    public List(Collection<T> fromCollection) {
        list = new ArrayList<>(fromCollection);
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
    public void add(T value) {
        list.add(value);
    }

    @Override
    public void add(ListInterface<T> another) {
        list.addAll(another.toCollection());
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
    public String toString() {
        return list.toString();
    }
}
