package com.kevinearls.adventofcode.hexgrid;

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
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Crossing the bridge, you've barely reached the other side of the stream when a program comes up to you, clearly in
 * distress. "It's my child process," she says, "he's gotten lost in an infinite grid!"

 Fortunately for her, you have plenty of experience with infinite grids.

 Unfortunately for you, it's a hex grid.

 The hexagons ("hexes") in this grid are aligned such that adjacent hexes can be found to the north, northeast,
 southeast, south, southwest, and northwest:

 \ n  /
 nw +--+ ne
 /    \
 -+      +-
 \    /
 sw +--+ se
 / s  \
 You have the path the child process took. Starting where he started, you need to determine the fewest number of steps
 required to reach him. (A "step" means to move from the hex you are in to any adjacent hex.)

 For example:

 ne,ne,ne is 3 steps away.
 ne,ne,sw,sw is 0 steps away (back where you started).
 ne,ne,s,s is 2 steps away (se,se).
 se,sw,se,sw,sw is 3 steps away (s,s,sw).

 --- Part Two ---

 How many steps away is the furthest he ever got from his starting position?



 */
public class HexGridTest {
    public HexGrid hexgrid;
    private static Logger logger = Logger.getLogger(HexGridTest.class.getName());

    @Before
    public void setup() {
        hexgrid = new HexGrid();
    }

    @Test
    public void testWithExampleData() {
        Map<String, Integer> testData = new HashMap<>();
        testData.put("ne,ne,ne", 3);
        testData.put("ne,ne,sw,sw", 0);
        testData.put("ne,ne,s,s", 2);
        testData.put("se,sw,se,sw,sw", 3);

        for (String route : testData.keySet()) {
            logger.info("Checking [" + route + "]");
            Integer steps = hexgrid.followPath(route);
            assertEquals(testData.get(route), new Integer(steps));
        }
    }

    @Test
    public void testWithRealData() throws Exception {
        List<String> fileContents = loadFromFile("input.txt");
        assertEquals("Input file should only have 1 line", 1, fileContents.size());
        String instructions = fileContents.get(0);
        Integer steps = hexgrid.followPath(instructions);

        logger.info("It took " + steps + " steps to get home");
        assertEquals(new Integer(808), steps);

        // Part 2
        int maxSteps = hexgrid.getMaxDistance();
        logger.info("Maximum distance " + maxSteps);
        assertEquals(1556, maxSteps);
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
