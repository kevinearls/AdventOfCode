package com.kevinearls.adventofcode.spiral;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpiralTest {

    /**
     * ou come across an experimental new kind of memory stored on an infinite two-dimensional grid.

     Each square on the grid is allocated in a spiral pattern starting at a location marked 1 and then
     counting up while spiraling outward. For example, the first few squares are allocated like this:

     17  16  15  14  13
     18   5   4   3  12
     19   6   1   2  11
     20   7   8   9  10
     21  22  23---> ...
     While this is very space-efficient (no squares are skipped), requested data must be carried back to
     square 1 (the location of the only access port for this memory system) by programs that can only move
     up, down, left, or right. They always take the shortest path: the Manhattan Distance between the location
     of the data and square 1.

     For example:

     Data from square 1 is carried 0 steps, since it's at the access port.
     Data from square 12 is carried 3 steps, such as: down, left, left.
     Data from square 23 is carried only 2 steps: up twice.
     Data from square 1024 must be carried 31 steps.
     How many steps are required to carry the data from the square identified in your puzzle input all the way to the access port?

     Your puzzle input is 368078.
     */
    @Test
    public void testExampleData() {
        Spiral spiral = new Spiral();

        assertEquals(new Integer(0), spiral.calculateSteps(1));
        assertEquals(new Integer(3), spiral.calculateSteps(12));
        assertEquals(new Integer(2), spiral.calculateSteps(23));
        assertEquals(new Integer(31), spiral.calculateSteps(1024));
    }

    @Test
    public void testTargetInstance() {
        Spiral spiral = new Spiral();
        int target = 368078;
        int result = spiral.calculateSteps(target);
        System.out.println("GOT " + result);
        assertEquals(371, result);
    }

    @Test
    public void testCalculateGridSize() {
        Spiral spiral = new Spiral();
        assertEquals(1, spiral.calculateGridSize(1));
        assertEquals(5, spiral.calculateGridSize(12));
        assertEquals(5, spiral.calculateGridSize(23));
        assertEquals(9, spiral.calculateGridSize(55));
    }
}
