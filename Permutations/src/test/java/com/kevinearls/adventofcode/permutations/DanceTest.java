package com.kevinearls.adventofcode.permutations;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * --- Day 16: Permutation Promenade ---
 You come upon a very unusual sight; a group of programs here appear to be dancing.

 There are sixteen programs in total, named a through p. They start by standing in a line: a stands in position 0, b
 stands in position 1, and so on until p, which stands in position 15.

 The programs' dance consists of a sequence of dance moves:

 Spin, written sX, makes X programs move from the end to the front, but maintain their order otherwise. (For example,
 s3 on abcde produces cdeab).
 Exchange, written xA/B, makes the programs at positions A and B swap places.
 Partner, written pA/B, makes the programs named A and B swap places.
 For example, with only five programs standing in a line (abcde), they could do the following dance:

 s1, a spin of size 1: eabcd.
 x3/4, swapping the last two programs: eabdc.
 pe/b, swapping programs e and b: baedc.
 After finishing their dance, the programs end up in order baedc.

 You watch the dance for a while and record their dance moves (your puzzle input). In what order are the programs standing after their dance?
 */
public class DanceTest {

    @Test
    public void testExchange() {
        Dance dance = new Dance("eabcd");
        dance.exchange(3, 4);
        System.out.println(dance.getCurrentPrograms());
        assertEquals("eabdc", dance.getCurrentPrograms());

    }

    @Test
    public void testPartner() {
        Dance dance = new Dance("eabdc");
        dance.partner("e", "b");
        System.out.println(dance.getCurrentPrograms());
        assertEquals("baedc", dance.getCurrentPrograms());
    }


    @Test
    public void testSpin() {
        Dance dance = new Dance("abcde");
        dance.spin(1);
        System.out.println(dance.getCurrentPrograms());

    }

    /**
     *  s1, a spin of size 1: eabcd.
     x3/4, swapping the last two programs: eabdc.
     pe/b, swapping programs e and b: baedc.
     After finishing their dance, the programs end up in order baedc.
     * @throws Exception
     */
    @Test
    public void testWithExampleData() throws Exception {
        String programs = "abcde";
        //programs = "eabcd"; // TODO remove
        List<String> commands = new ArrayList<>();
        commands.add("s1");
        commands.add("x3/4");
        commands.add("pe/b");

        Dance exampleDance = new Dance(programs);
        takeSteps(exampleDance, commands);

        String result = exampleDance.getCurrentPrograms();
        System.out.println("With example data got results " + result);
        assertEquals("baedc", result);

        // Dance a second time for part2
        takeSteps(exampleDance, commands);
        System.out.println("After round 2" + exampleDance.getCurrentPrograms());
        assertEquals("ceadb", exampleDance.getCurrentPrograms());

    }

    @Test
    public void testWithReadData() throws Exception {
        String programs = "abcdefghijklmnop";
        Dance realDance = new Dance(programs);

        /// The input is one big line, we need to split it
        List<String> input = loadFromFile("input.txt");
        String[] parts = input.get(0).split(",");
        List<String> commands = Arrays.asList(parts);

        boolean done = false;
        int oneBillion = 1_000_000_000;
        List<String> outcomes = new ArrayList<>();
        int iteration = 0;
        // For part 2 the key is don't do this loop a billion times, but find a cycle that repeats, and
        // then calculate which would have been the billionth.
        while (!done && (iteration < oneBillion)) {
            takeSteps(realDance, commands);
            String outcome = realDance.getCurrentPrograms();
            if (outcomes.contains(outcome)) {
                done = true;
            } else {
                outcomes.add(outcome);
                iteration++;
            }
        }

        System.out.println("Finished after iteration " + iteration);
        String target = outcomes.get(oneBillion % outcomes.size() - 1);
        System.out.println("With real data got " + target);
        assertEquals("hklecbpnjigoafmd", target);

    }

    public void takeSteps(Dance dancer, List<String> commands) throws Exception {
        for (String command : commands) {
            String rest;
            String[] components;
            String operation = command.charAt(0) + "";  // s= spin, x = exchange, p = partner
            switch (operation) {
                case "s":
                    int spinCount = Integer.valueOf(command.substring(1));
                    dancer.spin(spinCount);
                    break;
                case "x":
                    rest = command.substring(1);
                    components = rest.split("/");
                    dancer.exchange(Integer.valueOf(components[0]), Integer.valueOf(components[1]));
                    break;
                case "p":
                    rest = command.substring(1);
                    components = rest.split("/");
                    dancer.partner(components[0], components[1]);
                    break;

                default:
                    throw new Exception("Got illegal command [" + command +"] from line [" + command + "]" );
            }
        }
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
