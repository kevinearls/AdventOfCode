package com.kevinearls.adventofcode.particles;

public class Velocity {
    public Long x=0L;
    public Long y=0L;
    public Long z=0L;

    public Velocity(Long x, Long y, Long z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
        return "Velocity{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
