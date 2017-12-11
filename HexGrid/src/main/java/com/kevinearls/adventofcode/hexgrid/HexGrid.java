package com.kevinearls.adventofcode.hexgrid;


import java.util.logging.Logger;

/**
 * Crossing the bridge, you've barely reached the other side of the stream when a program comes up to you, clearly in
 * distress. "It's my child process," she says, "he's gotten lost in an infinite grid!"

 Fortunately for her, you have plenty of experience with infinite grids.

 Unfortunately for you, it's a hex grid.

 The hexagons ("hexes") in this grid are aligned such that adjacent hexes can be found to the north, northeast,
 southeast, south, southwest, and northwest:

 \ n  /
 nw +--+ ne
 /    \
 -+      +-
 \    /
 sw +--+ se
 / s  \
 You have the path the child process took. Starting where he started, you need to determine the fewest number of steps
 required to reach him. (A "step" means to move from the hex you are in to any adjacent hex.)

 For example:

 ne,ne,ne is 3 steps away.
 ne,ne,sw,sw is 0 steps away (back where you started).
 ne,ne,s,s is 2 steps away (se,se).
 se,sw,se,sw,sw is 3 steps away (s,s,sw).

 --- Part Two ---

 How many steps away is the furthest he ever got from his starting position?

 */
public class HexGrid {
    Integer maxDistance = 0;
    private static Logger logger = Logger.getLogger(HexGrid.class.getCanonicalName());

    public Integer followPath(String instructions) {
        HexPoint point = new HexPoint(0,0,0);

        String[] steps = instructions.split(",");
        logger.fine("Got " + steps.length + " steps");
        for (String step : steps)  {
            logger.fine("\t" + step);
            move(point, step);
            int distance = computeDistance(point);
            logger.fine("DISTANCE is " + distance) ;
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }

        Integer result = computeDistance(point);
        logger.info("Distance " + result);
        return result;
    }

    public Integer getMaxDistance() {
         return maxDistance;
    }

    /**
     *
     * @param point
     * @param step
     */
    public void move(HexPoint point, String step) {
        switch (step) {
            case "n":
                point.y++; point.z--; return;
            case "ne":
                point.x++; point.z--; return;
            case "se":
                point.x++; point.y--; return;
            case "s":
                point.y--; point.z++; return;
            case "sw":
                point.x--; point.z++; return;
            case "nw":
                point.x--; point.y++; return;
            default:
                throw new RuntimeException("Got unknown direction " + step);
        }
    }

    /**
     * Compute distance from origin
     *
     * @param point
     * @return
     */
    public int computeDistance(HexPoint point) {
        int distance = (Math.abs(point.getX()) + Math.abs(point.getY()) + Math.abs(point.getZ())) / 2;
        return distance;
    }
}
