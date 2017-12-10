package com.kevinearls.adventofcode.knot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KnotsTest {
    Knots knots = new Knots();

    @Before
    public void setup() {
        knots = new Knots();
    }

    @Test
    public void testExampleData() {
        int[] list = {0, 1, 2, 3, 4};
        int[] inputLengths = {3, 4, 1, 5};
        int result = knots.computeHash(list, inputLengths);
        System.out.println("Result " + result);
        assertEquals(12, result);
    }

    @Test
    public void testWithReadData() {
        int[] list = new int[256];
        for (int i=0; i < 256; i++) {
            list[i] = i;
        }
        int[] day10Data = {94,84,0,79,2,27,81,1,123,93,218,23,103,255,254,243};
        int result = knots.computeHash(list, day10Data);
        System.out.println(result);

    }

    // TODO remove
    @Test
    public void reverseTest() {
        int[] simple = {1, 2, 3, 4, 5};
        knots.printArray(knots.reverse(simple));
    }

    @Test
    public void testConvertToAscii() {
        int[] simple = {1, 2, 3, 4, 5};
        knots.printArray(knots.convertToAscii(simple));
    }
}
