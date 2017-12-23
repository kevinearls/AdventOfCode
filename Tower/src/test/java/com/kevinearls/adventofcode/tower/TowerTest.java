package com.kevinearls.adventofcode.tower;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.*;

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
        List<String> exampleData = List.of(
        "pbga (66)",
        "xhth (57)",
        "ebii (61)",
        "havc (66)",
        "ktlj (57)",
        "fwft (72) -> ktlj, cntj, xhth",
        "qoyq (66)",
        "padx (45) -> pbga, havc, qoyq",
        "tknk (41) -> ugml, padx, fwft",
        "jptl (61)",
        "ugml (68) -> gyxo, ebii, jptl",
        "gyxo (61)",
        "cntj (57)"
        );

        for (String example : exampleData) {
            InputLine line = new InputLine(example);
            tower.addToTower(line.getName(), line.getWeight(), line.childNames);
        }

        System.out.println("ROOT: " + tower.findRootNodeName());
        assertEquals("tknk", tower.findRootNodeName());

        System.out.println("---------- Part 2---------------");
        int result = tower.balance(tower.getNodeByName(tower.findRootNodeName()), 0);
        assertEquals(60, result);
    }


    @Test
    public void testRealData() throws Exception {
        List<String> testData = loadFromFIle("input.txt");
        for (String example : testData) {
            InputLine line = new InputLine(example);
            tower.addToTower(line.getName(), line.getWeight(), line.childNames);
        }

        assertEquals("cyrupz", tower.findRootNodeName());

        // "cwwwj" needs to be balanced and set to 193 instead of 210
        Integer result = tower.balance(tower.getNodeByName(tower.findRootNodeName()), 0);
        System.out.println("RESULT " + result);
        assertEquals(new Integer(193), result);
    }

    @Test
    public void testInputLine() {
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
