package org.mariotaku.commons.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultiMap<K, V> {

    private final Map<K, List<V>> map;
    @Nullable
    private final Equatable<K> equatable;

    public MultiMap() {
        this(new HashMap<K, List<V>>(), null);
    }

    public MultiMap(Map<K, List<V>> map) {
        this(map, null);
    }

    public MultiMap(@Nullable Map<K, List<V>> map, @Nullable Equatable<K> equatable) {
        this.map = map != null ? map : new HashMap<K, List<V>>();
        this.equatable = equatable;
    }

    public V getFirst(@NotNull K key) {
        final List<V> values = get(key);
        if (values == null || values.isEmpty()) return null;
        return values.get(0);
    }

    public List<V> get(@NotNull K key) {
        if (equatable != null) {
            for (Map.Entry<K, List<V>> entry : map.entrySet()) {
                if (equatable.equals(entry.getKey(), key)) {
                    return entry.getValue();
                }
            }
        }
        return map.get(key);
    }

    public final void add(@NotNull K key, V value) {
        List<V> list = get(key);
        if (list == null) {
            list = new ArrayList<>();
            map.put(key, list);
        }
        list.add(value);
    }

    public final void addAll(@NotNull K key, V[] values) {
        List<V> list = get(key);
        if (list == null) {
            list = new ArrayList<>();
            map.put(key, list);
        }
        if (values != null) {
            Collections.addAll(list, values);
        } else {
            list.add(null);
        }
    }

    public final void addAll(@NotNull K key, @Nullable Collection<V> values) {
        List<V> list = get(key);
        if (list == null) {
            list = new ArrayList<>();
            map.put(key, list);
        }
        if (values != null) {
            list.addAll(values);
        } else {
            list.add(null);
        }
    }

    public final void remove(@NotNull K key, V value) {
        List<V> list = get(key);
        if (list != null) {
            list.remove(value);
        }
    }

    public final void removeAll(@NotNull K key) {
        map.remove(key);
    }

    public final void clear() {
        map.clear();
    }

    public void addFrom(@NotNull MultiMap<K, V> another) {
        for (Map.Entry<K, List<V>> entry : another.map.entrySet()) {
            addAll(entry.getKey(), entry.getValue());
        }
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    public List<Pair<K, V>> toList() {
        final ArrayList<Pair<K, V>> list = new ArrayList<>();
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            for (V value : entry.getValue()) {
                list.add(Pair.create(entry.getKey(), value));
            }
        }
        return list;
    }

    public Set<Map.Entry<K, List<V>>> entrySet() {
        return map.entrySet();
    }

    public interface Equatable<T> {
        boolean equals(T left, T right);
    }
}
