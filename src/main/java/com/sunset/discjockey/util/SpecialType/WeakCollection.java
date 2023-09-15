package com.sunset.discjockey.util.SpecialType;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class WeakCollection<T> {

    private final Collection<WeakReference<T>> collection;

    private final List<WeakReference<T>> waitingQueue = new ArrayList<>();

    private boolean isIterating = false;

    public WeakCollection(@NotNull Collection<WeakReference<T>> collection) {
        this.collection = collection;
    }

    public void add(T value) {
        synchronized (collection) {
            if (isIterating) {
                waitingQueue.add(new WeakReference<>(value));
            } else {
                collection.add(new WeakReference<>(value));
            }
        }
    }

    public void iterate(Consumer<T> consumer) {
        synchronized (collection) {
            isIterating = true;
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
            isIterating = false;
            this.pushWaiting();
        }
    }

    private void pushWaiting() {
        synchronized (collection) {
            collection.addAll(waitingQueue);
            waitingQueue.clear();
        }
    }
}
