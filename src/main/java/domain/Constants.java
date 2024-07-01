package domain;

import domain.building.elevator.ElevatorStatus;

import java.util.function.Predicate;

public final class Constants {
    public static final Predicate<Integer> IS_POSITIVE_NUMBER = number -> number > 0;

    public static final int NUMBER_START_FLOOR = 1;
    public static final ElevatorStatus START_STATUS = ElevatorStatus.FREE;

    private Constants() {
    }
}
