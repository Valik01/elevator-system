package storage;

import domain.building.elevator.Elevator;

import java.util.List;
import java.util.Map;

public class StatisticsPrinter {
    private final SystemStorage storage;
    private final List<Elevator> elevators;

    public StatisticsPrinter(SystemStorage storage, List<Elevator> elevators) {
        this.storage = storage;
        this.elevators = elevators;
    }

    public void printStatistics() {
        System.out.println("Statistics:");

        elevators.stream()
                .map(elevator -> "Elevator №" + elevator.getNumber() + " transported "
                        + storage.loadNumberHumansTransported(elevator) + " humans")
                .forEach(System.out::println);

        for (Elevator elevator : elevators) {
            Map<Integer, Integer> startFloorsMap = storage.loadStartFloors(elevator);
            System.out.println("Elevator №" + elevator.getNumber());
            for (Map.Entry<Integer, Integer> entry : startFloorsMap.entrySet()) {
                System.out.println(entry.getValue() + " people loaded on floor number " + entry.getKey());
            }
        }

        for (Elevator elevator : elevators) {
            Map<Integer, Integer> finalFloorsMap = storage.loadFinalFloors(elevator);
            System.out.println("Elevator №" + elevator.getNumber());
            for (Map.Entry<Integer, Integer> entry : finalFloorsMap.entrySet()) {
                System.out.println(entry.getValue() + " people delivered to floor number " + entry.getKey());
            }
        }
    }
}