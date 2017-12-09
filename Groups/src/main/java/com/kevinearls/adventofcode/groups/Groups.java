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


    /**
     * Now, you're ready to remove the garbage.

     To prove you've removed it, you need to count all of the characters within the garbage. The leading and trailing
     < and > don't count, nor do any canceled characters or the ! doing the canceling.

     <>, 0 characters.
     <random characters>, 17 characters.
     <<<<>, 3 characters.
     <{!>}>, 2 characters.
     <!!>, 0 characters.
     <!!!>>, 0 characters.
     <{o"i!a,<{i<a>, 10 characters.
     How many non-canceled characters are within the garbage in your puzzle input?
     * @param line
     * @return
     */
    public int countGarbageCharacters(String line) {
        int garbageCount = 0;
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
                } else  {
                    garbageCount++;
                }
            } else if (c == '<') {
                inGarbage = true;
            }
        }

        return garbageCount;
    }
}
