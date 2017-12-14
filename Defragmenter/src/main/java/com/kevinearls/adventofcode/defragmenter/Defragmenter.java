package com.kevinearls.adventofcode.defragmenter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Defragmenter {
    private static final Integer GRID_SIZE = 128;
    private Map<String, String> hexMap = new HashMap<>();
    private Map<String, Integer> hexCounts = new HashMap<>();
    private int[][] grid  = new int[GRID_SIZE][GRID_SIZE];;

    public Defragmenter() {
        hexMap.put("0", "0000");
        hexMap.put("1", "0001");
        hexMap.put("2", "0010");
        hexMap.put("3", "0011");
        hexMap.put("4", "0100");
        hexMap.put("5", "0101");
        hexMap.put("6", "0110");
        hexMap.put("7", "0111");
        hexMap.put("8", "1000");
        hexMap.put("9", "1001");
        hexMap.put("a", "1010");
        hexMap.put("b", "1011");
        hexMap.put("c", "1100");
        hexMap.put("d", "1101");
        hexMap.put("e", "1110");
        hexMap.put("f", "1111");

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

    public Integer calculateRegions(String lengths) {
        Integer regionCount = 0;

        // Build the grid
        for (int i = 0; i < GRID_SIZE; i++) {
            String knotHash = computeDenseKnotHash(lengths + "-" + i);
            int[] row = computeRowFromHash(knotHash);
            if (row.length != GRID_SIZE) {
                throw new RuntimeException("Bad row length " + row.length);
            }
            for (int j=0; j < row.length; j++) {
                grid[i][j] = row[j];
            }
        }

        int regionId = 0;
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++ ) {
                if (grid[r][c] == -1) {
                    regionId++;
                    grid[r][c] = regionId;
                    markNeighbors(r, c, regionId);
                }
            }
        }

        return regionId;
    }

    /**
     *
     * Now, all the defragmenter needs to know is the number of regions. A region is a group of used squares that are
     * all adjacent, not including diagonals. Every used square is in exactly one region: lone used squares form their
     * own isolated regions, while several adjacent squares all count as a single region.
     *
     * Look at r-1,c; r+1, c; r, c-1; r, c+1
     *
     * @param startRow
     * @param startColumn
     * @param regionId
     */
    public void markNeighbors(final int startRow, final int startColumn, int regionId) {
        Queue<Point> points = new LinkedList<>();
        Point start = new Point(startRow, startColumn);
        points.add(start);

        while (!points.isEmpty()) {
            Point current = points.remove();
            int r = current.r;
            int c = current.c;
            if (r > 0  && (grid[r - 1][c] == -1)) {
                grid[r - 1][c] = regionId;
                points.add(new Point(r - 1, c));
            }
            if (r < 127 && (grid[r + 1][c] == -1))  {
                grid[r + 1][c] = regionId;
                points.add(new Point(r + 1, c));
            }
            if (c > 0 && (grid[r][c - 1] == -1)) {
                grid[r][c - 1] = regionId;
                points.add(new Point(r, c - 1));
            }
            if (c < 127 && (grid[r][c + 1] == -1)) {
                grid[r][c + 1] = regionId;
                points.add(new Point(r , c + 1));
            }
        }
    }



    public int[] computeRowFromHash(String hash) {
        int[] row = new int[128];
        int position = 0;
        for (char c : hash.toCharArray()) {
            String bits = hexMap.get(c + "");
            for (int i = 0; i < bits.length(); i++) {
                if (bits.charAt(i) == '0' ) {
                    row[position] = 0;
                } else {
                    row[position] = -1;
                }
                position++;
            }
        }

        return row;
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

    class Point {
        int r;
        int c;

        public Point(int r, int c) {
            this.r=r;
            this.c=c;
        }
    }
}
