package container;

import kotlin.jvm.functions.Function2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Dictionary<K, V> implements DictionaryInterface<K, V> {
    private final ConcurrentHashMap<K, V> map;

    public Dictionary() {
        map = new ConcurrentHashMap<>();
    }

    public Dictionary(Dictionary<K, V> dictionaryToCopy) {
        map = new ConcurrentHashMap<>(dictionaryToCopy.map);
    }

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
    public ListInterface<K> getKeys() {
        return new List<>(map.keySet());
    }

    @Override
    public ListInterface<V> getValues() {
        return new List<>(map.values());
    }

    @Override
    public ListInterface<Map.Entry<K, V>> getEntries() {
        return new List<>(map.entrySet());
    }

    @Override
    public boolean filtered(Function2<K, V, Boolean> selection) {
        var modified = new AtomicBoolean(false);

        new HashMap<>(map).forEach((key, value) -> {
            if (!selection.invoke(key, value)) {
                map.remove(key);
                modified.set(true);
            }
        });

        return modified.get();
    }

    @Override
    public DictionaryInterface<K, V> clone() {
        return new Dictionary<>(this);
    }

    @Override
    public DictionaryInterface<K, V> filter(Function2<K, V, Boolean> selection) {
        var filteredMap = new Dictionary<K, V>();

        map.forEach((key, value) -> {
            if (selection.invoke(key, value)) {
                filteredMap.set(key, value);
            }
        });

        return filteredMap;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
