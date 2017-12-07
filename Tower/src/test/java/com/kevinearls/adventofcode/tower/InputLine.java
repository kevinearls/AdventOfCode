package com.kevinearls.adventofcode.tower;

import java.util.ArrayList;
import java.util.List;

public class InputLine {
    private String name;
    private Integer weight;
    List<String> childNames = new ArrayList<>();

    public InputLine(String line) {
        line = line.trim();
        String[] parts = line.split("\\s+");

        this.name = parts[0];
        String weight = parts[1];
        weight = weight.substring(1);
        weight = weight.substring(0, weight.length() - 1);
        this.weight = Integer.parseInt(weight);
        // weight is part 1, but we need to chop of parens
        // if there are more than 2 parts, part 2 is an arrow, and children come after that
        if (parts.length > 2) {
            for (int i = 3; i < parts.length; i++) {
                String childName = parts[i];
                if (childName.endsWith(",")) {
                    childName = childName.substring(0, childName.length() -1);  // chop off comma
                }
                childNames.add(childName);
            }
        }
    }

    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }

    public List<String> getChildNames() {
        return childNames;
    }
}
