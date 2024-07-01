package property;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PropertyManagerTest {

    @Test
    void getPropertyByPredicateTest() {
        assertTrue(PropertyManager.getPropertyByPredicate(PropertyKey.MAX_WEIGHT) > 0);
        assertTrue(PropertyManager.getPropertyByPredicate(PropertyKey.FREQUENCY_GENERATION) > 0);
        assertTrue(PropertyManager.getPropertyByPredicate(PropertyKey.NUMBER_OF_ELEVATORS) > 0);
        assertTrue(PropertyManager.getPropertyByPredicate(PropertyKey.NUMBER_OF_FLOORS) > 0);
        assertTrue(PropertyManager.getPropertyByPredicate(PropertyKey.DOORS_DELAY) > 0);
        assertTrue(PropertyManager.getPropertyByPredicate(PropertyKey.LIFTING_CAPACITY) > 0);
        assertTrue(PropertyManager.getPropertyByPredicate(PropertyKey.DELAY) > 0);
    }
}