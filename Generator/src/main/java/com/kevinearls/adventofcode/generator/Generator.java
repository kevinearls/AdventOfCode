package com.kevinearls.adventofcode.generator;


/**
 * The generators both work on the same principle. To create its next value, a generator will take the previous value
 * it produced, multiply it by a factor (generator A uses 16807; generator B uses 48271), and then keep the remainder
 * of dividing that resulting product by 2147483647. That final remainder is the value it produces next.

 To calculate each generator's first value, it instead uses a specific starting value as its "previous value" (as
 listed in your puzzle input).

 For example, suppose that for starting values, generator A uses 65, while generator B uses 8921. Then, the first five
 pairs of generated values are:
 */
public class Generator {
    Long factor;
    Long previousValue;

    /**
     *
     * @param factor
     * @param seed
     */
    public Generator(Long factor, Long seed) {
        this.previousValue = seed;
        this.factor = factor;
    }

    public Long generateNextValue() {
        Long newValue = (previousValue * factor) % Integer.MAX_VALUE;
        previousValue = newValue;
        return newValue;
    }
}
