package com.kevinearls.adventofcode.plumber;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PlumbingTest {
    Plumber plumber;

    @Before
    public void setup() {
        plumber = new Plumber();
    }


    /**
     * 0 <-> 2
     1 <-> 1
     2 <-> 0, 3, 4
     3 <-> 2, 4
     4 <-> 2, 3, 6
     5 <-> 6
     6 <-> 4, 5
     */
    @Test
    public void testWithExampleData() {
        List<String> input = new ArrayList<>();
        input.add("0 <-> 2");
        input.add("1 <-> 1");
        input.add("2 <-> 0, 3, 4");
        input.add("3 <-> 2, 4");
        input.add("4 <-> 2, 3, 6");
        input.add("5 <-> 6");
        input.add("6 <-> 4, 5");

        Integer result = plumber.groupSize(input);
        System.out.println("Result for test data " + result);
        assertEquals(Integer.valueOf(6), result);
    }

    @Test
    public void testWithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        System.out.println("GOT  " + input.size() + " lines");

        Integer result = plumber.groupSize(input);
        System.out.println("Result for real data " + result);
        assertEquals(Integer.valueOf(378), result);
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


    //--------------- ERASE AFTER HERE --------------------------
    @Test
    public void testMap() {
        List<String> input = new ArrayList<>();
        input.add("0 <-> 2");
        input.add("1 <-> 1");
        input.add("2 <-> 0, 3, 4");
        input.add("3 <-> 2, 4");
        input.add("4 <-> 2, 3, 6");
        input.add("5 <-> 6");
        input.add("6 <-> 4, 5");

        Map<Integer, List<Integer>> result = plumber.createInputMap(input);
        for (Integer key : result.keySet()) {
            List<Integer> values = result.get(key);
            System.out.print(key + " <-> "  );
            for (Integer value : values) {
                System.out.print(value + ",");
            }
            System.out.println();
        }
    }

    @Test
    public void eraseMe() {
        String testData = "4 <-> 2, 3, 6";
        testData = testData.replaceAll(",", "");
        String[] parts = testData.split("\\s+");
        for (String part : parts) {
            System.out.println(part);
        }
    }

}
