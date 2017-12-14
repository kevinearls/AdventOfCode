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
    public void testPart2WithExampleData() {
        String testInput = "flqrgnkx";
        Integer regions = defragmenter.calculateRegions(testInput);
        System.out.println("Part 2 With test data got " + regions);
        assertEquals(Integer.valueOf(1242), regions);
    }

    @Test
    public void testWithRealData() {
        String testInput = "ugkiagan";
        Integer occupiedSquares = defragmenter.calculateUsedSquares(testInput);
        System.out.println("With test data got " + occupiedSquares);
        assertEquals(Integer.valueOf(8292), occupiedSquares);
    }

    @Test
    public void testPart2WithRealData() {
        String testInput = "ugkiagan";
        Integer regions = defragmenter.calculateRegions(testInput);
        System.out.println("Part 2 With real data got " + regions);
        assertEquals(Integer.valueOf(1069), regions);
    }

    // ---------------------------------------------------
    @Test
    public void testCounter() throws Exception {
        String testInput = "a0c2017";
        Integer count = defragmenter.convertAndCountBits(testInput);
        assertEquals(Integer.valueOf(9), count);
    }
}
