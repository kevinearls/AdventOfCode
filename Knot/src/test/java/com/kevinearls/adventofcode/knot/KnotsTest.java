package com.kevinearls.adventofcode.knot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KnotsTest {
    Knots knots = new Knots();

    @Before
    public void setup() {
        knots = new Knots();
    }

    /**
     * The empty string becomes a2582a3a0e66e6e86e3812dcb672a272.
     AoC 2017 becomes 33efeb34ea91902bb2f59c9920caa6cd.
     1,2,3 becomes 3efbe78a8d82f29979031a4aa0b16a9d.
     1,2,4 becomes 63960835bcdc130f0b66d7ff4f6a5a8e.
     */
    @Test
    public void testPart2ExampleData() {
        int[] list = new int[256];
        for (int i=0; i < 256; i++) {
            list[i] = i;
        }
        assertEquals("a2582a3a0e66e6e86e3812dcb672a272", knots.computePart2HashRedux(list, ""));
        assertEquals("33efeb34ea91902bb2f59c9920caa6cd", knots.computePart2HashRedux(list, "AoC 2017"));
        assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", knots.computePart2HashRedux(list, "1,2,3"));
        assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", knots.computePart2HashRedux(list, "1,2,4"));

    }

    @Test
    public void testWithRealData() {
        int[] list = new int[256];
        for (int i=0; i < 256; i++) {
            list[i] = i;
        }
        int[] day10Data = {94,84,0,79,2,27,81,1,123,93,218,23,103,255,254,243};
        String day10Part2Data = "94,84,0,79,2,27,81,1,123,93,218,23,103,255,254,243";
        String result = knots.computePart2HashRedux(list, day10Part2Data);
        System.out.println(result);

        assertEquals("541dc3180fd4b72881e39cf925a50253", result);

    }

    // TODO remove
    @Test
    public void reverseTest() {
        int[] simple = {1, 2, 3, 4, 5};
        knots.printArray(knots.reverse(simple));
    }

    @Test
    public void testConvertToAscii() {
        String simple = "1,2,3,4,5";
        knots.printArray(knots.convertToAscii(simple));
    }

    @Test
    public void testComputeLengths() {
        String simple = "1,2,3,";
        // expected 49,44,50,44,51,17,31,73,47,23
        knots.printArray(knots.computeLengths(simple));
    }

    @Test
    public void testXorInJava() {
        // 65 ^ 27 ^ 9 ^ 1 ^ 4 ^ 3 ^ 40 ^ 50 ^ 91 ^ 7 ^ 6 ^ 0 ^ 2 ^ 5 ^ 68 ^ 22 = 64
        int[] data = {65 , 27 , 9 , 1 , 4 , 3 , 40 , 50 , 91 , 7 , 6 , 0 , 2 , 5 , 68 , 22};
        int total = 0;
        for (int i=0; i < data.length; i++) {
            total = total ^ data[i];
        }
        System.out.println("GOT "+ total);
        assertEquals(64, total);
    }

    @Test
    public void convertToHex() {
        //are 64, 7, 255, they correspond to the hexadecimal numbers 40, 07, ff, and so the first six characters
        // of the hash would be 4007ff. Because every Knot
        int[] simple = {64, 7, 255};
        String result = knots.convertToHex(simple);
        System.out.println("Got " + result);
        assertEquals("4007ff", result);
    }
}
