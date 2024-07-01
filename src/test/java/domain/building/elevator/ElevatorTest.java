package domain.building.elevator;

import domain.building.Building;
import domain.human.Human;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import service.SystemController;
import storage.SystemStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorTest {

    @SneakyThrows
    @Test
    void runTest() {
        Building building = new Building();
        SystemStorage storage = new SystemStorage();
        SystemController controller = new SystemController(building, storage);

        Elevator elevator = building.getElevators().get(0);
        Thread elevatorThread = new Thread(elevator);
        elevatorThread.start();

        assertEquals(ElevatorStatus.FREE, elevator.getStatus());

        Human human = new Human("123", 123, 2, 4);
        controller.addHuman(human);

        assertEquals(ElevatorStatus.BUSY, elevator.getStatus());

        int timeSleep = ((human.getStartFloor() - 1 + (human.getDestinationFloor() - human.getStartFloor())) * elevator.getDelay() + (elevator.getDoorOpenCloseDelay()) * 4);

        Thread.sleep(timeSleep);
        elevator.finish();

        assertEquals(1, storage.loadNumberHumansTransported(elevator));
        assertEquals(1, storage.loadStartFloors(elevator).get(human.getStartFloor()));
        assertEquals(1, storage.loadFinalFloors(elevator).get(human.getDestinationFloor()));
    }
}