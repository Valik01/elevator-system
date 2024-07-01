package storage;

import domain.building.elevator.Elevator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SystemStorageTest {

    @Test
    void persistStartFloorsTest() {
        Elevator elevator = new Elevator(1);
        SystemStorage storage = new SystemStorage();

        int floorTwo = 2;
        int floorFive = 5;

        for (int i = 0; i < 5; i++) {
            storage.persistStartFloors(elevator, floorTwo);
        }

        for (int i = 0; i < 10; i++) {
            storage.persistStartFloors(elevator, floorFive);
        }

        int numberRecordsOnSecondFloor = storage.loadStartFloors(elevator).get(floorTwo);
        int numberRecordsOnFiveFloor = storage.loadStartFloors(elevator).get(floorFive);

        assertEquals(5, numberRecordsOnSecondFloor);
        assertEquals(10, numberRecordsOnFiveFloor);

    }

    @Test
    void persisFinalFloorsTest() {
        Elevator elevator = new Elevator(1);
        SystemStorage storage = new SystemStorage();

        int numberSecondFloor = 2;
        int numberFifthFloor = 5;

        for (int i = 0; i < 5; i++) {
            storage.persistFinalFloors(elevator, numberSecondFloor);
        }

        for (int i = 0; i < 10; i++) {
            storage.persistFinalFloors(elevator, numberFifthFloor);
        }

        int numberRecordsOnSecondFloor = storage.loadFinalFloors(elevator).get(numberSecondFloor);
        int numberRecordsOnFiveFloor = storage.loadFinalFloors(elevator).get(numberFifthFloor);
        int totalTransportedHumans = storage.loadNumberHumansTransported(elevator);

        assertEquals(5, numberRecordsOnSecondFloor);
        assertEquals(10, numberRecordsOnFiveFloor);
        assertEquals(15, totalTransportedHumans);
    }

    @Test
    void loadStartFloorsTest() {
        Elevator elevator = new Elevator(1);
        SystemStorage storage = new SystemStorage();

        int numberFirstFloor = 1;
        int numberSecondFloor = 2;

        storage.persistStartFloors(elevator, numberFirstFloor);
        storage.persistStartFloors(elevator, numberFirstFloor);
        storage.persistStartFloors(elevator, numberSecondFloor);

        assertEquals(2, storage.loadStartFloors(elevator).get(numberFirstFloor));
        assertEquals(1, storage.loadStartFloors(elevator).get(numberSecondFloor));
    }

    @Test
    void loadFinalFloorsTest() {
        Elevator elevator = new Elevator(1);
        SystemStorage storage = new SystemStorage();

        int numberFirstFloor = 1;
        int numberSecondFloor = 2;

        storage.persistFinalFloors(elevator, numberFirstFloor);
        storage.persistFinalFloors(elevator, numberFirstFloor);
        storage.persistFinalFloors(elevator, numberFirstFloor);
        storage.persistFinalFloors(elevator, numberSecondFloor);
        storage.persistFinalFloors(elevator, numberSecondFloor);

        assertEquals(3, storage.loadFinalFloors(elevator).get(numberFirstFloor));
        assertEquals(2, storage.loadFinalFloors(elevator).get(numberSecondFloor));
    }
}