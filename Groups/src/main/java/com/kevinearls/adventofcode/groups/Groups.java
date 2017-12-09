package com.kevinearls.adventofcode.groups;

public class Groups {

    public int groupScore(String line) {
        // if ! ignore next character
        // if < inGarbage until we hit a >
        // if { start of group
        // if } end of group
        int groupScore = 0;
        int groupDepth = 0;
        boolean ignore = false;
        boolean inGarbage = false;

        for (char c : line.toCharArray()) {
            if (ignore) { // isgnore this character
                ignore = false;
            } else if (c == '!') {
                ignore = true;
            } else if (inGarbage) {
                if (c == '>') {
                    inGarbage = false;
                }
            } else if (c == '<') {
                inGarbage = true;
            } else if (c == '{') {
                groupDepth++;
            } else if (c == '}') {
                groupScore += groupDepth;
                groupDepth--;
            }

        }
        return groupScore;
    }
}
