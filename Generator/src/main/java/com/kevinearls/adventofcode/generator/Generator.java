package com.kevinearls.adventofcode.generator;

public class Generator {
    Long factor;
    Long previousValue;
    Long multipleOf;

    public Generator(Long factor, Long seed, Long multipleOf) {
        this.previousValue = seed;
        this.factor = factor;
        this.multipleOf = multipleOf;
    }

    public Long generateNextValue() {
        boolean found = false;
        Long newValue = (previousValue * factor) % Integer.MAX_VALUE;
        while (newValue % multipleOf != 0) {
            newValue = (newValue * factor) % Integer.MAX_VALUE;
        }
        previousValue = newValue;
        return newValue;
    }
}
