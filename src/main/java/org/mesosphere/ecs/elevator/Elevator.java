package org.mesosphere.ecs.elevator;

import org.mesosphere.ecs.Event;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Elevator {
    private int id;
    private int floor;
    private int floors;

    private int[] weightedRoute;

    public Elevator(int id, int floors) {
        if (floors < 0)
            throw new IllegalArgumentException("Floors must be a positive number");

        this.id = id;
        this.floor = 0;
        this.floors = floors;

        weightedRoute = new int[floors];
    }

    public int getId() {
        return id;
    }

    public int getFloor() {
        return floor;
    }

    public void pulse() {
        // Reduce the current floor's weight by one
        if (weightedRoute[floor] > 0) weightedRoute[floor]--;

        // The direction this elevator should move base on it's weighted routes
        int newFloor = floor;
        int weightUp = getArrayElement(weightedRoute, floor + 1);
        int weightDown = getArrayElement(weightedRoute, floor - 1);
        if (weightUp > 0 && weightDown <= 0) { // Move up
            weightedRoute[floor] = 0;
            newFloor = floor + 1;
        } else if (weightDown > 0 && weightUp <= 0) { // Move down
            weightedRoute[floor] = 0;
            newFloor = floor - 1;
        } else if (weightUp > 0 && weightDown > 0) {
            newFloor += (weightUp > weightDown) ? 1 : -1;
        }

        if (floor != newFloor) { // Are we on hold?
            System.out.println(String.format("%s moving to floor [%d]", this, newFloor));
            floor = newFloor;
        }
    }

    public int getTotalWeight() {
        return Arrays.stream(weightedRoute).sum();
    }

    public void addRoute(Event event) {
        if (event == null) throw new IllegalArgumentException("Event can't be null");

        System.out.println(String.format("%s received new %s", this, event));

        int[] route = weightsBitMap(event);
        weightedRoute = IntStream.range(0, floors).map(i -> route[i] + weightedRoute[i]).toArray();

        // Add route from current floor to start
        if (floor != event.getFloor()) {
            Event startEvent = new Event(0, floor, event.getFloor());
            int[] routeToStart = weightsBitMap(startEvent);
            routeToStart[event.getFloor()] = 0;
            weightedRoute = IntStream.range(0, floors).map(i -> routeToStart[i] + weightedRoute[i]).toArray();
        }

        System.out.println(String.format("%s updated weightedRoute to %s", this, Arrays.toString(weightedRoute)));
    }

    private int[] weightsBitMap(Event event) {
        if (event == null) throw new IllegalArgumentException("Event can't be null");

        int[] weights;
        if (event.getDirection() == Direction.DOWN) {
            weights = IntStream.range(0, floors).map(i -> (i <= event.getFloor() && i >= event.getDestination()) ? 1 : 0).toArray();
        } else {
            weights = IntStream.range(0, floors).map(i -> (i >= event.getFloor() && i <= event.getDestination()) ? 1 : 0).toArray();
        }

        return weights;
    }

    @Override
    public String toString() {
        return String.format("Elevator[id=%d, floor=%d, totalWeight=%d, weightedRoute=%s]", id, floor, getTotalWeight(), Arrays.toString(weightedRoute));
    }

    private int getArrayElement(int[] array, int index) {
        if (index < 0 || index > array.length - 1) return -1;
        return array[index];
    }

}
