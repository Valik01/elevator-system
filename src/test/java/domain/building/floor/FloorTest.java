package domain.building.floor;

import domain.human.Human;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FloorTest {
    private Floor floor;

    @Test
    void validConstructorTest() {
        floor = new Floor(1);
        assertEquals(1, floor.getNumber());
    }

    @Test
    void invalidConstructorTest() {
        assertThrows(IllegalArgumentException.class, () -> new Floor(0));
    }

    @Test
    void addHumanToTheQueueUpTest() {
        floor = new Floor(2);
        Human human = new Human("123", 123, 2, 4);

        floor.addHumanToTheQueueUp(human);

        assertEquals(human, floor.getQueueUp().peek());
    }

    @Test
    void deleteHumanInTheQueueUpTest() {
        floor = new Floor(2);
        Human human = new Human("123", 123, 2, 4);
        floor.addHumanToTheQueueUp(human);

        floor.deleteHumanInTheQueueUp(human);

        assertFalse(floor.getQueueUp().contains(human));
    }

    @Test
    void addHumanToTheQueueDownTest() {
        floor = new Floor(2);
        Human human = new Human("123", 123, 2, 4);

        floor.addHumanToTheQueueDown(human);

        assertEquals(human, floor.getQueueDown().peek());
    }

    @Test
    void deleteHumanInTheQueueDownTest() {
        floor = new Floor(2);
        Human human = new Human("123", 123, 2, 4);
        floor.addHumanToTheQueueDown(human);

        floor.deleteHumanInTheQueueDown(human);

        assertFalse(floor.getQueueDown().contains(human));
    }
}