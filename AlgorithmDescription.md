#Algorithm description
HumanSpawner generates random people and then transfers them to the controller.
The controller checks if there are free elevators.
The controller has two queues: a queue of floors where people go down and a queue of floors where people go up.
In the absence of free elevators, the new person's floor is added to one of the controller queues.
If there is a free elevator, the controller assigns the current delivery task for this free elevator.
The elevator task is going to the floor of this person, 
adding all possible people(elevator capacity is limited) from current floor queue to elevator.
After that, the elevator takes these people to required floors. 
When the elevator delivered all the people to the floors and became empty, 
it asks the controller for a new task. The controller assignes a task with the biggest queue(by size) to an eager for tasks performing elevator.
