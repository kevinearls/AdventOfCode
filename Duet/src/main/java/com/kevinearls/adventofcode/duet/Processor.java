package com.kevinearls.adventofcode.duet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor {
    private Map<String, Long> registers = new HashMap<>();
    private List<Instruction> instructions = new ArrayList<>();

    public Processor(List<String> input) {
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
        //System.out.println("Got " + instructions.size() + " instructions");

        for (char c = 'a'; c <= 'z'; c++) {
            registers.put("" + c, 0L);
        }

    }

    /**
     * Is this when we're done?
     *
     * What is the value of the recovered frequency (the value of the most recently played sound) the first time a
     * rcv instruction is executed with a non-zero value?
     *
     * snd X plays a sound with a frequency equal to the value of X.
     set X Y sets register X to the value of Y.
     add X Y increases register X by the value of Y.
     mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
     mod X Y sets register X to the remainder of dividing the value contained in register X by the value of Y (that is,
         it sets X to the result of X modulo Y).
     rcv X recovers the frequency of the last sound played, but only when the value of X is not zero. (If it is zero, the command does nothing.)
     jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero.
     (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
     *
     *
     * Continuing (or jumping) off either end of the program terminates it.


     */
    public Long process() {
        int position = 0;
        boolean stopping = false;
        Long lastSoundPlayed = -1L;


        while (!stopping) {
            Instruction instruction = instructions.get(position);
            String command = instruction.getCommand();
            String p1 = instruction.getP1();
            String p2 = instruction.getP2();

            //System.out.println("Instruction "  + instruction + " ; position " + position);
            switch(command) {
                case "snd" :
                    lastSoundPlayed = snd(p1);
                    break;
                case "set" :
                    set(p1, p2);
                    break;
                case "add" :
                    add(p1, p2);
                    break;
                case "mul" :
                    mul(p1, p2);
                    break;
                case "mod" :
                    mod(p1, p2);
                    break;
                case "rcv" :
                    Long value = registers.get(p1);
                    if (value != 0) {
                        System.out.println(">>>>>>>>>>>> Before return registers has " + registers.size() + " entries");
                        return lastSoundPlayed;
                    }
                    break;
                case "jgz" :
                    position = jump(p1, p2, position);
                    break;
                default:
                    throw new RuntimeException("Illegal command [" + command + "]");
            }
            if (!command.equals("jgz")) {
                position++;
            }

            // Continuing (or jumping) off either end of the program terminates it.
            if (position < 0 ||  position >= instructions.size()) {
                stopping = true;
            }
        }


        return lastSoundPlayed;
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

    public Long snd(String register) {
        System.out.println(">>>>> Setting lastSound to register " + register + " value " + registers.get(register));
        Long lastSound = registers.get(register);  // TODO what if not found?
        return  lastSound;
    }


    private boolean isRegister(final String input) {
        if (Character.isLetter(input.charAt(0))) {
            return true;
        } else {
            return false;
        }
    }

}
