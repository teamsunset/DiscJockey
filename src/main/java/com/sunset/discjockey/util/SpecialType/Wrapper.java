package com.sunset.discjockey.util.SpecialType;

public class Wrapper<T> {
    public T value;

    public Wrapper(T value) {
        this.value = value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }
}
