package com.kevinearls.adventofcode.virus;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kevinearls.adventofcode.virus.NodeState.*;
import static org.junit.Assert.assertEquals;

public class VirusTest {
    Map<Location, NodeState> grid = new HashMap<>();

    @Before
    public void setup() {
        grid = new HashMap<>();
    }

    @Test
    public void testExampleDataSmall() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("..#");
        input.add("#..");
        input.add("...");

        Integer result = countBursts(input, 100);  // 100 iterations should return 26
        assertEquals(Integer.valueOf(26), result);
    }

    @Test
    public void testExampleDataBig() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("..#");
        input.add("#..");
        input.add("...");

        Integer result = countBursts(input, 10000000);
        System.out.println("Saw " + result + " bursts");
        assertEquals(Integer.valueOf(2511944), result);
    }


    @Test
    public void testPart1WithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        System.out.println("Got " + input.size() + " input lines");

        Integer result = countBursts(input, 10000000);
        System.out.println("Real data Saw " + result + " bursts");
        assertEquals(Integer.valueOf(2511424), result);  // 2511944 is too high
    }

    /**
     * FIXME Clean up this horrible mess!!!!!
     * @param input
     * @param bursts
     * @return
     * @throws Exception
     */
    public Integer countBursts(List<String> input, Integer bursts) throws Exception {
        int startRow = input.size() / 2;
        int startColumn = input.get(0).length() / 2;
        System.out.println("Start row " + startColumn + " start column " + startColumn);

        populateInitialGrid(input);

        // After a total of 10000 bursts of activity, 5587 bursts will have caused an infection.
        // Given your actual map, after 10000 bursts of activity, how many bursts cause a node to
        // become infected? (Do not count nodes that begin infected.)
        //
        // The virus carrier is on a clean node, so it turns left, infects the node, and moves left:
        //
        String direction = "up";
        String turning = "";
        Integer infectedCount = 0;
        int row = startRow;
        int column = startColumn;
        for (int i = 0; i < bursts; i++) {
            Location currentLocation = new Location(row, column);
            NodeState state = grid.get(currentLocation);
            if (state == null) {
                state = CLEAN;
                grid.put(currentLocation, state); // not yet?
            }

            // FIXME there must be a simler way
            if (state == CLEAN || state == INFECTED) {
                if (state == CLEAN) {
                    state = WEAKENED;
                    turning = "left";
                } else {
                    state = FLAGGED;
                    turning = "right";
                }
                switch (direction) {
                    case "up":
                        if (turning.equals("left")) {
                            column--;
                            direction = "left";
                        } else {
                            column++;
                            direction = "right";
                        }
                        break;
                    case "down":
                        if (turning.equals("left")) {
                            column++;
                            direction = "right";
                        } else {
                            column--;
                            direction = "left";
                        }
                        break;
                    case "left":
                        if (turning.equals("left")) {
                            row++;
                            direction = "down";
                        } else {
                            row--;
                            direction = "up";
                        }
                        break;
                    case "right":
                        if (turning.equals("left")) {
                            row--;
                            direction = "up";
                        } else {
                            row++;
                            direction = "down";
                        }
                        break;
                    default:
                        throw new RuntimeException("Unknown direction " + direction);
                }
            } else if (state == WEAKENED) {
                state = INFECTED;
                infectedCount++;
                // Keep moving in the same direction
                switch (direction) { 
                    case "up":
                        row--;
                        break;
                    case "down":
                        row++;
                        break;
                    case "left":
                        column--;
                        break;
                    case "right":
                        column++;
                        break;
                    default:
                        throw new RuntimeException("Weakened can't handle direction " + direction);
                }
            } else {  // for FLAGGED
                state = CLEAN;
                switch (direction) {  // Reverse direction
                    case "up":
                        direction="down";
                        row++;
                        break;
                    case "down":
                        direction="up";
                        row--;
                        break;
                    case "left":
                        direction="right";
                        column++;
                        break;
                    case "right":
                        direction = "left";
                        column--;
                        break;
                    default:
                        throw new RuntimeException("Weakened can't handle direction " + direction);
                }
            }
            grid.put(currentLocation, state);
        }

        return infectedCount;
    }

    private void populateInitialGrid(List<String> input) {
        int row = 0;
        for (String line : input) {
            for (int column = 0; column < line.length(); column++) {
                // Clean nodes are shown as .; infected nodes are shown as #.
                Location location = new Location(row, column);
                NodeState initialState = CLEAN;
                if (line.charAt(column) == '#') {
                    initialState = INFECTED;
                }
                grid.put(location, initialState);

            }
            row++;
        }
    }

    private void printGrid() {
        System.out.println("----------------------------------------");
        List<Location> locations = new ArrayList<>(grid.keySet());

        int minRow = Integer.MAX_VALUE;
        int minColumn = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;
        int maxColumn = Integer.MIN_VALUE;

        for (Location location : locations) {
            if (location.getRow() < minRow) {
                minRow = location.getRow();
            } else if (location.getRow() > maxRow) {
                maxRow = location.getRow();
            }
            if (location.getColumn() < minColumn) {
                minColumn = location.getColumn();
            } else if (location.getColumn() > maxColumn) {
                maxColumn = location.getColumn();
            }
        }

        for (int r = minRow; r <= maxRow; r++) {  // TODO need to keep track of min max row, or get
            for (int c = minColumn; c <= maxColumn; c++) {
                NodeState state = grid.get(new Location(r, c));
                if (state == null || state == CLEAN) {
                    System.out.print(".");
                } else if (state == WEAKENED){
                    System.out.print("W");
                } else if (state == INFECTED){
                    System.out.print("#");
                } else if (state == FLAGGED){
                    System.out.print("F");
                }
            }
            System.out.println();
        }
        System.out.println("----------------------------------------");
    }

    private List<String> loadFromFile(String filename) throws Exception {
        List<String> contents = new ArrayList<>();
        File target = Paths.get(getClass().getClassLoader().getResource(filename).toURI()).toFile();
        BufferedReader br = new BufferedReader(new FileReader(target));

        String line = br.readLine();
        while (line != null) {
            contents.add(line.trim());
            line = br.readLine();
        }

        return contents;
    }
}
