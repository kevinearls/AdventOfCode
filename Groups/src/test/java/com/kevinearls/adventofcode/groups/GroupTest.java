package com.kevinearls.adventofcode.groups;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GroupTest {
    Groups groups;

    @Before
    public void setup() {
        groups = new Groups();
    }


    /**
     * {}, score of 1.
     {{{}}}, score of 1 + 2 + 3 = 6.
     {{},{}}, score of 1 + 2 + 2 = 5.
     {{{},{},{{}}}}, score of 1 + 2 + 3 + 3 + 3 + 4 = 16.
     {<a>,<a>,<a>,<a>}, score of 1.
     {{<ab>},{<ab>},{<ab>},{<ab>}}, score of 1 + 2 + 2 + 2 + 2 = 9.
     {{<!!>},{<!!>},{<!!>},{<!!>}}, score of 1 + 2 + 2 + 2 + 2 = 9.
     {{<a!>},{<a!>},{<a!>},{<ab>}}, score of 1 + 2 = 3.
     */
    @Test
    public void testGroupScoreWithExampleData() {
        assertEquals(1, groups.groupScore("{}"));
        assertEquals(6, groups.groupScore("{{{}}}"));
        assertEquals(5, groups.groupScore("{{},{}}"));
        assertEquals(16, groups.groupScore("{{{},{},{{}}}}"));
        assertEquals(1, groups.groupScore("{<{},{},{{}}>}"));
        assertEquals(1, groups.groupScore("{<a>,<a>,<a>,<a>}"));
        assertEquals(9, groups.groupScore("{{<!!>},{<!!>},{<!!>},{<!!>}}"));
        assertEquals(3, groups.groupScore("{{<a!>},{<a!>},{<a!>},{<ab>}}"));
    }

    /**
     * Now, you're ready to remove the garbage.

     To prove you've removed it, you need to count all of the characters within the garbage. The leading and trailing
     < and > don't count, nor do any canceled characters or the ! doing the canceling.

     <>, 0 characters.
     <random characters>, 17 characters.
     <<<<>, 3 characters.
     <{!>}>, 2 characters.
     <!!>, 0 characters.
     <!!!>>, 0 characters.
     <{o"i!a,<{i<a>, 10 characters.
     How many non-canceled characters are within the garbage in your puzzle input?
     */
    @Test
    public void testRemoveGarbageFromExampleData() {
        assertEquals(0, groups.countGarbageCharacters("<>"));
        assertEquals(17, groups.countGarbageCharacters("<random characters>"));
        assertEquals(3, groups.countGarbageCharacters("<<<<>"));
        assertEquals(2, groups.countGarbageCharacters("<{!>}>"));
        assertEquals(0, groups.countGarbageCharacters("<!!>"));
        assertEquals(0, groups.countGarbageCharacters("<!!!>>"));
        assertEquals(10, groups.countGarbageCharacters("<{o\"i!a,<{i<a>"));
    }

    @Test
    public void testPart2ReadData() throws Exception {
        List<String> data = loadFromFile("input.txt");
        String line = data.get(0);
        Integer result = groups.countGarbageCharacters(line);
        System.out.println("Removed : " + result + " garbage characters");
        assertEquals(new Integer(7685), result);
    }

    @Test
    public void testReadData() throws Exception {
        List<String> data = loadFromFile("input.txt");
        String line = data.get(0);
        Integer result = groups.groupScore(line);
        System.out.println("Result: " + result);
        assertEquals(new Integer(16021), result);
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
