package container;

import java.util.HashMap;
import java.util.function.Consumer;

public class Dictionary<K, V> implements DictionaryInterface<K, V> {
    HashMap<K, V> map = new HashMap<>();

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void set(K key, V value) {
        map.put(key, value);
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public boolean hasKey(K key) {
        return map.containsKey(key);
    }

    @Override
    public V remove(K key) {
        return map.remove(key);
    }

    @Override
    public Dictionary<K, V> forEachValue(Consumer<V> action) {
        map.values().forEach(action);

        return this;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
