package com.kevinearls.adventofcode.spiral;


import static com.kevinearls.adventofcode.spiral.Direction.*;

/**
 * ou come across an experimental new kind of memory stored on an infinite two-dimensional grid.

 Each square on the grid is allocated in a spiral pattern starting at a location marked 1 and then counting up while
 spiraling outward. For example, the first few squares are allocated like this:

 17  16  15  14  13
 18   5   4   3  12
 19   6   1   2  11
 20   7   8   9  10
 21  22  23---> ...
 While this is very space-efficient (no squares are skipped), requested data must be carried back to square 1 (the
 location of the only access port for this memory system) by programs that can only move up, down, left, or right.
 They always take the shortest path: the Manhattan Distance between the location of the data and square 1.

 For example:

 Data from square 1 is carried 0 steps, since it's at the access port.
 Data from square 12 is carried 3 steps, such as: down, left, left.
 Data from square 23 is carried only 2 steps: up twice.
 Data from square 1024 must be carried 31 steps.
 How many steps are required to carry the data from the square identified in your puzzle input all the way to the access port?

 Your puzzle input is 368078.
 */
public class Spiral {
    public Integer calculateSteps(Integer targetValue) {
        int gridSize = calculateGridSize(targetValue);
        int[][] grid = createGrid(gridSize);

        int foundRow=-1, foundColumn=-1;
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                if (grid[row][column] == targetValue) {
                    foundRow = row;
                    foundColumn = column;
                    break;
                }
            }
        }
        // TODO add logging and use logger.debug
        //System.out.println("Found target at row " + foundRow + " column " + foundColumn);
        int middle = gridSize / 2;
        int rowDiff = Math.abs(foundColumn - middle);
        int columnDiff = Math.abs(foundRow - middle);
        return rowDiff + columnDiff;
    }


    /**
     * TODO check input, should be an odd number > 0
     * @param target
     * @return
     */
    protected int calculateGridSize(int target) {
        int size = 1;
        int square = size * size;
        while(target > square) {
            size += 2;
            square = size * size;
        }

        return size;
    }

    protected int[][] createGrid(int size) {
        int[][] grid = new int[size][size];
        // Set the start location and max value
        int row = size / 2;
        int column = size / 2;
        int maxValue = size * size;

        // Set the start value to 1
        grid[row][column] = 1;
        int nextValue = 2;
        Direction currentDirection = RIGHT;

        while (nextValue <= maxValue) {
            switch (currentDirection) {
                case UP:
                    row--;
                    grid[row][column] = nextValue;
                    //System.out.println("Moved UP set " + row + ", " + column + " to " + nextValue);
                    // if we're at the top, or the cell to the left is empty, move left
                    if (row == 0 || grid[row][column - 1] == 0) {
                        currentDirection = LEFT;
                    }
                    break;
                case DOWN:
                    row++;
                    grid[row][column] = nextValue;
                    //System.out.println("Moved DOWN set " + row + ", " + column + " to " + nextValue);
                    // if we're at the bottom, or the column to the right is empty, move right
                    if (row == (size - 1) || grid[row][column + 1] == 0) {
                        currentDirection = RIGHT;
                    }
                    break;
                case RIGHT:
                    column++;
                    grid[row][column] = nextValue;
                    //System.out.println("Moved RIGHT set " + row + ", " + column + " to " + nextValue);
                    // If we're at the right margin, or the cell above is empty, change direction move up
                    if (column == (size - 1) || grid[row - 1][column] == 0) {
                        currentDirection = UP;
                    }
                    break;
                case LEFT:
                    column--;
                    grid[row][column] = nextValue;
                    //System.out.println("Moved LEFT set " + row + ", " + column + " to " + nextValue);
                    // If we're at the left margin, or the cell below is empty change to DOWN
                    if (column == 0 || grid[row + 1][column] == 0) {
                        currentDirection = DOWN;
                    }
                    break;
            }
            nextValue++;
        }
        return grid;
    }
}
