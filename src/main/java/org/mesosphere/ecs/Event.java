package org.mesosphere.ecs;

import org.mesosphere.ecs.elevator.Direction;

public class Event {

    private int timeStep, floor, destination;

    public Event(int timeStep, int floor, int destination) {
        this.timeStep = timeStep;
        this.floor = floor;
        this.destination = destination;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public Direction getDirection() {
        return (floor > destination) ? Direction.DOWN : Direction.UP;
    }

    @Override
    public String toString() {
        return String.format("Event{timeStep=%d, floor=%d, destination=%d}", timeStep, floor, destination);
    }
}
