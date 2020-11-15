package container;

import java.util.function.Consumer;

public interface DictionaryInterface<K, V> {
    boolean isEmpty();

    void set(K key, V value);

    V get(K key);

    boolean hasKey(K key);

    V remove(K key);

    DictionaryInterface forEachValue(Consumer<V> action);
}
