package domain.building;

import org.junit.jupiter.api.Test;
import property.PropertyKey;
import property.PropertyManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BuildingTest {
    Building building;

    @Test
    void constructorTest() {
        building = new Building();
        int numberOfFloors = PropertyManager.getPropertyByPredicate(PropertyKey.NUMBER_OF_FLOORS);
        int numberOfElevators = PropertyManager.getPropertyByPredicate(PropertyKey.NUMBER_OF_ELEVATORS);

        assertEquals(numberOfFloors, building.getNumberOfFloors());
        assertEquals(numberOfElevators, building.getNumberOfElevators());
    }

    @Test
    void getFloorByNumberTest() {
        building = new Building();

        assertNotNull(building.getFloorByNumber(1));
    }
}
