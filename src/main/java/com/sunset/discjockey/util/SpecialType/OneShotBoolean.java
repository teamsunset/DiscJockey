package com.sunset.discjockey.util.SpecialType;

public class OneShotBoolean extends Property<Boolean> {
    public OneShotBoolean() {
        super(false,
                wov -> {
                    boolean v = wov.get();
                    wov.set(false);
                    return v;
                },
                Wrapper::set);
    }

    public OneShotBoolean(boolean origin) {
        super(origin,
                wov -> {
                    boolean v = wov.get();
                    wov.set(false);
                    return v;
                },
                Wrapper::set);
    }
}
