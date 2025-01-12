# Algorithm description
HumanSpawner generates random people and then transfers them to the controller. <br>
The controller checks if there are free elevators.<br>
The controller has two queues: a queue of floors where people go down and a queue of floors where people go up. <br>
In the absence of free elevators, the new person's floor is added to one of the controller queues. <br>
If there is a free elevator, the controller assigns the current delivery task for this free elevator. <br>
The elevator task is going to the floor of this person, 
adding all possible people(elevator capacity is limited) from current floor queue to elevator. <br>
After that, the elevator takes these people to required floors. <br>
When the elevator delivered all the people to the floors and became empty, 
it asks the controller for a new task. The controller assignes a task with the biggest queue(by size) to an eager for tasks performing elevator.
