package domain.human;

import domain.Direction;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.NonFinal;

@Getter
public class Human {
    private final String id;
    private final int weight;
    private final int startFloor;
    private final int destinationFloor;
    private final Direction direction;
    @Setter
    @NonFinal
    HumanStatus status;

    public Human(String id, int weight, int startFloor, int destinationFloor) {
        this.id = id;
        this.weight = weight;
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
        this.direction = determineDirection(startFloor, destinationFloor);
        status = null;
    }

    private Direction determineDirection(int startFloor, int destinationFloor) {
        if (startFloor < destinationFloor) {
            return Direction.UP;
        } else {
            return Direction.DOWN;
        }
    }
}