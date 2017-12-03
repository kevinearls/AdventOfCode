package com.kevinearls.adventofcode.checksum;


/**
 --- Part Two ---

 "Great work; looks like we're on the right track after all. Here's a star for your effort." However, the program seems
 a little worried. Can programs be worried?

 "Based on what we're seeing, it looks like all the User wanted is some information about the evenly divisible values in
 the spreadsheet. Unfortunately, none of us are equipped for that kind of calculation - most of us specialize in bitwise operations."

 It sounds like the goal is to find the only two numbers in each row where one evenly divides the other - that is, where
 the result of the division operation is a whole number. They would like you to find those numbers on each line, divide
 them, and add up each line's result.

 For example, given the following spreadsheet:

 5 9 2 8
 9 4 7 3
 3 8 6 5
 In the first row, the only two numbers that evenly divide are 8 and 2; the result of this division is 4.
 In the second row, the two numbers are 9 and 3; the result is 3.
 In the third row, the result is 2.
 In this example, the sum of the results would be 4 + 3 + 2 = 9.

 What is the sum of each row's result in your puzzle input?


 */
public class CheckSum {
    public Integer checksum(Integer[][] spreadsheet) {
        Integer total = 0;

        /**
         *  int[] data = {5, 9, 2, 8};
         for (int i = 0; i < data.length; i++) {
             int target = data[i];
             for (int j=(i+1); j < data.length; j++) {
             System.out.println("Comparing " + target + " to " + data[j]);
         }
         }
         */

        for (int row = 0 ; row < spreadsheet.length; row++) {
            Integer[] data = spreadsheet[row];
            for (int i = 0; i < data.length; i++) {
                int target = data[i];
                for (int j = (i + 1); j < data.length; j++) {
                    Integer max = Math.max(target, data[j]);
                    Integer min = Math.min(target, data[j]);
                    System.out.println("Comparing " + target + " to " + data[j] + " >>> Max " + max + " min " + min);
                    if (max % min == 0) {
                        System.out.println("\tFound match");
                        total += (max / min);
                    }
                }
            }
            /*int rowSmallest = Integer.MAX_VALUE;
            int rowLargest = Integer.MIN_VALUE;
            for (int column = 0; column < spreadsheet[row].length; column++) {
                int value = spreadsheet[row][column];
                if (value < rowSmallest) {
                    rowSmallest = value;
                }
                if (value > rowLargest) {
                    rowLargest = value;
                }

            }
            int rowDifference = rowLargest - rowSmallest;
            total += rowDifference;*/
        }
        return total;
    }
}
