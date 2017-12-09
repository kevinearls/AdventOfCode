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
        //assertEquals(1, groups.groupScore("{<{},{},{{}}>}"));
        assertEquals(1, groups.groupScore("{<a>,<a>,<a>,<a>}"));
        assertEquals(9, groups.groupScore("{{<!!>},{<!!>},{<!!>},{<!!>}}"));
        assertEquals(3, groups.groupScore("{{<a!>},{<a!>},{<a!>},{<ab>}}"));
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
