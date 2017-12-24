package com.kevinearls.adventofcode.bridge;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.of;
import static org.junit.Assert.*;

public class BridgeTest {

    @Before
    public void setup() {
    }

    @Test
    public void testPart1WithExampleData() {
        List<Component> components = loadExampleData();
        System.out.println("Got " + components.size());

        Integer strongest = getStrongest(components);
        System.out.println("Strongest " + strongest);
        assertEquals(Integer.valueOf(31) , strongest);
    }

    @Test
    public void testPart2WithExampleData() {
        List<Component> components = loadExampleData();
        System.out.println("Got " + components.size());

        Integer strongest = getStrongestLongest(components);
        System.out.println("Strongest " + strongest);
        assertEquals(Integer.valueOf(19) , strongest);
    }

    @Test
    public void testPart1WithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        List<Component> components = createComponentList(input);

        Integer strongest = getStrongest(components);
        System.out.println("Strongest " + strongest);
        assertEquals(Integer.valueOf(1695) , strongest);
    }


    @Test
    public void testPart2WithRealData() throws Exception {
        List<String> input = loadFromFile("input.txt");
        List<Component> components = createComponentList(input);

        Integer strongest = getStrongestLongest(components);
        System.out.println("Strongest " + strongest);
        assertEquals(Integer.valueOf(1673) , strongest);
    }

    public Integer getStrongest(List<Component> components) {
        Integer strongest = Integer.MIN_VALUE;

        List<List<Component>> bridges = createBridges(0, components);
        System.out.println("Got " + bridges.size() + " bridges");
        for (List<Component> bridge : bridges) {
            int strength = 0;
            for (Component c : bridge) {
                strength += c.getPortA() + c.getPortB();
            }
            if (strength > strongest) {
                strongest = strength;
            }
        }
        return strongest;
    }

    public Integer getStrongestLongest(List<Component> components) {
        Integer strongest = Integer.MIN_VALUE;
        Integer longest = Integer.MIN_VALUE;

        List<List<Component>> bridges = createBridges(0, components);
        // pass 1, find longest
        for (List<Component> bridge : bridges) {
            if (bridge.size() > longest) {
                longest = bridge.size();
            }
        }
        System.out.println("Out of " + bridges.size() + " the longest is " + longest);
        int used = 0;
        for (List<Component> bridge : bridges) {
            if (bridge.size() == longest) {
                used++;
                int strength = 0;
                for (Component c : bridge) {
                    strength += c.getPortA() + c.getPortB();
                }
                if (strength > strongest) {
                    strongest = strength;
                }
            }
        }

        System.out.println("Found " + used + " bridges of size " + longest);

        return strongest;
    }

    public List<List<Component>> createBridges(Integer startPort, List<Component> components) {
        List<List<Component>> bridges = new ArrayList<>();
        List<Component> startComponents = new ArrayList<>();
        for (Component c : components) {
            if (c.getPortA() == startPort || c.getPortB() == startPort) {
                startComponents.add(c);
            }
        }

        if (startComponents.size() == 0) {
            return bridges;  // Or return null?
        }

        // For each start component, add it to the bridge, then recurse
        for (Component sc : startComponents) {
            // Create a copy of the components and remove this one
            List<Component> remainingComponents = components.stream().collect(Collectors.toList());
            remainingComponents.remove(sc);
            Integer otherPortForComponent = sc.getOtherPort(startPort);
            List<List<Component>> subBridges = createBridges(otherPortForComponent, remainingComponents);
            if (subBridges.size() == 0) {
                List<Component> thisBridge = new ArrayList<>();
                thisBridge.add(sc);
                bridges.add(thisBridge);

            }
            for (List<Component> sub : subBridges) {
                List<Component> thisBridge = new ArrayList<>();
                thisBridge.add(sc);
                thisBridge.addAll(sub);
                bridges.add(thisBridge);
            }
        }

        return bridges;
    }

    private List<Component> loadExampleData() {
        List<String> input  = List.of (
                "0/2",
                "2/2",
                "2/3",
                "3/4",
                "3/5",
                "0/1",
                "10/1",
                "9/10"
        );

        List<Component> components = createComponentList(input);
        return components;
    }


    private List<Component> createComponentList(List<String> input) {
        List<Component> components = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.trim().split("/");
            Integer portA = Integer.parseInt(parts[0]);
            Integer portB = Integer.parseInt(parts[1]);
            Component component = new Component(portA, portB);
            components.add(component);
        }
        return components;
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
