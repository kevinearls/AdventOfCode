package com.kevinearls.adventofcode.cpu;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * Each instruction consists of several parts: the registerName to modify, whether to increase or decrease that registerName's value,
 * the amount by which to increase or decrease it, and a condition. If the condition fails, skip the instruction without modifying
 * the registerName. The registers all start at 0. The instructions look like this:

 b inc 5 if a > 1
 a inc 1 if b < 5
 c dec -10 if a >= 1
 c inc -20 if c == 10
 These instructions would be processed as follows:

 Because a starts at 0, it is not greater than 1, and so b is not modified.
 a is increased by 1 (to 1) because b is less than 5 (it is 0).
 c is decreased by -10 (to 10) because a is now greater than or equal to 1 (it is 1).
 c is increased by -20 (to -10) because c is equal to 10.
 After this process, the largest value in any registerName is 1.

 You might also encounter <= (less than or equal to) or != (not equal to). However, the CPU doesn't have the bandwidth
 to tell you what all the registers are named, and leaves that to you to determine.

 What is the largest value in any registerName after completing the instructions in your puzzle input?
 */
public class Instruction {
    private String registerName;
    private Integer amount;
    // Conditionall stuff
    private String targetRegisterName;
    private String comparator;
    private Integer comparatorValue;
    List<String> expectedComparators = new ArrayList<>();

    public Instruction(String line) {
        expectedComparators.add(">");
        expectedComparators.add("<");
        expectedComparators.add(">=");
        expectedComparators.add("<=");
        expectedComparators.add("!=");
        expectedComparators.add("==");

        String[] parts = line.split("\\s+");
        if (parts.length != 7) {
            throw new RuntimeException("Got " + parts.length + " parts for [" + line + "]");
        }
        if (!parts[1].equalsIgnoreCase("inc") && !parts[1].equalsIgnoreCase("dec")) {
            throw new RuntimeException("Bad operation [" + parts[1]);
        }

        /*for (int i=0; i < parts.length; i++) {
            System.out.println(parts[i]);
        */
        registerName = parts[0];
        amount = Integer.parseInt(parts[2]);
        if (parts[1].equalsIgnoreCase("dec")) {
            amount = -amount;
        }
        targetRegisterName = parts[4];
        comparator = parts[5];
        if (!expectedComparators.contains(comparator)) {
            throw new RuntimeException("Bad comparator [" + comparator + "]");
        }
        comparatorValue = Integer.parseInt(parts[6]);
    }

    public void evaluateAndApply(CPU cpu) {
        // evaluate condition
        Integer targetRegisterValue = cpu.getRegisterValue(getTargetRegisterName());
        //Integer comparatorValue = instruction.getComparatorValue();
        boolean apply = false;
        //System.out.println(">>>>>> IF " + targetRegisterValue + " " + instruction.getComparator() + " " + comparatorValue);
        switch (getComparator()) {
            case ">":
                apply = targetRegisterValue > comparatorValue;
                break;
            case "<":
                apply = targetRegisterValue < comparatorValue;
                break;
            case "==":
                apply = targetRegisterValue.equals(comparatorValue);
                break;
            case ">=":
                apply = targetRegisterValue >= comparatorValue;
                break;
            case "<=":
                apply = targetRegisterValue <= comparatorValue;
                break;
            case "!=":
                apply = !targetRegisterValue.equals(comparatorValue);
                break;
            default:
                throw new RuntimeException("Bad operation [" + getComparator() + "]");
        }
        if (apply) {
            Integer registerValue = cpu.getRegisterValue(getRegisterName());
            Integer updatedValue = registerValue + getAmount();
            cpu.setRegister(getRegisterName(), updatedValue);
        }
    }

    public String getRegisterName() {
        return registerName;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getTargetRegisterName() {
        return targetRegisterName;
    }

    public String getComparator() {
        return comparator;
    }

    public Integer getComparatorValue() {
        return comparatorValue;
    }
}
