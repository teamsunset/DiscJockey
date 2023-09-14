package com.sunset.discjockey.util.SpecialType;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * C# style property
 *
 * @param <T> type of the value
 *            getter: wov -> {}
 *            setter: (wov, newValue) -> {}
 */
public class Property<T> {
    Wrapper<T> value;

    private Function<Wrapper<T>, T> getter;

    private BiConsumer<Wrapper<T>, T> setter;


    public Property(T value, Function<Wrapper<T>, T> getter, BiConsumer<Wrapper<T>, T> setter) {
        this.value = new Wrapper<>(value);
        this.getter = getter;
        this.setter = setter;
    }

    public Property(Function<Wrapper<T>, T> getter, BiConsumer<Wrapper<T>, T> setter) {
        this(null, getter, setter);
    }

    public T get() {
        if (this.value == null) {
            return null;
        } else {
            return getter.apply(this.value);
        }
    }

    public void set(T value) {
        if (this.value == null) {
            this.value = new Wrapper<>(value);
        } else {
            setter.accept(this.value, value);
        }
    }
}
