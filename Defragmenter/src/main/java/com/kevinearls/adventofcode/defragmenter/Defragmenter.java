package com.kevinearls.adventofcode.defragmenter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Defragmenter {
    private Map<String, Integer> hexCounts = new HashMap<>();

    public Defragmenter() {
        hexCounts.put("0", 0);
        hexCounts.put("1", 1);
        hexCounts.put("2", 1);
        hexCounts.put("3", 2);
        hexCounts.put("4", 1);
        hexCounts.put("5", 2);
        hexCounts.put("6", 2);
        hexCounts.put("7", 3);
        hexCounts.put("8", 1);
        hexCounts.put("9", 2);
        hexCounts.put("a", 2);
        hexCounts.put("b", 3);
        hexCounts.put("c", 2);
        hexCounts.put("d", 3);
        hexCounts.put("e", 3);
        hexCounts.put("f", 4);
    }
    
    public Integer calculateUsedSquares(String lengths) {
        Integer squaresUsed = 0;
        for (int i = 0; i < 128; i++) {
            String knotHash = computeDenseKnotHash(lengths + "-" + i);
            Integer squares = convertAndCountBits(knotHash);
            squaresUsed += squares;
        }
        return squaresUsed;
    }


    /**
     * @param input a hexadecimal string, presumably a hash
     * @return
     */
    public Integer convertAndCountBits(String input) {
        Integer totalCount = 0;
        for (char c : input.toCharArray()) {
            Integer count = hexCounts.get(c + "");
            totalCount += count;
        }

        return totalCount;
    }


    public String computeDenseKnotHash(String lengthsString) {
        List<Integer> lengths = computeLengths(lengthsString);
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

        StringBuffer returnValue = new StringBuffer();
        for (String d : denseHash) {
            returnValue.append(d);
        }
        return returnValue.toString();
    }

    public List<Integer> computeLengths(String input) {
        int[] lengthsToApend = {17, 31, 73, 47, 23};
        int[] convertedInput = convertToAscii(input);
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < convertedInput.length; i++) {
            result.add(convertedInput[i]);
        }
        for (int i = convertedInput.length; i < (convertedInput.length + lengthsToApend.length); i++) {
            result.add(lengthsToApend[i - convertedInput.length]);
        }

        return result;
    }

    public int[] convertToAscii(String input) {
        char[] blah = input.toCharArray();
        int[] result = new int[blah.length];
        for (int i=0; i < blah.length; i++) {
            result[i] = blah[i];
        }

        return result;
    }
}
