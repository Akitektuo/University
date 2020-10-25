package container;

public interface ListInterface<T> {
    boolean isEmpty();

    int getSize();

    void add(T value);

    T get(int index) throws IndexOutOfBoundsException;

    boolean set(int index, T value);

    boolean remove(int index);

    boolean remove(T value);

    T getLast();
}
