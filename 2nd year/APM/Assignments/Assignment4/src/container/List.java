package container;

import java.util.ArrayList;

public class List<T> implements ListInterface<T> {
    java.util.List<T> list = new ArrayList<>();

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
    public String toString() {
        return list.toString();
    }
}
