package com.kevinearls.adventofcode.duet;

public class Instruction {
    private String command;
    private String p1;
    private String p2;

    public Instruction(String name, String p1, String p2) {
        this.command = name;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Instruction(String name, String p1) {
        this.command = name;
        this.p1 = p1;
        this.p2 = null;
    }

    public String getCommand() {
        return command;
    }

    public String getP1() {
        return p1;
    }

    public String getP2() {
        return p2;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "command='" + command + '\'' +
                ", p1='" + p1 + '\'' +
                ", p2='" + p2 + '\'' +
                '}';
    }
}
