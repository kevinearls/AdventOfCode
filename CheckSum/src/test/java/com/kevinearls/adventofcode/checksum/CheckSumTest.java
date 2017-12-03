package com.kevinearls.adventofcode.checksum;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class CheckSumTest {

    @Test
    public void testExampleInputs() {
        CheckSum checkSum = new CheckSum();
        Integer[][] testData = new Integer[][]{
                { 5, 1, 9, 5 },
                { 7, 5, 3 },
                { 2, 4, 6, 8},
        };

        Integer result = checkSum.checksum(testData);
        System.out.printf("Result " + result);
        assertEquals(new Integer(18), result);
    }

    @Ignore
    @Test
    public void testExampleDataWithStreams() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("testData.txt").toURI());

        Stream<String> lines = Files.lines(path);
        lines.forEach(System.out::println);

        // TODO create an integer array from the data
        List<String> list = new ArrayList<>();

        lines.close();
    }


    @Test
    public void testExampleDataOldSchool() throws Exception {
        File target = Paths.get(getClass().getClassLoader().getResource("testData.txt").toURI()).toFile();
        BufferedReader br = new BufferedReader(new FileReader(target));

        List<List<Integer>> data = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            List<String> lineValues = Arrays.asList(line.split("\t"));
            List<Integer> integerValues = new ArrayList<>();
            for (String cellValue : lineValues) {
                Integer integerValue = Integer.parseInt(cellValue);
                integerValues.add(integerValue);
            }
            data.add(integerValues);

            line = br.readLine();
        }

        // Convert the data we just got to an array of integers
        Integer[][] array = new Integer[data.size()][];
        int i = 0;
        for (List<Integer> nestedList : data) {
            array[i++] = nestedList.toArray(new Integer[nestedList.size()]);
        }

        CheckSum checkSum = new CheckSum();
        Integer result = checkSum.checksum(array);
        System.out.println(result);
        assertEquals(new Integer(42378), result);
    }
}
