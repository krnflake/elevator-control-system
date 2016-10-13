### Elevator Control System
The following README.md describes my approach to solve the challenge.

#### Design

I've chosen to try a Test Driven Development (TDD) for this task, because I knew that at some point I need to write test cases in order to validate my algorithm. My test cases sadly went a little over the top and I've spend almost an hour to implement them. My tests load a predefined event queue from a .txt file, run a simulation and request elevator pickups based on the loaded event queue. This leads to a normalized test environment and I could tweak my algorithm based on it's results.

For the algorithm itself I've chosen to try a different approach than most others might have taken. I've decided to use a weighted graph to decide which route my elevators should take. For example if a person requests a ride from floor 2 to 4, the weights of this route get increased my 1. But I've sadly didn't have enough time to implement the weighted graph as I wanted. I originally planned to implement more dynamic weights and for example weigh 'shared' routes higher, weigh routes higher as they stay longer in the system or weigh routes higher if they go in the same direction as the elevator currently does. As I've mentioned I've sadly didn't have enough time to implement this feature so my algorithm become something like a 'Social Elevator Control System'. It it's current way, my algorithm chooses the route, that fits best for the majority of it's current riders. Aka it became 'social'. In the worst case this sadly can result in dead looks where a person never reaches it's destination.

#### Tests

Here's an example output of one test case:

```txt
SECS incoming pickup request Event[Event{timeStep=0, floor=0, destination=1}]
Elevator[id=0, floor=0, totalWeight=0, weightedRoute=[0, 0, 0]] received new Event{timeStep=0, floor=0, destination=1}
Elevator[id=0, floor=0, totalWeight=2, weightedRoute=[1, 1, 0]] updated weightedRoute to [1, 1, 0]
Elevator[id=0, floor=0, totalWeight=1, weightedRoute=[0, 1, 0]] moving to floor [1]
```

#### Todo

- [ ] Implement dynamic weights
    - [ ] Weigh 'shared' routes higher
    - [ ] Weigh routes higher as they stay longer in the system
    - [ ] Weigh routes higher if they go in the same direction as the elevator
- [ ] Benchmark the test cases
    - [ ] Based on overall time
    - [ ] Based on moves needed