package com.kevinearls.adventofcode.plumber;

import java.util.*;

public class Plumber {
    //Set<Integer> reachable = new HashSet<>();
    // Map<Integer, List<Integer>> network = new HashMap<>();

    public Integer groupSize(List<String> input) {
        Set<Integer> reachable = new HashSet<>();
        Map<Integer, List<Integer>> network = createInputMap(input);
        Queue<Integer> blah = new LinkedList<>();

        /// Start with 0, add that to reachable
        reachable.add(new Integer(0));

        boolean done=false;
        Integer current = 0;
        while (!done) {
            List<Integer> neighbors = network.get(current);
            boolean addedNewReachable = false;
            for (Integer neigbor : neighbors) {
                if (!reachable.contains(neigbor)) {
                    addedNewReachable = true;
                    reachable.add(neigbor);
                    blah.add(neigbor);
                }
            }
            if (blah.isEmpty()) {
                done = true;
            } else {
                current = blah.remove();
            }
        }

        return reachable.size();
    }


    public Map<Integer, List<Integer>> createInputMap(List<String> input) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (String line : input ) {
            line = line.replaceAll(",", "");
            String[] parts = line.split("\\s+");
            Integer key = Integer.parseInt(parts[0]);
            List<Integer> reachableNodes = new ArrayList<>();
            for (int i = 2; i < parts.length; i++) {
                reachableNodes.add(Integer.parseInt(parts[i]));
            }
            map.put(key, reachableNodes);
        }

        return map;
    }
}
