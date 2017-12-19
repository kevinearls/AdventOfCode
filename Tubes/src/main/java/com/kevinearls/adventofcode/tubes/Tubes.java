package com.kevinearls.adventofcode.tubes;

import java.util.ArrayList;
import java.util.List;

public class Tubes {
    String[][] map;
    Integer stepsTaken = 0;

    public Tubes(List<String> input) {
        int longestLine = Integer.MIN_VALUE;
        int lineCount = 0;
        for (String line : input) {
            lineCount++;
            if (line.length() > longestLine) {
                longestLine = line.length();
            }
        }

        // create map and pad all cells to avoid NPEs
        map = new String[lineCount][longestLine];
        for (int row = 0; row < lineCount; row++) {
            for (int column = 0; column < longestLine; column++) {
                map[row][column] = " ";
            }
        }

        // Now fill with real data
        int row = 0;
        for (String line :input) {
            for (int column = 0; column < line.length(); column++) {
                map[row][column] = line.charAt(column) + "";
            }
            row++;
        }
    }

    public void printMap() {
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {
                System.out.print(map[row][column]);
            }
            System.out.println();
        }
    }

    public Integer getStepsTaken() {
        return stepsTaken;
    }

    public List<String> follow() {
        List<String> lettersSeen = new ArrayList<>();
        // initial positions
        int row = 0;
        int column = 0;
        // Find the start, which should be in the first row
        column = getStartColumn(column);

        String direction = "down";  // TODO use an enum
        boolean finished = false;
        while (!finished) {
            if ((row <0) || (row >= map.length) || (column<0) || column >= map[row].length) {
                return lettersSeen;
            }
            String currentCell = map[row][column];
            //System.out.println("At " + row + ", " + column + " value [" + currentCell + "] row length " + map[row].length );
            if (currentCell.equals(" ")) {
                return lettersSeen;
            }
            stepsTaken++;

            if (Character.isLetter(currentCell.charAt(0))) {   // TODO do this here, or at end?
                lettersSeen.add(currentCell);
            }

            // If we hit a + we have to turn;
            // if going up or down look left or right,
            // if going left or right, look up or down
            if (currentCell.equals("+")) {
                if (direction.equals("left") || direction.equals("right")) {
                    if (row > 0 && map[row-1][column].equals(" ")) {
                        direction = "down";
                    } else {
                        direction = "up";
                    }
                } else {
                    if (column > 0 && map[row][column-1].equals(" ")) {
                        direction = "right";
                    } else {
                        direction = "left";
                    }
                }
            }


            // Now Turn.....
            switch (direction) {
                case "down":
                    row++;
                    break;
                case "up":
                    row--;
                    break;
                case "left":
                    column--;
                    break;
                case "right":
                    column++;
                    break;
                default:
                    throw new RuntimeException("Got unknown direction " + direction);
            }
        }

        return lettersSeen;
    }

    private int getStartColumn(int column) {
        while (column < map[0].length) {
            if (map[0][column].equals("|")) {
                break;
            } else {
                column++;
            }
        }
        return column;
    }


}
