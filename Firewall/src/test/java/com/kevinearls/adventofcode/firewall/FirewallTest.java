package com.kevinearls.adventofcode.firewall;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class FirewallTest {
    private List<String> exampleInput = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(FirewallTest.class.getName());

    @Before
    public void setup() {
        exampleInput.add("0: 3");
        exampleInput.add("1: 2");
        exampleInput.add("4: 4");
        exampleInput.add("6: 4");
    }

    private Firewall createExampleFirewall() {
        Firewall firewall = new Firewall(exampleInput);
        return firewall;
    }

    @Test
    public void testWithExampleData() {
        Firewall firewall = createExampleFirewall();
        Integer severity = firewall.calculateCrossingSeverity();
        logger.info("Severity with example data was " + severity);
        assertEquals(Integer.valueOf(24), severity);
    }

    @Test
    public void testPart2WithExampleData() {
        Integer delay = findLeastDelayToCross(exampleInput);
        logger.info("Delay with example data was " + delay);
        assertEquals(Integer.valueOf(10), delay);
    }

    @Test
    public void testPart1WithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        Firewall firewall = new Firewall(input);
        Map<Integer, Layer> layers = firewall.getLayers();
        assertEquals("Test firewall has wrong number of layers.", 43, layers.size());
        assertEquals("Wrong max layer value", Integer.valueOf(96), firewall.getMaxLayerId());

        Integer severity = firewall.calculateCrossingSeverity();
        logger.info("Severity of real data was " + severity);
        assertEquals(Integer.valueOf(1876), severity);
    }

    @Test
    public void testPart2WithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        Integer delay = findLeastDelayToCross(input);

        System.out.println("DELAY WAS " + delay);
        assertEquals(Integer.valueOf(3964778), delay);
    }

    public Integer findLeastDelayToCross(List<String> input) {
        long startTime = System.currentTimeMillis();
        Firewall firewall = new Firewall(input);
        Integer delay = 0;
        Firewall saved = new Firewall(firewall);
        boolean canCross = firewall.canCross();
        while (!canCross) {
            delay++;
            if (delay % 100000 == 0) {
                long currentTime = System.currentTimeMillis();
                long duration = currentTime - startTime;
                System.out.println("Starting iteration " + delay + " after " + duration + " ms");
            }
            firewall = new Firewall(saved);
            firewall.step();

            saved = new Firewall(firewall);
            if (firewall.canCross()) {
                System.out.println("Can cross");
                canCross = true;
            }
        }

        logger.info("DELAY WAS " + delay);
        return delay;
    }


    @Test
    public void testLayer() {
        Layer l1 = new Layer(1, 4);
        for (int i=0; i < 10; i++) {
            l1.move();
            logger.info("Moved to " + l1.getCurrentPosition());
        }
        logger.info("Moved to " + l1.getCurrentPosition());
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
