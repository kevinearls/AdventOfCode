package com.kevinearls.adventofcode.tower;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String name;
    private Integer weight;
    private List<String> children;
    private boolean isAChild;

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }

    public List<String> getChildren() {
        return children;
    }

    public Node(String name, Integer weight, List<String> children) {
        this.name = name;
        this.weight = weight;
        this.children = new ArrayList<>(children);
        this.isAChild = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;
        if (isAChild != node.isAChild) return false;
        return name != null ? name.equals(node.name) : node.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (isAChild ? 1 : 0);
        return result;
    }


    public boolean isAChild() {
        return isAChild;
    }

    public void setAChild(boolean AChild) {
        this.isAChild = AChild;
    }

}
