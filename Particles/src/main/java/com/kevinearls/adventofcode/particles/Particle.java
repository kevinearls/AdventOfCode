package com.kevinearls.adventofcode.particles;

public class Particle {
    Position startingPosition;
    Position position;
    Acceleration acceleration;
    Velocity velocity;
    private Integer id;


    public Particle(Integer id, String line) {
        this.id = id;
        String[] parts = line.split(",");

        Long xPosition = Long.valueOf(parts[0].trim().substring(3));  // chop off "p=<"
        Long yPosition = Long.valueOf(parts[1]);
        Long zPosition = Long.valueOf(parts[2].substring(0, parts[2].length() - 1)); // chop of closing >
        this.position = new Position(xPosition, yPosition, zPosition);
        this.startingPosition = new Position(xPosition, yPosition, zPosition);

        Long xVelocity = Long.valueOf(parts[3].trim().substring(3));  // chop off "v=<"
        Long yVelocity = Long.valueOf(parts[4]);
        Long zVelocity = Long.valueOf(parts[5].substring(0, parts[5].length() - 1)); // chop of closing >
        this.velocity = new Velocity(xVelocity, yVelocity, zVelocity);

        Long xAccelleration = Long.valueOf(parts[6].trim().substring(3));  // chop off "p=<"
        Long yAccelleration = Long.valueOf(parts[7]);
        Long zAccelleration = Long.valueOf(parts[8].substring(0, parts[8].length() - 1)); // chop of closing >
        this.acceleration = new Acceleration(xAccelleration, yAccelleration, zAccelleration);
    }

    /*public Particle(Position position, Acceleration acceleration, Velocity velocity) {
        this.position = position;
        this.acceleration = acceleration;
        this.velocity = velocity;
    }*/

    /*
    Each tick, all particles are updated simultaneously. A particle's properties are updated in
the following order:

Increase the X velocity by the X acceleration.
Increase the Y velocity by the Y acceleration.
Increase the Z velocity by the Z acceleration.
Increase the X position by the X velocity.
Increase the Y position by the Y velocity.
Increase the Z position by the Z velocity.
     */
    public void move() {
        velocity.setX(velocity.getX() + acceleration.getX());
        velocity.setY(velocity.getY() + acceleration.getY());
        velocity.setZ(velocity.getZ() + acceleration.getZ());

        position.setX(position.getX() + velocity.getX());
        position.setY(position.getY() + velocity.getY());
        position.setZ(position.getZ() + velocity.getZ());
    }

    public Long getManhattanDistance() {
        Long manhattanDistance = position.getManhattanDistance();
        return manhattanDistance;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Acceleration getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Acceleration acceleration) {
        this.acceleration = acceleration;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "id=" + id +
                ", starting position=" + startingPosition +
                ", position=" + position +
                ", acceleration=" + acceleration +
                ", velocity=" + velocity +
                '}';
    }
}
