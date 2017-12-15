package com.kevinearls.adventofcode.generator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The generators both work on the same principle. To create its next value, a generator will take the previous value
 * it produced, multiply it by a factor (generator A uses 16807; generator B uses 48271), and then keep the remainder
 * of dividing that resulting product by 2147483647. That final remainder is the value it produces next.

 To calculate each generator's first value, it instead uses a specific starting value as its "previous value" (as
 listed in your puzzle input).

 For example, suppose that for starting values, generator A uses 65, while generator B uses 8921. Then, the first five
 pairs of generated values are:
 */

public class GeneratorTest {
    private static final Long GENERATOR_A_FACTOR = 16807L;
    private static final Long GENERATOR_B_FACTOR = 48271L;
    private Part1Generator generatorA;
    private Part1Generator generatorB;

    @Before
    public void setup() {

    }

    /**
     * For example, suppose that for starting values, generator A uses 65, while generator B uses 8921. Then, the first five pairs of generated values are:

     --Gen. A--  --Gen. B--
     1092455   430625591
     1181022009  1233683848
     245556042  1431495498
     1744312007   137874439
     1352636452   285222916
     */
    @Test
    public void testPart1WithExampleData() {
        generatorA = new Part1Generator(GENERATOR_A_FACTOR, 65L);
        generatorB = new Part1Generator(GENERATOR_B_FACTOR, 8921L);

        int count = 0;
        for (int i=0; i < 40000000; i++) {
            long a = generatorA.generateNextValue();
            long b = generatorB.generateNextValue();

            if ((a & 65535) == (b & 65535)) {
                count++;
            }
        }
        System.out.println("Found " + count);
        assertEquals(588, count);
    }

    /**
     * Part1Generator A starts with 591
     Part1Generator B starts with 393
     */
    @Test
    public void testPart1WithRealData() {
        generatorA = new Part1Generator(GENERATOR_A_FACTOR, 591L);
        generatorB = new Part1Generator(GENERATOR_B_FACTOR, 393L);

        int count = 0;
        for (int i=0; i < 40000000; i++) {
            long a = generatorA.generateNextValue();
            long b = generatorB.generateNextValue();

            if ((a & 65535) == (b & 65535)) {
                count++;
            }
        }
        System.out.println("Found " + count);
        assertEquals(619, count);
    }


}
