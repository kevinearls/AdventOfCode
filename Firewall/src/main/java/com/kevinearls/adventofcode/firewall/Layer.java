package com.kevinearls.adventofcode.firewall;

public class Layer {
    Integer id; // do we need this?
    Integer size;
    Integer currentPosition = 0;
    Integer increment = 1;
    // DO I actually need an array?

    public Layer(Integer id, Integer size) {
        this.id = id;
        this.size = size;
    }

    public void move() {
        currentPosition += increment;
        if (currentPosition == size) {
            currentPosition = size -2;
            increment = -1;
        } else if (currentPosition == -1) {
            currentPosition = 1;
            increment = 1;
        }
    }

    public Integer getCurrentPosition() {
        return currentPosition;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSize() {
        return size;
    }
}
