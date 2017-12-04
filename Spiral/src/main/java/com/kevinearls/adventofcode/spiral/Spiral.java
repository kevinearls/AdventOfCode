package com.kevinearls.adventofcode.spiral;


import java.util.logging.Logger;

import static com.kevinearls.adventofcode.spiral.Direction.*;

/**
 --- Part Two ---

 As a stress test on the system, the programs here clear the grid and then store the value 1 in square 1. Then, in the same allocation order as shown above, they store the sum of the values in all adjacent squares, including diagonals.

 So, the first few squares' values are chosen as follows:

 Square 1 starts with the value 1.
 Square 2 has only one adjacent filled square (with value 1), so it also stores 1.
 Square 3 has both of the above squares as neighbors and stores the sum of their values, 2.
 Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.
 Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.
 Once a square is written, its value does not change. Therefore, the first few squares would receive the following values:

 147  142  133  122   59
 304    5    4    2   57
 330   10    1    1   54
 351   11   23   25   26
 362  747  806--->   ...
 What is the first value written that is larger than your puzzle input?

 Your puzzle input is still 368078.
 */
public class Spiral {
    private static final Logger logger = Logger.getLogger(Spiral.class.getName());

    /**
     * NOTE: size must be odd and >0 to model a spiral!
     *
     * TODO Explain this!!!!
     * @param target We want the first value bigger than this
     * @return first value found > target
     */
    protected int findTargetWhileCreatingGrid(int target) {
        // Brute force, keep building grids till we find one big enough to hold the target
        int size = 3;
        while (true) {
            int[][] grid = new int[size][size];
            int row = size / 2;
            int column = size / 2;
            int numberOfEntriesInGrid = size * size;

            // Set the start value to 1
            grid[row][column] = 1;
            Direction currentDirection = RIGHT;
            int entriesFiled = 1;
            int valueAdded = -1;

            while (entriesFiled < numberOfEntriesInGrid) {
                switch (currentDirection) {
                    case UP:
                        row--;
                        valueAdded = sumOfNeighbors(grid, row, column);
                        grid[row][column] = valueAdded;
                        if (row == 0 || grid[row][column - 1] == 0) {
                            currentDirection = LEFT;  // if we're at the top, or the cell to the left is empty, move left
                        }
                        break;
                    case DOWN:
                        row++;
                        valueAdded = sumOfNeighbors(grid, row, column);
                        grid[row][column] = valueAdded;
                        if (row == (size - 1) || grid[row][column + 1] == 0) {
                            currentDirection = RIGHT;  // if we're at the bottom, or the column to the right is empty, move right
                        }
                        break;
                    case RIGHT:
                        column++;
                        valueAdded = sumOfNeighbors(grid, row, column);
                        grid[row][column] = valueAdded;
                        if (column == (size - 1) || grid[row - 1][column] == 0) {
                            currentDirection = UP; // If we're at the right margin, or the cell above is empty, change direction move up
                        }
                        break;
                    case LEFT:
                        column--;
                        valueAdded = sumOfNeighbors(grid, row, column);
                        grid[row][column] = valueAdded;
                        if (column == 0 || grid[row + 1][column] == 0) {
                            currentDirection = DOWN;  // If we're at the left margin, or the cell below is empty change to DOWN
                        }
                        break;
                }
                entriesFiled++;

                if (valueAdded > target) {
                    return valueAdded;
                }

            }
            size += 2;
        }
    }

    protected int sumOfNeighbors(int[][] grid, int row, int column) {
        logger.fine("SUM called on " + row + "," + column);
        int rowStart = Math.max(row-1, 0);
        int rowEnd = Math.min(row+1, grid.length-1);
        int columnStart = Math.max(column-1, 0);
        int columnEnd = Math.min(column+1, grid.length-1);

        int total = 0;
        for (int r = rowStart; r <= rowEnd; r++) {
            for (int c = columnStart; c <= columnEnd; c++) {
                total += grid[r][c];
            }
        }
        return total;
    }
}
