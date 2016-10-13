package org.mesosphere.ecs.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QueueGenerator {

    public static void main(String[] args) {
        // Define parameters to generate a random queue
        int amount = 100;
        int floors = 8;
        int density = 4;

        List<String> queue = generateQueue(amount, floors, density);
        queue.forEach(System.out::println);
    }

    private static List<String> generateQueue(int amount, int floors, int density) {
        List<String> queue = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            int step = rnd(0, amount / density);
            int floor = rnd(0, floors);
            int destination = rnd(0, floors);

            queue.add(String.format("%03d, %03d, %03d", step, floor, destination));
        }

        Collections.sort(queue);

        return queue;
    }

    private static int rnd(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
