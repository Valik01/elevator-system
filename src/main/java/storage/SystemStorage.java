package storage;

import domain.building.elevator.Elevator;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SystemStorage {
    private final Map<Elevator, Integer> numberHumansTransported = new ConcurrentHashMap<>();
    private final Map<Elevator, Map<Integer, Integer>> startFloors = new ConcurrentHashMap<>();
    private final Map<Elevator, Map<Integer, Integer>> finalFloors = new ConcurrentHashMap<>();

    public Map<Integer, Integer> loadStartFloors(Elevator elevator) {
        return startFloors.getOrDefault(elevator, Collections.emptyMap());
    }

    public Map<Integer, Integer> loadFinalFloors(Elevator elevator) {
        return finalFloors.getOrDefault(elevator, Collections.emptyMap());
    }

    public int loadNumberHumansTransported(Elevator elevator) {
        return numberHumansTransported.getOrDefault(elevator, 0);
    }

    public void persistStartFloors(Elevator elevator, int numberFloor) {
        Map<Integer, Integer> innerMap = startFloors.computeIfAbsent(elevator, map -> new ConcurrentHashMap<>());

        innerMap.compute(numberFloor, (key, number) -> (number == null) ? 1 : number + 1);
    }

    public void persistFinalFloors(Elevator elevator, int numberFloor) {
        Map<Integer, Integer> innerMap = finalFloors.computeIfAbsent(elevator, map -> new ConcurrentHashMap<>());

        innerMap.compute(numberFloor, (key, number) -> (number == null) ? 1 : number + 1);
        persistTransportedHuman(elevator);
    }

    private void persistTransportedHuman(Elevator elevator) {
        if (!numberHumansTransported.containsKey(elevator)) {
            numberHumansTransported.put(elevator, 0);
        }
        numberHumansTransported.compute(elevator, (key, number) -> (number == null) ? 1 : number + 1);
    }
}