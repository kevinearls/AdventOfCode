package com.kevinearls.adventofcode.coprocessor;

import java.util.HashMap;
import java.util.Map;

public class Coprocessor {
    private Map<String, Long> registers = new HashMap<>();
    private Integer multiplyExecuted = 0;

    public Coprocessor() {
        registers.put("a", 0L);
        registers.put("b", 0L);
        registers.put("c", 0L);
        registers.put("d", 0L);
        registers.put("e", 0L);
        registers.put("f", 0L);
        registers.put("g", 0L);
        registers.put("g", 0L);
    }

    public Integer getMultiplyExecuted() {
        return multiplyExecuted;
    }

    /**
     *
     * @param register  should always be a register index
     * @param value can be a register or a number
     */
    public void set(String register, String value) {
        registers.put(register, getValue(value));
    }

    public void sub(String register, String value) {
        Long startValue = registers.get(register);
        Long toSubtract = getValue(value);
        registers.put(register, startValue - toSubtract);
    }

    public void mul(String register, String value) {
        multiplyExecuted++;
        Long registerValue = registers.get(register);
        Long toMultiply = getValue(value);
        registers.put(register, registerValue * toMultiply);
    }


    public Long getValue(String target) {
        Long value = registers.get(target);
        if (value != null) {
            return value;
        } else {
            return Long.valueOf(target);
        }
    }


    public Map<String, Long> getRegisters() {
        return registers;
    }

    public Long getRegister(String register) {
        return registers.get(register);
    }
}
