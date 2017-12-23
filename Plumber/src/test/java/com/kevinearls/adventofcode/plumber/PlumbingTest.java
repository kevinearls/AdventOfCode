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
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PlumbingTest {
    Plumber plumber;
    List<String> exampleInput;

    @Before
    public void setup() {
        plumber = new Plumber();
        exampleInput = List.of(
                "0 <-> 2",
                "1 <-> 1",
                "2 <-> 0, 3, 4",
                "3 <-> 2, 4",
                "4 <-> 2, 3, 6",
                "5 <-> 6",
                "6 <-> 4, 5"
        );
    }

    @Test
    public void testWithExampleData() {
        Integer result = plumber.groupSize(exampleInput);
        System.out.println("Result for test data part 1 " + result);
        assertEquals(Integer.valueOf(6), result);
    }

    @Test
    public void testPart2WithExampleData() {
        Integer result = plumber.numberOfGroups(exampleInput);
        System.out.println("Result for test data part 2" + result);
        assertEquals(Integer.valueOf(2), result);
    }

    @Test
    public void testWithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        System.out.println("GOT  " + input.size() + " lines");

        Integer result = plumber.groupSize(input);
        System.out.println("Result for real data part 1" + result);
        assertEquals(Integer.valueOf(378), result);
    }

    @Test
    public void testPart2WithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        System.out.println("GOT  " + input.size() + " lines");

        Integer result = plumber.numberOfGroups(input);
        System.out.println("Result for real data part 2 " + result);
        assertEquals(Integer.valueOf(204), result);

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


    @Test
    public void testComputeReachableFrom() throws Exception {
        Map<Integer, List<Integer>> network = plumber.createInputMap(exampleInput);
        Set<Integer> reachable = plumber.computeReachableFrom(Integer.valueOf(0), network);
        System.out.println(reachable);

        Set<Integer> reachable2 = plumber.computeReachableFrom(Integer.valueOf(1), network);
        System.out.println(reachable2);
    }

    @Test
    public void testMap() {
        Map<Integer, List<Integer>> result = plumber.createInputMap(exampleInput);
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
