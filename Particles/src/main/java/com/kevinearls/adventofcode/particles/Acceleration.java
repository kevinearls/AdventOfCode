package com.kevinearls.adventofcode.particles;

public class Acceleration {
    public Long x=0L;
    public Long y=0L;
    public Long z=0L;

    public Acceleration(Long x, Long y, Long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Long getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    public Long getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "Acceleration{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
