package com.sunset.discjockey.util.SpecialType;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public class WeakCollection<T> {

    private final Collection<WeakReference<T>> collection;

    public WeakCollection(@NotNull Collection<WeakReference<T>> collection) {
        this.collection = collection;
    }

    public void add(T value) {
        synchronized (collection) {
            collection.add(new WeakReference<>(value));
        }
    }

    public void iterate(Consumer<T> consumer) {
        synchronized (collection) {
            Iterator<WeakReference<T>> iterator = collection.iterator();
            while (iterator.hasNext()) {
                T value = iterator.next().get();
                if (value == null) {
                    iterator.remove();
                } else {
                    if (consumer != null)
                        consumer.accept(value);
                }
            }
        }
    }
}
