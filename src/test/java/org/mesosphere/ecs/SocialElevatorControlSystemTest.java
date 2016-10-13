package org.mesosphere.ecs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SocialElevatorControlSystemTest {

    @Test
    public void simpleQueue() {
        int numberFloors = 3;
        int numberElevators = 1;

        System.out.println(" +++++ RUNNING simpleQueue +++++");
        runSimulation(numberFloors, numberElevators, "simpleQueue.txt");
        System.out.println(" +++++ DONE simpleQueue +++++\n");
    }

    @Test
    public void advancedQueue() {
        int numberFloors = 5;
        int numberElevators = 2;

        System.out.println(" +++++ RUNNING advancedQueue +++++");
        runSimulation(numberFloors, numberElevators, "advancedQueue.txt");
        System.out.println(" +++++ DONE simpleQueue +++++\n");
    }

    private void runSimulation(int numberFloors, int numberElevators, String file) {
        SocialElevatorControlSystem ecs = new SocialElevatorControlSystem(numberFloors, numberElevators);

        List<Event> events = new ArrayList<>();

        // Load events from local file
        Scanner scanner = new Scanner(this.getClass().getResourceAsStream(file));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("#")) continue; // Ignore comments

            Integer[] data = Arrays.stream(line.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
            Event event = new Event(data[0], data[1], data[2]);

            events.add(event);
        }

        // Run simulation
        System.out.println("Starting simulation");
        int timeStep = 0;
        while (timeStep < 100) {
            for (Event e : events) {
                if (e.getTimeStep() == timeStep) ecs.pickup(e);
            }

            ecs.pulse();

            timeStep++;
        }
    }
}
