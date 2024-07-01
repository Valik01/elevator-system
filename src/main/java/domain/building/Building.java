package domain.building;

import domain.building.elevator.Elevator;
import domain.building.floor.Floor;
import lombok.Getter;
import lombok.ToString;
import property.PropertyKey;
import property.PropertyManager;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ToString
@Getter
public class Building {
    private final int numberOfFloors;
    private final int numberOfElevators;
    private final List<Elevator> elevators;
    private final List<Floor> floors;

    public Building() {
        numberOfFloors = PropertyManager.getPropertyByPredicate(PropertyKey.NUMBER_OF_FLOORS);
        numberOfElevators = PropertyManager.getPropertyByPredicate(PropertyKey.NUMBER_OF_ELEVATORS);
        this.floors = IntStream.range(1, numberOfFloors + 1).mapToObj(Floor::new).collect(Collectors.toList());
        this.elevators = IntStream.range(1, numberOfElevators + 1).mapToObj(Elevator::new).collect(Collectors.toList());
    }

    public Floor getFloorByNumber(int number) {
        return floors.get(number - 1);
    }
}
