package com.kevinearls.adventofcode.tower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tower {
    Map<String, Node> tower = new HashMap<>();

    public String findRootNodeName() {
        for (String nodeName : tower.keySet()) {
            Node node = tower.get(nodeName);
            if (!node.isAChild()) {
                return nodeName;
            }
        }

        return null;
    }

    /**  1. Add the node to the tower
     *  2. for all children, add to tower and/or mark as seen
     */
    public void addToTower(String nodeName, Integer weight, List<String> childNames) {
        Node node = new Node(nodeName, weight, childNames);
        if (tower.containsKey(nodeName)) {  // If it's already been added, then we know it's a child
            node.setAChild(true);
        }
        tower.put(nodeName, node);

        // Mark children as children
        for (String childName : childNames) {
            markAsChild(childName);
        }
    }

    public void markAsChild(String nodeName) {
        Node node = tower.get(nodeName);
        if (node == null) {
            node = new Node(nodeName, 0, new ArrayList<String>());
        }
        node.setAChild(true);
        tower.put(nodeName, node);  //
    }
}
