package com.kevinearls.adventofcode.knot;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     */


    public String computePart2HashRedux(int[] list, String lengthsString) {
        int[] lengthsArray = computeLengths(lengthsString);
        System.out.println("Lengths.size: " + lengthsArray.length);

        List<Integer> lengths = new ArrayList<>();
        for (int i=0; i < lengthsArray.length; i++) {
            lengths.add(lengthsArray[i]);
        }

        List<Integer> sparseHash = IntStream.range(0, 256)
                .boxed()
                .collect(Collectors.toList());
        int current = 0, skip = 0;
        for (int round = 0; round < 64; round++) {
            for (int length : lengths) {
                List<Integer> segment = new ArrayList<>();
                for (int i = current; i < current + length; i++) {
                    segment.add(sparseHash.get(i % sparseHash.size()));
                }
                Collections.reverse(segment);
                for (int i = 0; i < segment.size(); i++) {
                    sparseHash.set((i + current) % sparseHash.size(), segment.get(i));
                }
                current += length + skip++;
            }
        }

        final int size = sparseHash.size();
        List<String> denseHash = IntStream.range(0, (size + 15) >> 4)
                .mapToObj(i -> sparseHash.subList(i << 4, Math.min((i + 1) << 4, size))
                        .stream()
                        .reduce((a, b) -> a ^ b)
                        .orElse(0))
                .map(i -> String.format("%02X", i).toLowerCase())
                .collect(Collectors.toList());

        return String.join("", denseHash);
    }

    public String computePart2Hash(int[] list, String lengthsString) {
        int currentPosition = 0;
        int skipSize = 0;
        final int maxRounds = 64;
        int[] lengths = computeLengths(lengthsString);
        System.out.println("Lengths.size: " + lengths.length);

        for (int round = 0; round < maxRounds; round++) {
            for (int i = 0; i < lengths.length; i++) {
                int blah = lengths[i];
                // 1 REVERSE the order of blah elements, starting at current position
                int toBeReversed[] = new int[blah];
                int j = 0;
                int k = currentPosition;
                while (j < blah) {
                    toBeReversed[j] = list[k];
                    k++;
                    j++;
                    if (k >= list.length) {
                        k = 0;
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
                        k = 0;
                    }
                }

                // Move current position by blah + skipSize;
                currentPosition = (currentPosition + blah + skipSize) % list.length;

                // increase skip size by 1
                skipSize++;
            }
        }

        // Now we should have the sparse hash
        int[] denseHash = computeDenseHash(list);

        String result = convertToHex(denseHash);

        return result;
    }


    public int[] computeDenseHash(int[] sparseHash) {
        int[] denseHash = new int[16];
        if (sparseHash.length != 256) {
            throw new RuntimeException("Wrong sparse hash size " + sparseHash.length + " it must be 256");
        }

        int position = 0;
        for (int i=0; i < denseHash.length; i++) {
            int total = 0;
            for (int j = 0; j < 16; j++) {
                total = total ^ sparseHash[position];
                position++;
            }

            denseHash[i] = total;
        }

        return denseHash;
    }


    public int[] computeLengths(String input) {
        // 17, 31, 73, 47, 23
        int[] lengthsToApend = {17, 31, 73, 47, 23};
        int[] convertedInput = convertToAscii(input);
        int[] result = new int[convertedInput.length + lengthsToApend.length];

        for (int i=0; i < convertedInput.length; i++) {
            result[i] = convertedInput[i];
        }
        for (int i = convertedInput.length; i < (convertedInput.length + lengthsToApend.length); i++) {
            result[i] = lengthsToApend[i - convertedInput.length];
        }

        return result;

    }

    public int[] convertToAscii(String input) {
        char[] blah = input.toCharArray();

        int[] result = new int[blah.length];
        char zero = '0';
        for (int i=0; i < blah.length; i++) {
            result[i] = blah[i];
        }

        return result;
    }

    public String convertToHex(int[] input) {
        String result = "";
        for (int i=0; i < input.length; i++) {
            String thisOne = convertToHex(input[i]);
            result += thisOne;
        }

        return result;
    }

    public String convertToHex(int input) {
        String result = Integer.toHexString(input);
        if (result.length() ==1) {
            result = "0" + result;
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
