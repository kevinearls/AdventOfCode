package com.kevinearls.adventofcode.tower;

import java.util.HashMap;
import java.util.Map;

public class Tower {
    Map<String, Boolean> tower = new HashMap<>();

    public String findRootNodeName() {
        for (String nodeName : tower.keySet()) {
            Boolean isAChild = tower.get(nodeName);
            if (!isAChild) {
                return nodeName;
            }
        }

        return null;
    }

    public void addToTower(String nodeName) {
        if (!tower.containsKey(nodeName)) {
            tower.put(nodeName, Boolean.FALSE);
        }
    }

    public void markAsChild(String nodeName) {
        tower.put(nodeName, Boolean.TRUE);
    }


}
