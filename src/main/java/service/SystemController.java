package service;

import domain.Direction;
import domain.building.Building;
import domain.building.elevator.Elevator;
import domain.building.elevator.ElevatorStatus;
import domain.building.floor.Floor;
import domain.human.Human;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import storage.SystemStorage;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class SystemController {
    private final Building building;
    private final HumanSpawner humanSpawner;
    private final SystemStorage storage;
    private final Queue<Floor> queueUp = new LinkedList<>();
    private final Queue<Floor> queueDown = new LinkedList<>();

    public SystemController(Building building, SystemStorage storage) {
        this.building = building;
        this.humanSpawner = new HumanSpawner(building.getNumberOfFloors(), this);
        this.storage = storage;
        building.getElevators().forEach(elevator -> elevator.setController(this));
    }

    public void startSystem() {
        List<Thread> elevatorThreads = building.getElevators().stream()
                .map(Thread::new)
                .collect(Collectors.toList());

        int numberElevatorThread = 0;

        for (Thread elevatorThread : elevatorThreads) {
            numberElevatorThread++;
            elevatorThread.setName("Elevator №" + numberElevatorThread);
            elevatorThread.start();
        }

        Thread humanSpawnThread = new Thread(humanSpawner);
        humanSpawnThread.setName("HumanSpawner");
        humanSpawnThread.start();
    }

    public void stopSystem() {
        building.getElevators().forEach(Elevator::finish);
        humanSpawner.finish();
    }

    public void addHuman(Human human) {
        addHumanToTheQueueOnTheFloor(human);
        Floor floor = building.getFloorByNumber(human.getStartFloor());

        if (!checkAndGiveTaskElevator(floor, human.getDirection())) {
            addFloorInQueue(floor, human.getDirection());
            log.debug("Human: {} added to controller queue.", human.getId());
        }
    }

    public void addStartFloorInStorage(Elevator elevator, int numberFloor) {
        storage.persistStartFloors(elevator, numberFloor);
    }

    public void addFinalFloorInStorage(Elevator elevator, int numberFloor) {
        storage.persistFinalFloors(elevator, numberFloor);
    }

    public void getTask(Elevator elevator) {
        if (queueDown.size() > queueUp.size()) {
            checkAndGiveTaskElevator(queueDown.remove(), Direction.DOWN, elevator);
        } else if (!queueUp.isEmpty()) {
            checkAndGiveTaskElevator(queueUp.remove(), Direction.UP, elevator);
        }
    }

    private void addHumanToTheQueueOnTheFloor(Human human) {
        Floor floor = building.getFloorByNumber(human.getStartFloor());
        if (human.getDirection() == Direction.DOWN) {
            floor.addHumanToTheQueueDown(human);
        } else {
            floor.addHumanToTheQueueUp(human);
        }
    }

    private void addFloorInQueue(Floor floor, Direction humanDirection) {
        if (humanDirection == Direction.DOWN && !queueDown.contains(floor)) {
            queueDown.add(floor);
        } else if (humanDirection == Direction.UP && !queueUp.contains(floor)) {
            queueUp.add(floor);
        }
    }

    private boolean checkAndGiveTaskElevator(Floor floor, Direction direction) {
        for (Elevator elevator : building.getElevators()) {
            if (checkAndGiveTaskElevator(floor, direction, elevator)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkAndGiveTaskElevator(Floor floor, Direction direction, Elevator elevator) {
        if (ElevatorStatus.FREE.equals(elevator.getStatus())) {
            elevator.operate(floor, direction);

            synchronized (this) {
                this.notifyAll();
            }

            return true;
        }
        return false;
    }
}