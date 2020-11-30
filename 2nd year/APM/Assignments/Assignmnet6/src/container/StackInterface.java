package container;

import java.util.NoSuchElementException;

public interface StackInterface<T> {
    boolean isEmpty();

    void push(T value);

    T pop() throws NoSuchElementException;
}
