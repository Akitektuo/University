package container;

import kotlin.jvm.functions.Function2;

import java.util.HashMap;

public class Dictionary<K, V> implements DictionaryInterface<K, V> {
    private final HashMap<K, V> map;

    public Dictionary() {
        map = new HashMap<>();
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
    public void filtered(Function2<K, V, Boolean> selection) {
        map.forEach((key, value) -> {
            if (!selection.invoke(key, value)) {
                map.remove(key);
            }
        });
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
