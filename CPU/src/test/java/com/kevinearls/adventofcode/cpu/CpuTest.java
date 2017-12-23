package com.kevinearls.adventofcode.cpu;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CpuTest {
    private CPU cpu;

    @Before
    public void setUp() {
        cpu = new CPU();
    }


    /**
     * You receive a signal directly from the CPU. Because of your recent assistance with jump instructions, it would
     * like you to compute the result of a series of unusual register instructions.

     Each instruction consists of several parts: the register to modify, whether to increase or decrease that register's
     value, the amount by which to increase or decrease it, and a condition. If the condition fails, skip the instruction without modifying the register. The registers all start at 0. The instructions look like this:

     b inc 5 if a > 1
     a inc 1 if b < 5
     c dec -10 if a >= 1
     c inc -20 if c == 10
     These instructions would be processed as follows:

     Because a starts at 0, it is not greater than 1, and so b is not modified.
     a is increased by 1 (to 1) because b is less than 5 (it is 0).
     c is decreased by -10 (to 10) because a is now greater than or equal to 1 (it is 1).
     c is increased by -20 (to -10) because c is equal to 10.
     After this process, the largest value in any register is 1.

     You might also encounter <= (less than or equal to) or != (not equal to). However, the CPU doesn't have the
     bandwidth to tell you what all the registers are named, and leaves that to you to determine.

     What is the largest value in any register after completing the instructions in your puzzle input?

     PART 2

     To be safe, the CPU also needs to know the highest value held in any register during this process so that it can decide
     how much memory to allocate to these operations. For example, in the above instructions, the highest value ever held was
     10 (in register c after the third instruction was evaluated).

     */
    @Test
    public void testExampleData() {
        List<Instruction> instructions = new ArrayList<>();
        List<String> input = List.of(
                "b inc 5 if a > 1",
                "a inc 1 if b < 5",
                "c dec -10 if a >= 1",
                "c inc -20 if c == 10"
        );

        // First pass, parse data and load all registers
        for (String s : input) {
            Instruction instruction = new Instruction(s);
            instructions.add(instruction);
            cpu.setRegister(instruction.getRegisterName(), new Integer(0));
        }

        for (Instruction instruction : instructions) {
            instruction.evaluateAndApply(cpu);
        }

        Integer result = cpu.findGreatestRegisterValue();
        System.out.println("Greatest registerValue  " + result);
        assertEquals(new Integer(1), result);

        Integer highestValueHeld = cpu.getHighestValueHeld();
        System.out.println("Highest Value Held " + highestValueHeld);
        assertEquals(new Integer(10), highestValueHeld);
    }


    @Test
    public void testWithRealData() throws Exception {
        List<String> testData = loadFromFile("input.txt");
        List<Instruction> instructions = new ArrayList<>();
        System.out.println("Got " + testData.size() + " instructions");
        for (String s : testData) {
            Instruction instruction = new Instruction(s);
            instructions.add(instruction);
            cpu.setRegister(instruction.getRegisterName(), new Integer(0));
        }

        for (Instruction instruction : instructions) {
            instruction.evaluateAndApply(cpu);
        }

        Integer result = cpu.findGreatestRegisterValue();
        System.out.println("REAL RESULT " + result);
        assertEquals(new Integer(4416), result);

        Integer highestValueHeld = cpu.getHighestValueHeld();
        System.out.println("Highest Value Held " + highestValueHeld);
        assertEquals(new Integer(5199), highestValueHeld);
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
