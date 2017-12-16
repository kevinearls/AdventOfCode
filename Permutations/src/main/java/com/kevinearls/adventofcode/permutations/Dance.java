package com.kevinearls.adventofcode.permutations;

/**
 * --- Day 16: Permutation Promenade ---
 You come upon a very unusual sight; a group of programs here appear to be dancing.

 There are sixteen programs in total, named a through p. They start by standing in a line: a stands in position 0, b stands in position 1, and so on until p, which stands in position 15.

 The programs' dance consists of a sequence of dance moves:

 Spin, written sX, makes X programs move from the end to the front, but maintain their order otherwise. (For example, s3 on abcde produces cdeab).
 Exchange, written xA/B, makes the programs at positions A and B swap places.
 Partner, written pA/B, makes the programs named A and B swap places.
 For example, with only five programs standing in a line (abcde), they could do the following dance:

 s1, a spin of size 1: eabcd.
 x3/4, swapping the last two programs: eabdc.
 pe/b, swapping programs e and b: baedc.
 After finishing their dance, the programs end up in order baedc.

 You watch the dance for a while and record their dance moves (your puzzle input). In what order are the programs standing after their dance?
 */
public class Dance {
    String[] programs;

    public Dance(String programsInput) {
        programs = new String[programsInput.length()];
        for (int i=0; i < programsInput.length(); i++) {
            programs[i] = "" + programsInput.charAt(i);
        }
    }

    public String getCurrentPrograms() {
        return String.join("", programs);
    }

    /**
     * Spin, written sX, makes X programs move from the end to the front, but maintain their order otherwise.
     * (For example, s3 on abcde produces cdeab).
     * @param n
     */
    public void spin(int n) {
        if (n > programs.length) {
            throw new RuntimeException("Got n of " + n);
        }
        String[] newOrder = new String[programs.length];
        int position = n;
        for (int i=0; i < programs.length; i++) {
            newOrder[position] = programs[i];
            position++;
            if (position == programs.length) {
                position = 0;
            }
        }

        programs = newOrder; // COPY?
    }

    /**
     *  Exchange, written xA/B, makes the programs at positions A and B swap places.
     *
     * @param positionA
     * @param positionB
     */
    public void exchange(int positionA, int positionB) {
        String a = programs[positionA];
        String b = programs[positionB];
        programs[positionA] = b;
        programs[positionB] = a;
    }


    /**
     *  Partner, written pA/B, makes the programs named A and B swap places.
     *
     * @param nameA
     * @param nameB
     */
    public void partner(String nameA, String nameB) {
        int positionA=0, positionB=0;
        for (int i=0; i < programs.length; i++) {
            if(programs[i].equals(nameA))  {
                positionA = i;
            } else if (programs[i].equals(nameB)) {
                positionB = i;
            }
        }
        exchange(positionA, positionB);
    }
}
