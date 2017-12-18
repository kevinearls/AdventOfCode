package com.kevinearls.adventofcode.duet;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DuetTest {

    /**
     * set a 1
     add a 2
     mul a a
     mod a 5
     snd a
     set a 0
     rcv a
     jgz a -1
     set a 1
     jgz a -2

     The first four instructions set a to 1, add 2 to it, square it, and then set it to itself modulo 5, resulting in a value of 4.
     Then, a sound with frequency 4 (the value of a) is played.
     After that, a is set to 0, causing the subsequent rcv and jgz instructions to both be skipped (rcv because a is 0, and jgz because a is not greater than 0).
     Finally, a is set to 1, causing the next jgz instruction to activate, jumping back two instructions to another jump, which jumps again to the rcv, which ultimately triggers the recover operation.
     At the time the recover operation is executed, the frequency of the last sound played is 4.

     What is the value of the recovered frequency (the value of the most recently played sound) the first time a rcv instruction is executed with a non-zero value?

     */
    @Test
    public void testPart1WithExampleData() {
        List<String> input = new ArrayList<>();
        input.add("set a 1");
        input.add("add a 2");
        input.add("mul a a");
        input.add("mod a 5");
        input.add("snd a");
        input.add("set a 0");
        input.add("rcv a");
        input.add("jgz a -1");
        input.add("set a 1");
        input.add("jgz a -2");

        Processor p = new Processor(input);
        Long lastSoundPlayed = p.process();
        System.out.println(lastSoundPlayed);
        assertEquals(Long.valueOf(4), lastSoundPlayed);
    }

    @Test
    public void testPart1WithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        Processor p = new Processor(input);
        Long lastSoundPlayed = p.process();
        System.out.println(lastSoundPlayed);
        assertEquals(Long.valueOf(3423), lastSoundPlayed);
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
