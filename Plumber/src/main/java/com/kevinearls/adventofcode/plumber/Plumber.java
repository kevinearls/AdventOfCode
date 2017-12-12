package com.kevinearls.adventofcode.plumber;

import java.util.*;

public class Plumber {
    public Integer groupSize(List<String> input) {
        Map<Integer, List<Integer>> network = createInputMap(input);
        Set<Integer> reachable = computeReachableFrom(0, network);
        return reachable.size();
    }

    public Integer numberOfGroups(List<String> input) {
        Map<Integer, List<Integer>> network = createInputMap(input);
        Integer numberOfGroups = 0;
        boolean done = false;
        while (!done) {
            Integer root = network.keySet().iterator().next();
            Set<Integer> reachable = computeReachableFrom(root, network);
            numberOfGroups++;
            for (Integer key : reachable) {
                network.remove(key);
            }
            if (network.keySet().size() == 0) {
                done = true;
            }
        }

        return numberOfGroups;
    }

    public Set<Integer> computeReachableFrom(Integer root, Map<Integer, List<Integer>> network) {
        Set<Integer> reachable = new HashSet<>();
        reachable.add(root);
        Queue<Integer> blah = new LinkedList<>();
        boolean done = false;
        Integer current = root;

        while (!done) {
            List<Integer> neighbors = network.get(current);
            for (Integer neigbor : neighbors) {
                if (!reachable.contains(neigbor)) {
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

        return reachable;
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
