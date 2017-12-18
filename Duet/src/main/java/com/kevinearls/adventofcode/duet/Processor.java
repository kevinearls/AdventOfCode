package com.kevinearls.adventofcode.duet;

import java.util.*;

public class Processor {
    private Integer id;
    private Map<String, Long> registers = new HashMap<>();
    private List<Instruction> instructions = new ArrayList<>();
    private Processor neighbor;
    private Queue<Long> messageQueue = new LinkedList<>();
    private Integer messagesSent = 0;
    int position = 0;  // current instruction
    boolean stopped = false;

    public Processor(Integer id, List<String> input) {
        this.id = id;
        for (String line : input) {
            String[] parts = line.split("\\s+");
            Instruction instruction;
            if (parts.length == 3) {
                instruction = new Instruction(parts[0], parts[1], parts[2]);
            } else {
                instruction = new Instruction(parts[0], parts[1]);
            }
            instructions.add(instruction);
        }
        for (char c = 'a'; c <= 'z'; c++) {
            registers.put("" + c, 0L);
        }

        // Each program also has its own program ID (one 0 and the other 1); the register p should begin with this value.
        registers.put("p", Long.valueOf(id));
    }

    public Integer getMessagesSent() {
        return messagesSent;
    }

    public void setNeighbor(Processor neighbor) {
        this.neighbor = neighbor;
    }

    public boolean isAlive() {
        if (!stopped && !isBlocked()) {
            return true;
        } else {
            return false;
        }

    }

    // We're blocked if the queue is empty and the next instruction is rcv
    private Boolean isBlocked() {
        return messageQueue.isEmpty() && instructions.get(position).getCommand().equals("rcv");
    }

    public void sendToQueue(final Long value) {
        messageQueue.add(value);
    }

    /**

     */
    public void processTillBlockedOrDone() {
        while (!stopped && !isBlocked()) {
            Instruction instruction = instructions.get(position);
            String command = instruction.getCommand();
            String p1 = instruction.getP1();
            String p2 = instruction.getP2();

            //System.out.println("Instruction "  + instruction + " ; position " + position);
            switch (command) {
                case "snd":
                    snd(p1);
                    break;
                case "set":
                    set(p1, p2);
                    break;
                case "add":
                    add(p1, p2);
                    break;
                case "mul":
                    mul(p1, p2);
                    break;
                case "mod":
                    mod(p1, p2);
                    break;
                case "rcv":
                    registers.put(p1, messageQueue.remove());
                    break;
                case "jgz":
                    position = jump(p1, p2, position);
                    break;
                default:
                    throw new RuntimeException("Illegal command [" + command + "]");
            }
            if (!command.equals("jgz")) {
                position++;
            }

            // Continuing (or jumping) off either end of the program terminates it.
            if (position < 0 || position >= instructions.size()) {
                stopped = true;
            }
        }
    }

    /**
     *  jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero.
     (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
     * @param p1
     *
     * 4:jgz p p
    7:jgz i -2
    20:jgz i -9
    21:jgz a 3
    23:jgz b -1
    31:jgz p 4
    34:jgz 1 3
    38:jgz i -11
    40:jgz f -16
    41:jgz a -19
     *
     * @param p2
     */
    private Integer jump(String p1, String p2, Integer currentPosition) {
        if (getValue(p1) <= 0) {
            return currentPosition + 1;
        } else {
            return currentPosition + getValue(p2).intValue();
        }
    }

    private void mod(String register, String target) {
        Long targetValue = getValue(target);
        Long currentValue = registers.get(register);
        Long newValue = currentValue % targetValue;

        registers.put(register, newValue);
    }

    private void mul(String register, String target) {
        Long targetValue = getValue(target);

        Long currentValue = registers.get(register);
        Long newValue = currentValue * targetValue;

        registers.put(register, newValue);
    }

    private void add(String register, String target) {
        Long targetValue = getValue(target);
        Long currentValue = registers.get(register);
        Long newValue = currentValue + targetValue;

        registers.put(register, newValue);
    }


    public void set(String register, String target) {
        registers.put(register, getValue(target));
    }

    private Long getValue(String target) {
        if (target.length() == 1 && Character.isLetter(target.charAt(0))) {
            return registers.get(target);
        } else {
            return Long.parseLong(target);
        }
    }

    public void snd(String register) {
        messagesSent++;
        neighbor.sendToQueue(getValue(register));
    }
}
