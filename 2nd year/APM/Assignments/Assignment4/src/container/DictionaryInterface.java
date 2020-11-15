package container;

import kotlin.jvm.functions.Function2;

public interface DictionaryInterface<K, V> {
    boolean isEmpty();

    void set(K key, V value);

    V get(K key);

    boolean hasKey(K key);

    V remove(K key);

    ListInterface<K> getKeys();

    ListInterface<V> getValues();

    DictionaryInterface<K, V> filter(Function2<K, V, Boolean> selection);

    void filtered(Function2<K, V, Boolean> selection);
}
