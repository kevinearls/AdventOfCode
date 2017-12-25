package com.kevinearls.adventofcode.turing;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class TuringTest {
    @Test
    public void testWithRealData() throws Exception {
        String state="A";
        Integer cursor=0;
        LinkedList<Integer> tape = new LinkedList<>();
        String moveTo = "";
        tape.add(0);

        for (int i=0; i < 12302209; i++) {
            Integer currentValue = tape.get(cursor);
            switch (state) {
                case "A":
                    if (currentValue == 0) {
                        tape.set(cursor, 1);
                        moveTo = "right";
                        state = "B";
                    } else {
                        tape.set(cursor, 0);
                        moveTo = "left";
                        state = "D";
                    }
                    break;
                case "B":
                    if (currentValue == 0) {
                        tape.set(cursor, 1);
                        moveTo = "right";
                        state = "C";
                    } else {
                        tape.set(cursor, 0);
                        moveTo = "right";
                        state = "F";
                    }
                    break;
                case "C":
                    if (currentValue == 0) {
                        tape.set(cursor, 1);
                        moveTo = "left";
                        state = "C";
                    } else {
                        tape.set(cursor, 1);
                        moveTo = "left";
                        state = "A";
                    }
                    break;
                case "D":
                    if (currentValue == 0) {
                        tape.set(cursor, 0);
                        moveTo = "left";
                        state = "E";
                    } else {
                        tape.set(cursor, 1);
                        moveTo = "right";
                        state = "A";
                    }
                    break;
                case "E":
                    if (currentValue == 0) {
                        tape.set(cursor, 1);
                        moveTo = "left";
                        state = "A";
                    } else {
                        tape.set(cursor, 0);
                        moveTo = "right";
                        state = "B";
                    }
                    break;
                case "F":
                    if (currentValue == 0) {
                        tape.set(cursor, 0);
                        moveTo = "right";
                        state = "C";
                    } else {
                        tape.set(cursor, 0);
                        moveTo = "right";
                        state = "E";
                    }
                    break;
                default:
                    throw new Exception("Unknown state " + state);
            }

            // Now move....
            if (moveTo.equals("right")) {
                cursor++;
                if (cursor == tape.size()) {
                    tape.add(0);
                }
            } else { // moving left
                if (cursor == 0) {
                    // leave cursor at 0, but insert a new value
                    tape.add(0, 0);
                } else {
                    cursor--;
                }
            }
        }

        int checksum = getChecksum(tape);

        System.out.println("Checksum " + checksum);
        assertEquals(633, checksum);
    }

    @Test
    public void testWithExampleData() throws Exception {
        String state="A";
        Integer cursor=0;
        LinkedList<Integer> tape = new LinkedList<>();
        String moveTo = "";
        tape.add(0);

        for (int i=0; i < 6; i++) {
            Integer currentValue = tape.get(cursor);
            switch (state) {
                case "A":
                    if (currentValue == 0) {
                        tape.set(cursor, 1);
                        moveTo = "right";
                    } else {
                        tape.set(cursor, 0);
                        moveTo = "left";
                    }
                    state = "B";
                    break;
                case "B":
                    if (currentValue == 0) {
                        tape.set(cursor, 1);
                        moveTo = "left";
                    } else {
                        moveTo = "right";
                    }
                    state = "A";
                    break;
                default:
                    throw new Exception("Unknown state " + state);
            }

            // Now move....
            if (moveTo.equals("right")) {
                cursor++;
                if (cursor == tape.size()) {
                    tape.add(0);
                }
            } else { // moving left
                if (cursor == 0) {
                    // leave cursor at 0, but insert a new value
                    tape.add(0, 0);
                } else {
                    cursor--;
                }
            }

            System.out.println("------------------------------------------------------");
            System.out.println("After iteration " + i + " cursor " + cursor + " tape " + tape);
        }

        int checksum = getChecksum(tape);

        System.out.println("Checksum " + checksum);
        assertEquals(3, checksum);
    }

    private int getChecksum(LinkedList<Integer> tape) {
        int checksum = 0;
        for (Integer i : tape) {
            if (i == 1) {
                checksum++;
            }
        }
        return checksum;
    }
}
