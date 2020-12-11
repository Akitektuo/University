package container;

import kotlin.jvm.functions.Function2;

import java.util.Map;

public interface DictionaryInterface<K, V> {
    boolean isEmpty();

    void set(K key, V value);

    V get(K key);

    boolean hasKey(K key);

    V remove(K key);

    ListInterface<K> getKeys();

    ListInterface<V> getValues();

    ListInterface<Map.Entry<K, V>> getEntries();

    DictionaryInterface<K, V> filter(Function2<K, V, Boolean> selection);

    boolean filtered(Function2<K, V, Boolean> selection);

    DictionaryInterface<K, V> clone();
}
