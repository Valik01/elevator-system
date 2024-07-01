package service;

import domain.building.Building;
import domain.building.floor.Floor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import storage.SystemStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HumanSpawnerTest {

    @SneakyThrows
    @Test
    void runTest() {
        Building building = new Building();
        SystemStorage storage = new SystemStorage();
        SystemController controller = new SystemController(building, storage);

        HumanSpawner spawner = controller.getHumanSpawner();
        Thread humanSpawnerThread = new Thread(spawner);
        humanSpawnerThread.start();

        int sleepTime = 3000;
        Thread.sleep(sleepTime);

        spawner.finish();

        int numberGeneratedHumans = sleepTime / (spawner.getGenerationFrequency());

        int allHumansAddedToFloors = 0;

        for (Floor floor : building.getFloors()) {
            allHumansAddedToFloors += floor.getQueueDown().size();
            allHumansAddedToFloors += floor.getQueueUp().size();
        }

        assertEquals(numberGeneratedHumans, allHumansAddedToFloors);
    }
}