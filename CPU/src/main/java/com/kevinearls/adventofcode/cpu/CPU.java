package com.kevinearls.adventofcode.cpu;

import java.util.HashMap;
import java.util.Map;

public class CPU {
    private Map<String, Integer> registers = new HashMap<>();

    public Integer getRegisterValue(String registerName) {
        return registers.get(registerName);
    }

    public void setRegister(String registerName, Integer value) {
        registers.put(registerName, value);
    }

    public Integer findGreatestRegisterValue() {
        Integer greatest = Integer.MIN_VALUE;
        for (String name : registers.keySet()) {
            Integer value = registers.get(name);
            if (value > greatest) {
                greatest = value;
            }
        }
        return greatest;
    }
}
