package container;

import java.util.*;

public class Stack<T> implements StackInterface<T> {
    private final Deque<T> stack;

    public Stack() {
        stack = new ArrayDeque<>();
    }

    public Stack(T... initialValues) {
        stack = new ArrayDeque<>(Arrays.asList(initialValues));
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public T pop() throws NoSuchElementException {
        return stack.pop();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
