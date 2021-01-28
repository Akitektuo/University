package container;

import java.util.concurrent.atomic.AtomicInteger;

public class IdDictionary<V> extends Dictionary<Integer, V> {
    private final AtomicInteger emptyAddress = new AtomicInteger();

    public int set(V value) {
        var newAddress = emptyAddress.incrementAndGet();

        set(newAddress, value);

        return newAddress;
    }
}
