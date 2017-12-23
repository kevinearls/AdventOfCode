package com.kevinearls.adventofcode.tubes;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TubesTest {

    /**
     *
     * |
     |  +--+
     A  |  C
     F---|----E|--+
     |  |  |  D
     +B-+  +--+
     */
    @Test
    public void testPart1WithExampleData() {
        List<String> input = List.of(
                "    |         ",
                "    |  +--+   ",
                "    A  |  C   ",
                "F---|----E|--+",
                "    |  |  |  D",
                "    +B-+  +--+"
        );

        Tubes tubes = new Tubes(input);
        tubes.printMap();
        List<String> lettersSeen = tubes.follow();
        String result = String.join("", lettersSeen);
        System.out.println("Letters Seen: " + result);

        assertEquals("ABCDEF", result);
    }

    @Test
    public void testPart1WithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        Tubes tubes = new Tubes(input);
        List<String> lettersSeen = tubes.follow();
        String result = String.join("", lettersSeen);
        System.out.println("Letters Seen: " + result);

        assertEquals("BPDKCZWHGT", result);

        System.out.println("Steps taken " + tubes.getStepsTaken());
        assertEquals(Integer.valueOf(17728), tubes.getStepsTaken());
    }

    private List<String> loadFromFile(String filename) throws Exception {
        List<String> contents = new ArrayList<>();
        File target = Paths.get(getClass().getClassLoader().getResource(filename).toURI()).toFile();
        BufferedReader br = new BufferedReader(new FileReader(target));

        String line = br.readLine();
        while (line != null) {
            contents.add(line);
            line = br.readLine();
        }

        return contents;
    }
}
