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

import static org.junit.Assert.assertEquals;

public class VirusTest {
    Map<Location, Boolean> grid = new HashMap<>();

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

        Integer result = countBursts(input, 70);
        System.out.println("Saw " + result + " bursts");
        assertEquals(Integer.valueOf(41), result);
    }

    @Test
    public void testExampleDataBig() throws Exception {
        List<String> input = new ArrayList<>();
        input.add("..#");
        input.add("#..");
        input.add("...");

        Integer result = countBursts(input, 10000);
        System.out.println("Saw " + result + " bursts");
        assertEquals(Integer.valueOf(5587), result);
        //printGrid();
    }


    @Test
    public void testPart1WithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        System.out.println("Got " + input.size() + " input lines");

        Integer result = countBursts(input, 10000);
        System.out.println("Saw " + result + " bursts");
        assertEquals(Integer.valueOf(5587), result);  // 5243 is too low  5456 is too high
    }

    @Test
    public void stupid() {
        List<String> input = new ArrayList<>();
        input.add("..#");
        input.add("#..");
        input.add("...");

        int startRow = input.size() / 2;
        int startColumn = input.get(0).length() / 2;

        System.out.println("Start row " + startColumn + " start column " + startColumn);
    }

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
        String direction ="up";
        String turning = "";
        Integer infectedCount = 0;
        int row = startRow;
        int column = startColumn;
        for (int i=0; i < bursts; i++) {
            Location currentLocation = new Location(row, column);
            //System.out.println(currentLocation);
            Boolean infected = grid.get(currentLocation);
            if (infected == null) {
                infected = false;
                grid.put(currentLocation, infected); // not yet?
            }
            if (infected) {
                grid.put(currentLocation, false);
                turning = "right";
            } else {
                grid.put(currentLocation, true);
                infectedCount++;
                turning = "left";
            }

            switch (direction) {  // TODO combine cases where possible
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
        }

        return infectedCount;
    }

    private void populateInitialGrid(List<String> input) {
        int row = 0;
        for (String line : input) {
            for (int column = 0; column < line.length(); column++) {
                // Clean nodes are shown as .; infected nodes are shown as #.
                Location location = new Location(row, column);
                Boolean infected = false;
                if (line.charAt(column) == '#') {
                    infected = true;
                }
                grid.put(location, infected);

            }
            row++;
        }
    }

    private void printGrid() {
        System.out.println("----------------------------------------");
        List<Location> locations = new ArrayList<>(grid.keySet());
        for (Location l : locations) {
            System.out.println(l);
        }
        System.out.println("-------------------------------------------");

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

        System.out.println("Got " + locations.size() + " locations");
        System.out.println("Rows " + minRow + " to " + maxRow + " columns " + minColumn + " to " + maxColumn);

        for (int r = minRow; r <= maxRow; r++) {  // TODO need to keep track of min max row, or get
            for (int c = minColumn; c <= maxColumn; c++) {
                Boolean infected = grid.get(new Location(r, c));
                if (infected == null || !infected) {
                    System.out.print(".");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
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
