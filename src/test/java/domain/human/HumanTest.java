package domain.human;

import domain.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HumanTest {

    @Test
    void constructorTest(){
        Human human = new Human("123", 123, 2, 4);

        assertEquals(123, human.getWeight());
        assertEquals("123", human.getId());
        assertEquals(2, human.getStartFloor());
        assertEquals(4, human.getDestinationFloor());
        assertEquals(Direction.UP, human.getDirection());
    }
}
