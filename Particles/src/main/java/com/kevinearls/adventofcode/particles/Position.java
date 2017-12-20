package com.kevinearls.adventofcode.particles;

public class Position {
    public Long x=0L;
    public Long y=0L;
    public Long z=0L;

    public Position(Long x, Long y, Long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Long getManhattanDistance() {
        Long manhattanDistance = Math.abs(x) + Math.abs(y) + Math.abs(z);
        return manhattanDistance;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    public Long getZ() {
        return z;
    }

    public void setZ(Long z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (!x.equals(position.x)) return false;
        if (!y.equals(position.y)) return false;
        return z.equals(position.z);
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        result = 31 * result + z.hashCode();
        return result;
    }
}
