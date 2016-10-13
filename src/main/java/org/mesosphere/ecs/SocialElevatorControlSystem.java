package org.mesosphere.ecs;

import org.mesosphere.ecs.elevator.Elevator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Queue;

public class SocialElevatorControlSystem implements ElevatorControlSystem {
    private static final int NUM_ELEVATORS = 16;

    private ArrayList<Elevator> elevators = new ArrayList<>();
    private Queue<Event> pickupQueue = new ArrayDeque<>();

    public SocialElevatorControlSystem(int numberFloors, int numberElevators) {
        if (numberFloors < 0)
            throw new IllegalArgumentException("Floors must be a positive number");
        if (numberElevators < 0)
            throw new IllegalArgumentException("Elevators must be a positive number");
        if (numberElevators > NUM_ELEVATORS)
            throw new IllegalArgumentException("Number of elevators too high");

        // Initialize all elevators
        for (int i = 0; i < numberElevators; i++) elevators.add(new Elevator(i, numberFloors));
    }

    @Override
    public void pickup(Event event) {
        if (event == null) throw new IllegalArgumentException("Event can't be null");

        System.out.println(String.format("SECS incoming pickup request Event[%s]", event));
        pickupQueue.add(event);
    }

    @Override
    public void pulse() {
        while (!pickupQueue.isEmpty()) {
            // Find least busy elevator
            Optional<Elevator> e = elevators.stream().sorted((e1, e2) -> Integer.compare(e1.getTotalWeight(), e2.getTotalWeight())).findFirst();

            Event event = pickupQueue.poll();
            if (e.isPresent()) e.get().addRoute(event);
        }

        elevators.forEach(Elevator::pulse);
    }
}
