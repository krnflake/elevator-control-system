package org.mesosphere.ecs;

interface ElevatorControlSystem {
    void pickup(Event event);

    void pulse();
}
