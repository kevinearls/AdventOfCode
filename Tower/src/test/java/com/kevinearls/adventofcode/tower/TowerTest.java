package com.kevinearls.adventofcode.tower;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TowerTest {
    Tower tower = null;

    @Before
    public void setup() {
        tower = new Tower();
    }


    /**
     * Test Data:
     * pbga (66)
     xhth (57)
     ebii (61)
     havc (66)
     ktlj (57)
     fwft (72) -> ktlj, cntj, xhth
     qoyq (66)
     padx (45) -> pbga, havc, qoyq
     tknk (41) -> ugml, padx, fwft
     jptl (61)
     ugml (68) -> gyxo, ebii, jptl
     gyxo (61)
     cntj (57)

     name (weight) Optional arrow, children
     NOTE we may see a child before it is given as a node, see cntj above
     */
    @Test
    public void testExampleData() {
        List<String> exampleData = new ArrayList<>();
        exampleData.add("pbga (66)");
        exampleData.add("xhth (57)");
        exampleData.add("ebii (61)");
        exampleData.add("havc (66)");
        exampleData.add("ktlj (57)");
        exampleData.add("fwft (72) -> ktlj, cntj, xhth");
        exampleData.add("qoyq (66");
        exampleData.add("padx (45) -> pbga, havc, qoyq");
        exampleData.add("tknk (41) -> ugml, padx, fwft");
        exampleData.add("jptl (61)");
        exampleData.add("ugml (68) -> gyxo, ebii, jptl");
        exampleData.add("gyxo (61)");
        exampleData.add("cntj (57)");

        for (String example : exampleData) {
            InputLine line = new InputLine(example);
            // 1. Add the node name to the tower (if it's not already been seen)
            // 2. for all children, add to tower and/or mark as seen
            tower.addToTower(line.getName());
            for (String childName : line.getChildNames()) {
                tower.markAsChild(childName);
            }
        }

        System.out.println("ROOT: " + tower.findRootNodeName());
        assertEquals("tknk", tower.findRootNodeName());
    }


    @Test
    public void testRealData() throws Exception {
        List<String> testData = loadFromFIle("input.txt");
        for (String example : testData) {
            InputLine line = new InputLine(example);
            // 1. Add the node name to the tower (if it's not already been seen)
            // 2. for all children, add to tower and/or mark as seen
            tower.addToTower(line.getName());
            for (String childName : line.getChildNames()) {
                tower.markAsChild(childName);
            }
        }

        System.out.println("ROOT: " + tower.findRootNodeName());
        assertEquals("cyrupz", tower.findRootNodeName());

    }


    @Test
    public void testInputLine() {
        /*List<String> examples = new ArrayList<>();
        examples.add("pbga (66)");
        examples.add("fwft (72) -> ktlj, cntj, xhth");
        examples.add("tknk (41) -> ugml, padx, fwft");
        examples.add("cntj (57)");

        for (String example : examples) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("Testing: " + example);
            InputLine inputLine = new InputLine(example);
            System.out.println(inputLine.getName() + " " + inputLine.getWeight() + " " + inputLine.getChildNames());
        } */

        InputLine line1 = new InputLine("pbga (66)");
        assertEquals("pbga", line1.getName());
        assertEquals(new Integer(66), line1.getWeight());
        assertEquals(0, line1.childNames.size());

        InputLine line2 = new InputLine("fwft (72) -> ktlj, cntj, xhth");
        assertEquals("fwft", line2.getName());
        assertEquals(new Integer(72), line2.getWeight());
        assertEquals(3, line2.childNames.size());

        String[] names = {"ktlj", "cntj", "xhth"};
        List<String> expectedChildNames = Arrays.asList(names);
        assertEquals(expectedChildNames, line2.childNames);

    }

    private List<String> loadFromFIle(String filename) throws Exception {
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
