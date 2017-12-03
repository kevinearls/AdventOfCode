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

    /**
     * For Day2 - part 2
     * 5 9 2 8
     9 4 7 3
     3 8 6 5
     In the first row, the only two numbers that evenly divide are 8 and 2; the result of this division is 4.
     In the second row, the two numbers are 9 and 3; the result is 3.
     In the third row, the result is 2.
     In this example, the sum of the results would be 4 + 3 + 2 = 9.
     *
     *
     */
    @Test
    public void testExampleInputs() {
        CheckSum checkSum = new CheckSum();
        Integer[][] testData = new Integer[][]{
                { 5, 9, 2, 8 },
                { 9, 4, 7,3 },
                { 3, 8, 6, 5},
        };

        Integer result = checkSum.checksum(testData);
        System.out.printf("Result " + result);
        assertEquals(new Integer(9), result);
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
        assertEquals(new Integer(246), result);
    }
}
