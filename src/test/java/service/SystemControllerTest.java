package service;

import domain.building.Building;
import domain.human.Human;
import org.junit.jupiter.api.Test;
import storage.SystemStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SystemControllerTest {

    @Test
    void addHumanTest() {
        Building building = new Building();
        SystemController controller = new SystemController(building, new SystemStorage());
        Human human = new Human("123", 123, 2, 4);

        controller.addHuman(human);

        assertEquals(1, building.getFloorByNumber(2).getQueueUp().size());
    }
}
