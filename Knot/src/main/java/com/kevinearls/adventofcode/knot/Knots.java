package com.kevinearls.adventofcode.knot;


/**
 * To achieve this, begin with a list of numbers from 0 to 255, a current position which begins at 0 (the first element
 * in the list), a skip size (which starts at 0), and a sequence of lengths (your puzzle input). Then, for each length:

 Reverse the order of that length of elements in the list, starting with the element at the current position.
 Move the current position forward by that length plus the skip size.
 Increase the skip size by one.

 */

public class Knots {

    /**
     * The list begins as [0] 1 2 3 4 (where square brackets indicate the current position).
     The first length, 3, selects ([0] 1 2) 3 4 (where parentheses indicate the sublist to be reversed).
     After reversing that section (0 1 2 into 2 1 0), we get ([2] 1 0) 3 4.
     Then, the current position moves forward by the length, 3, plus the skip size, 0: 2 1 0 [3] 4. Finally, the skip size increases to 1.

     The second length, 4, selects a section which wraps: 2 1) 0 ([3] 4.
     The sublist 3 4 2 1 is reversed to form 1 2 4 3: 4 3) 0 ([1] 2.
     The current position moves forward by the length plus the skip size, a total of 5, causing it not to move because it wraps around: 4 3 0 [1] 2. The skip size increases to 2.

     The third length, 1, selects a sublist of a single element, and so reversing it has no effect.
     The current position moves forward by the length (1) plus the skip size (2): 4 [3] 0 1 2. The skip size increases to 3.
     The fourth length, 5, selects every element starting with the second: 4) ([3] 0 1 2. Reversing this sublist (3 0 1 2 4 into 4 2 1 0 3) produces: 3) ([4] 2 1 0.
     Finally, the current position moves forward by 8: 3 4 2 1 [0]. The skip size increases to 4.

     * @param list
     * @param inputLengths
     * @return
     */
    public int computeHash(int[] list, int[] inputLengths) {
        int currentPosition = 0;
        int skipSize = 0;

        for (int i=0; i < inputLengths.length; i++) {
            int blah = inputLengths[i];
            // 1 REVERSE the order of blah elements, starting at current position
            int toBeReversed[] = new int[blah];
            int j = 0;
            int k = currentPosition;
            while (j < blah) {
                toBeReversed[j] = list[k];
                k++;
                j++;
                if (k >= list.length) {
                    k=0;
                }
            }

            // Put the reversed elements back in the original list
            int[] reversed = reverse(toBeReversed);
            j = 0;
            k = currentPosition;
            while (j < blah) {
                list[k] = reversed[j];
                j++;
                k++;
                if (k >= list.length) {
                    k=0;
                }
            }

            // Move current position by blah + skipSize;
            currentPosition = (currentPosition + blah + skipSize) % list.length;

            // increase skip size by 1
            skipSize++;
        }

        // result = product of first 2 entries
        int result = list[0] * list[1];
        return result;
    }

    public int[] convertToAscii(int[] input) {
        int[] result = new int[input.length];
        char zero = '0';
        for (int i=0; i < input.length; i++) {
            result[i] = input[i] + (int) zero;
        }

        return result;
    }


    public int[] reverse(int[] input) {
        int[] reversed = new int[input.length];
        int position = reversed.length-1;
        for (int i=0; i < input.length; i++) {
            reversed[position] = input[i];
            position--;
        }

        return reversed;
    }

    public void printArray(int[] input) {
        for (int i=0; i < input.length; i++) {
            System.out.print(input[i] + " ");
        }
        System.out.println("");
    }
}
