package container;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public class Stack<T> implements StackInterface<T> {
    Deque<T> stack = new ArrayDeque<>();

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
}
