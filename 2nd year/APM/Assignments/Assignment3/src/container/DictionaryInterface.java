package container;

public interface DictionaryInterface<K, V> {
    boolean isEmpty();

    void set(K key, V value);

    V get(K key);

    boolean hasKey(K key);

    V remove(K key);
}
