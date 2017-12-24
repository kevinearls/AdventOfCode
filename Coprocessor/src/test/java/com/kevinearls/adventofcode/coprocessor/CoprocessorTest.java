package com.kevinearls.adventofcode.coprocessor;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
public class CoprocessorTest {
    Coprocessor coprocessor;

    @Before
    public void setup() {
        coprocessor = new Coprocessor(true);
    }

    @Ignore
    @Test
    public void testWithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        System.out.println("Got " + input.size() + " instuctions");

        long iterations = 0;
        long startTime = System.currentTimeMillis();
        int currentPosition = 0;
        while (currentPosition >= 0 && currentPosition < input.size()) {
            String[] parts = input.get(currentPosition).split("\\s+");
            if (parts.length != 3) {
                throw new RuntimeException("Wrong number of parts");
            }
            String instruction = parts[0].trim();
            String x = parts[1].trim();
            String y = parts[2].trim();

            switch (instruction) {
                case "set":
                    coprocessor.set(x, y);
                    currentPosition++;
                    break;
                case "sub":
                    coprocessor.sub(x, y);
                    currentPosition++;
                    break;
                case "mul":
                    coprocessor.mul(x, y);
                    currentPosition++;
                    break;
                case "jnz":
                    Long xValue = coprocessor.getValue(x);
                    if (xValue != 0) {
                        currentPosition += coprocessor.getValue(y);
                    } else {
                        currentPosition++;
                    }
                    break;
                default:
                    throw new RuntimeException("Shouldn't get here");
            }

            if ((instruction.equals("sub") || instruction.equals("set") || instruction.equals("mul")) && (x.equals("b") || x.equals("c"))) {
                System.out.println("Set [" + x + "] to " + coprocessor.getValue(x));
            }

            iterations++;
            if (iterations % 10_000_000 == 0) {
                long duration = System.currentTimeMillis() - startTime;
                System.out.println(iterations + " iterations after " + duration + " ms");
                //Thread.sleep(1000);
            }
        }

        System.out.println("Multiplications: " + coprocessor.getMultiplyExecuted());
        assertEquals(Integer.valueOf(5929), coprocessor.getMultiplyExecuted());

    }

    @Test
    public void setTest() {
        coprocessor.set("a", "5");
        coprocessor.set("b", "-8");
        coprocessor.set("c", "a");

        Long a = coprocessor.getRegister("a");
        Long b = coprocessor.getRegister("b");
        Long c = coprocessor.getRegister("c");
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("c: " + c);

        assertEquals(Long.valueOf(5), a);
        assertEquals(Long.valueOf(-8), b);
        assertEquals(Long.valueOf(5), c);
    }

    @Test
    public void testSub() {
        coprocessor.set("a", "5");
        coprocessor.set("b", "-8");

        coprocessor.sub("a", "3");
        System.out.println("a " + coprocessor.getRegister("a"));
        assertEquals(Long.valueOf(2), coprocessor.getRegister("a"));

        coprocessor.sub("a", "b");
        System.out.println("a " + coprocessor.getRegister("a"));
        assertEquals(Long.valueOf(10), coprocessor.getRegister("a"));
    }

    @Test
    public void testMultiply() {
        coprocessor.set("a", "5");
        coprocessor.set("b", "7");

        coprocessor.mul("a", "3");
        System.out.println("a " + coprocessor.getRegister("a"));
        assertEquals(Long.valueOf(15), coprocessor.getRegister("a"));

        coprocessor.mul("a", "b");
        System.out.println("a " + coprocessor.getRegister("a"));
        assertEquals(Long.valueOf(105), coprocessor.getRegister("a"));

        Integer multiplications = coprocessor.getMultiplyExecuted();
        System.out.println("Multiplications: " + multiplications);
        assertEquals(Integer.valueOf(2), multiplications);

    }

    @Test
    public void stupid() {
        System.out.println("Got " + method());
    }

    int method() {
        int h = 0;
        int b = 107900;
        int c = 124900;

        System.out.println("b = " + b);
        System.out.println("c = " + c);
        while (true) {
            boolean found = new BigInteger(Integer.toString(b)).isProbablePrime(10000000);

            if (!found) {
                h++;
            }
            if (b == c) {
                return h;
            }
            b += 17;
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
