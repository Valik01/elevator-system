package property;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

import static domain.Constants.IS_POSITIVE_NUMBER;

@AllArgsConstructor
@Getter
public enum PropertyKey {
    NUMBER_OF_FLOORS("building.floors", IS_POSITIVE_NUMBER),
    NUMBER_OF_ELEVATORS("building.elevators", IS_POSITIVE_NUMBER),
    MAX_WEIGHT("human.weight.max", IS_POSITIVE_NUMBER),
    LIFTING_CAPACITY("elevator.lifting.capacity", IS_POSITIVE_NUMBER),
    DOORS_DELAY("elevator.doors.delay", IS_POSITIVE_NUMBER),
    DELAY("elevator.floor.delay", IS_POSITIVE_NUMBER),
    FREQUENCY_GENERATION("spawner.frequency.generation", IS_POSITIVE_NUMBER);

    private final String key;
    private final Predicate<Integer> validationCondition;
}
