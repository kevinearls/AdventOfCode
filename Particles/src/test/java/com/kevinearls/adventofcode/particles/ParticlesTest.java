package com.kevinearls.adventofcode.particles;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ParticlesTest {

    @Ignore
    @Test
    public void eraseMe() throws Exception {
        List<String> input = loadFromFile("input.txt");
        System.out.println("Input has " + input.size() + " entries");
        String first = input.get(0);
        System.out.println(first);
        String[] parts = first.split(",");
    }

    @Test
    public void testPart1WithExampleData() {
        List<String> input = new ArrayList<>();
        input.add("p=<3,0,0>, v=<2,0,0>, a=<-1,0,0>");
        input.add("p=<4,0,0>, v=<0,0,0>, a=<-2,0,0>");

        List<Particle> particles = new ArrayList<>();
        Integer id=0;
        for (String line : input) {
            particles.add(new Particle(id, line));
            id++;
        }

        // Print the initial setup
        for (Particle p : particles) {
            System.out.println(p.toString());
        }

        // Move once, then print
        System.out.println("---------- Moving --------------------");

        for (int i=0; i < 3; i++) {
            for (Particle p : particles) {
                p.move();
            }
            System.out.println(">>> After move " + i);
            for (Particle p : particles) {
                System.out.println(p.toString());
            }
        }

        System.out.println("-------------- Determine closest ------------------");
        Particle closest = null;
        Long leastDistance = Long.MAX_VALUE;
        for (Particle p : particles) {
            if (p.getManhattanDistance() < leastDistance) {
                leastDistance = p.getManhattanDistance();
                closest = p;
            }
        }

        System.out.println("Distance " + leastDistance);
        System.out.println("Closest " + closest.toString());
    }

    /**
     * This is a brute force solution found by trial and error.  I imageine there is someway
     * to calculate when you've reached a state where 1 particle will always be the closest, but
     * I don't really have time to figure that out now.
     */
    @Test
    public void testPart2WithRealData() throws Exception {
        List<Particle> particles = getParticles();
        Integer aliveAtEnd = 0;
        Particle closest = null;
        for (int i=0; i < 10000; i++) {
            // Move all particles
            for (Particle p : particles) {
                if (!p.isRemoved()) {
                    p.move();
                }
            }

            // Part 2, eliminate duplicates.  First build a positions mpa
            Map<Position, List<Particle>> locationsMap = new HashMap<>();
            for (Particle p : particles) {
               if (!p.isRemoved()) {
                   List<Particle> occupants = locationsMap.get(p.position);
                   if (occupants == null) {
                       occupants = new ArrayList<>();
                   }
                   occupants.add(p);
                   locationsMap.put(p.position, occupants);
               }
            }

            // Now eliminate duplicates by marking them as removed; comment this out to get Part1 result
            for (Position p : locationsMap.keySet()) {
                List<Particle> occupants = locationsMap.get(p);
                if (occupants.size() > 1) {
                    for (Particle removeMe : occupants) {
                        removeMe.setRemoved(true);
                    }
                }
            }

            if (i % 1000 == 0 ) {
                // Find closest
                Long leastDistance = Long.MAX_VALUE;
                for (Particle p : particles) {
                    if (!p.isRemoved()) {
                        if (p.getManhattanDistance() < leastDistance) {
                            leastDistance = p.getManhattanDistance();
                            closest = p;
                        }
                    }
                }
                System.out.println("At iteration " + i + " Closest: " + closest);
            }
        }

        // Determine how many were left at the end
        for (Particle p : particles) {
            if (!p.isRemoved()) {
                aliveAtEnd++;
            }
        }
        System.out.println("Alive at end " + aliveAtEnd);
        assertEquals(Integer.valueOf(448), aliveAtEnd);
    }

    private List<Particle> getParticles() throws Exception {
        List<String> input = loadFromFile("input.txt");
        List<Particle> particles = new ArrayList<>();
        Integer id=0;
        for (String line : input) {
            particles.add(new Particle(id, line));
            id++;
        }
        return particles;
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
