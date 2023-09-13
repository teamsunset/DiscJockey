package com.sunset.discjockey.util.SpecialType;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class OneShotBoolean extends Property<Boolean> {
    public OneShotBoolean() {
        super(false,
                wov -> {
                    boolean v = wov.get();
                    wov.set(false);
                    return v;
                },
                (wov, dv) -> {
                    wov.set(true);
                });
    }
}
