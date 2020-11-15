package container;

public class MemoryHeap<V> extends Dictionary<Integer, V> {
    private int emptyAddress;

    public int set(V value) {
        set(++emptyAddress, value);

        return emptyAddress;
    }
}
