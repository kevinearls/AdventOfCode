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

    public List<Node> getChildrenOf(String nodeName) {
        List<Node> children = new ArrayList<>();
        Node parent = tower.get(nodeName);
        for (String childName : parent.getChildren()) {
            Node child = tower.get(childName);
            children.add(child);
        }

        return children;
    }

    public Node getNodeByName(String nodeName) {
        Node target = tower.get(nodeName);
        return target;
    }

    public Integer getTowerWeight(String startNodeName) {
        Integer weight = 0;
        Node startNode = tower.get(startNodeName);
        if (startNode.getChildren().size() == 0) {
            return startNode.getWeight();
        }

        for (String childName : startNode.getChildren()) {
            Integer towerWeight = getTowerWeight(childName);
            //System.out.println("Weight of " + childName + " is " + towerWeight);
            weight += towerWeight;
        }
        weight += startNode.getWeight();

        return weight;
    }

    public int balance(Node startNode, Integer correction) {
        //System.out.println("Balance called with " + startNode.getName() + " correction " + correction);
        List<Node> children = getChildrenOf(startNode.getName());
        boolean balanced = false;
        Map<Integer, Integer> occurences = new HashMap<>();
        Map<String, Integer> towerWeights = new HashMap<>();

        // First, see if towers are unbalanced
        for (Node child : children) {
            Integer towerWeight = getTowerWeight(child.getName());
            if (occurences.containsKey(towerWeight)) {
                occurences.put(towerWeight, occurences.get(towerWeight) + 1);
            } else {
                occurences.put(towerWeight, new Integer(1));
            }
            towerWeights.put(child.getName(), towerWeight); // TODO use Node as key instead?
        }

        Integer unbalancedValue=0;
        Integer toCorrectBy = 0;
        if (occurences.size() == 1) {  // Children are balanced, correct this node and return, we're done
            System.out.println("Fix node " + startNode.getName() + " by " + correction +" from current weight " + startNode.getWeight());
            return startNode.getWeight() + correction;
        } else {
            // Figure out which node we need to balance, and by how much
            Integer correctValue=0;

            for (Integer weight : occurences.keySet()) {
                Integer count = occurences.get(weight);
                if (count == 1) {
                    unbalancedValue = weight;
                } else {
                    correctValue = weight;
                }
            }
            toCorrectBy = correctValue - unbalancedValue;
            for (String nodeName : towerWeights.keySet()) {
                Integer weight = towerWeights.get(nodeName);
                if (weight == unbalancedValue) {
                    return balance(getNodeByName(nodeName), toCorrectBy);
                }
            }
        }
        return 0;
    }
}
