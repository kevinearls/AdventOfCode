package com.kevinearls.adventofcode.firewall;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class FirewallTest {
    private Firewall firewall;
    private List<String> exampleInput = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(FirewallTest.class.getName());

    private Firewall createExampleFirewall() {
        exampleInput.add("0: 3");
        exampleInput.add("1: 2");
        exampleInput.add("4: 4");
        exampleInput.add("6: 4");

        firewall = new Firewall(exampleInput);
        return firewall;
    }

    @Test
    public void testWithExampleData() {
        firewall = createExampleFirewall();
        Map<Integer, Layer> layers = firewall.getLayers();

        //logger.info("Max layer ID is " + firewall.getMaxLayerId());

        Integer severity = firewall.calculateCrossingSeverity();
        logger.info("Severity with example data was " + severity);
        assertEquals(Integer.valueOf(24), severity);
    }

    @Test
    public void testWithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        firewall = new Firewall(input);
        Map<Integer, Layer> layers = firewall.getLayers();
        assertEquals("Test firewall has wrong number of layers.", 43, layers.size());
        assertEquals("Wrong max layer value", Integer.valueOf(96), firewall.getMaxLayerId());

        Integer severity = firewall.calculateCrossingSeverity();
        logger.info("Severity of real data was " + severity);
        assertEquals(Integer.valueOf(1876), severity);
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
