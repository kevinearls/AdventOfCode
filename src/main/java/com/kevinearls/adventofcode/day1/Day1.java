package com.kevinearls.adventofcode.day1;
/**
 * The captcha requires you to review a sequence of digits (your puzzle input) and find the sum of all digits that match
 * the next digit in the list. The list is circular, so the digit after the last digit is the first digit in the list.

 For example:

 1122 produces a sum of 3 (1 + 2) because the first digit (1) matches the second digit and the third digit (2) matches the fourth digit.
 1111 produces 4 because each digit (all 1) matches the next.
 1234 produces 0 because no digit matches the next.
 91212129 produces 9 because the only digit that matches the next one is the last digit, 9.
 What is the solution to your captcha?

 */
public class Day1 {
    public int sum(String input) {
        // TODO check input?

        int[] contents = new int[input.length()];
        for (int i=0; i < input.length(); i++) {
            //if input.charAt(i)
            contents[i] = input.charAt(i) - '0';
        }

        int total = 0;
        for (int i=0; i < (contents.length -1); i++) {
            if (contents[i] == contents[i + 1]) {
                total += contents[i];
            }
        }
        // Special case, compare last to first
        if (contents[0] == contents[contents.length-1]) {
            total += contents[0];
        }
        return total;
    }

    public static void main(String[] args) {
        Day1 day1 = new Day1();
        System.out.println("1122: " + day1.sum("1122"));
    }

}
