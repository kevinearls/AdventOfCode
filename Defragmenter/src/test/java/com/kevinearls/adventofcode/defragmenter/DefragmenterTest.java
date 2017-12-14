package com.kevinearls.adventofcode.defragmenter;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefragmenterTest {
    Defragmenter defragmenter;

    @Before
    public void setup() {
        defragmenter = new Defragmenter();
    }

    @Test
    public void testWithExampleData() {
        String testInput = "flqrgnkx";
        Integer occupiedSquares = defragmenter.calculateUsedSquares(testInput);
        System.out.println("With test data got " + occupiedSquares);
        assertEquals(Integer.valueOf(8108), occupiedSquares);
    }

    @Test
    public void testWithRealData() {
        String testInput = "ugkiagan";
        Integer occupiedSquares = defragmenter.calculateUsedSquares(testInput);
        System.out.println("With test data got " + occupiedSquares);
        assertEquals(Integer.valueOf(8292), occupiedSquares);
    }

    // ---------------------------------------------------
    @Test
    public void testCounter() throws Exception {
        String testInput = "a0c2017";
        Integer count = defragmenter.convertAndCountBits(testInput);
        assertEquals(Integer.valueOf(9), count);
    }
}
