package domain.building.elevator;

import domain.Direction;
import domain.building.floor.Floor;
import domain.human.Human;
import domain.human.HumanStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import property.PropertyKey;
import property.PropertyManager;
import service.SystemController;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static domain.Constants.NUMBER_START_FLOOR;
import static domain.Constants.START_STATUS;

@Slf4j
@ToString
@Getter
@Setter
public class Elevator implements Runnable {
    private final int number;
    private int currentLiftingCapacity;
    private final int delay;
    private final int doorOpenCloseDelay;

    private SystemController controller;
    private Direction directionQueueOnFloor;
    private int currentFloor = NUMBER_START_FLOOR;
    private Floor destinationFloor;
    private final List<Human> humans;
    private ElevatorStatus status = START_STATUS;
    private boolean work;

    public Elevator(int number) {
        this.number = number;
        currentLiftingCapacity = PropertyManager.getPropertyByPredicate(PropertyKey.LIFTING_CAPACITY);
        doorOpenCloseDelay = PropertyManager.getPropertyByPredicate(PropertyKey.DOORS_DELAY);
        delay = PropertyManager.getPropertyByPredicate(PropertyKey.DELAY);
        humans = new CopyOnWriteArrayList<>();
        work = true;
    }

    public void setController(SystemController controller) {
        this.controller = controller;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (work) {
            if (destinationFloor == null) {
                synchronized (controller) {
                    controller.wait();
                }
            } else {
                status = ElevatorStatus.BUSY;

                goToTheFloor(destinationFloor.getNumber());

                loadHumans(destinationFloor, directionQueueOnFloor);

                deliverHumans();

                destinationFloor = null;
                status = ElevatorStatus.FREE;

                controller.getTask(this);
            }
        }
    }

    public void finish() {
        work = false;
    }

    public void operate(Floor destinationFloor, Direction directionQueueOnFloor) {
        checkArgument(status == ElevatorStatus.FREE, "Elevator is busy");
        this.destinationFloor = destinationFloor;
        this.directionQueueOnFloor = directionQueueOnFloor;
        if (destinationFloor != null) {
            status = ElevatorStatus.BUSY;
        }
    }

    @SneakyThrows
    private void goToTheFloor(int numberDirectionFloor) {
        while (numberDirectionFloor != currentFloor) {
            if (numberDirectionFloor > currentFloor) {
                currentFloor++;
            } else {
                currentFloor--;
            }
            Thread.sleep(delay);
        }
        log.debug("Elevator arrived at floor number: {}", currentFloor);
    }

    @SneakyThrows
    private void loadHumans(Floor floor, Direction directionQueueOnFloor) {
        openCloseDoors();
        if (Direction.DOWN.equals(directionQueueOnFloor)) {
            for (Human human : floor.getQueueDown()) {
                if (human.getWeight() > currentLiftingCapacity) {
                    continue;
                }
                addHuman(human);
                human.setStatus(HumanStatus.RIDES_THE_ELEVATOR);
                floor.deleteHumanInTheQueueDown(human);
                log.debug("Elevator load at floor number: {} human: {}", currentFloor, human.getId());
            }
        } else {
            for (Human human : floor.getQueueUp()) {
                if (human.getWeight() > currentLiftingCapacity) {
                    continue;
                }
                addHuman(human);
                human.setStatus(HumanStatus.RIDES_THE_ELEVATOR);
                floor.deleteHumanInTheQueueUp(human);
                log.debug("Elevator load at floor number: {} human: {}", currentFloor, human.getId());
            }
        }
        openCloseDoors();
    }

    private void deliverHumans() {
        Map<Integer, List<Human>> humansOrderedByNearestFloor = humans.stream()
                .sorted(Comparator.comparingInt(human -> Math.abs(human.getDestinationFloor() - currentFloor)))
                .collect(Collectors.groupingBy(Human::getDestinationFloor, LinkedHashMap::new, Collectors.toList()));

        for (Map.Entry<Integer, List<Human>> entry : humansOrderedByNearestFloor.entrySet()) {
            goToTheFloor(entry.getKey());
            openCloseDoors();
            entry.getValue().forEach(this::deleteHuman);
            openCloseDoors();
        }
    }

    @SneakyThrows
    private void openCloseDoors() {
        Thread.sleep(doorOpenCloseDelay);
    }

    private void addHuman(Human human) {
        checkArgument(!humans.contains(human), "This human is already in the elevator");
        humans.add(human);
        currentLiftingCapacity -= human.getWeight();
        controller.addStartFloorInStorage(this, human.getStartFloor());
        //log.debug("Human: {} got into the elevator on the floor № {}", human.getId(), human.getStartFloor());
    }

    private void deleteHuman(Human human) {
        humans.removeIf(human1 -> human.getId().equals(human1.getId()));
        human.setStatus(HumanStatus.GOT_OUT_OF_THE_ELEVATOR);
        currentLiftingCapacity += human.getWeight();
        controller.addFinalFloorInStorage(this, human.getDestinationFloor());
        log.debug("Elevator delivered to the floor number: {} human: {}", currentFloor, human.getId());
    }
}