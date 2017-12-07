package com.kevinearls.adventofcode.tower;

public class Node {
    private String name;
    private boolean referenced;

    public Node(String name) {
        this.name = name;
        this.referenced = false;
    }

    public Node(String name, boolean referenced) {
        this.name=name;
        this.referenced=referenced;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (referenced != node.referenced) return false;
        return name != null ? name.equals(node.name) : node.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (referenced ? 1 : 0);
        return result;
    }


    public boolean isReferenced() {
        return referenced;
    }

    public void setReferenced(boolean referenced) {
        this.referenced = referenced;
    }

}
