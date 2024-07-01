package service;

import domain.human.Human;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import property.PropertyKey;
import property.PropertyManager;

import java.util.UUID;

@Slf4j
@Getter
public class HumanSpawner implements Runnable {
    private final int numberFloors;
    private final SystemController controller;
    private final int generationFrequency;
    private boolean work;

    public HumanSpawner(int numberFloors, SystemController controller) {
        this.numberFloors = numberFloors;
        this.controller = controller;
        this.generationFrequency = PropertyManager.getPropertyByPredicate(PropertyKey.FREQUENCY_GENERATION);
        this.work = true;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (work) {
            controller.addHuman(spawnHuman());
            Thread.sleep(generationFrequency);
        }
    }

    public void finish() {
        work = false;
    }

    private Human spawnHuman() {
        String randomId = UUID.randomUUID().toString();
        int randomWeight = randomIntInRange(1, PropertyManager.getPropertyByPredicate(PropertyKey.MAX_WEIGHT));
        int randomStartFloor = randomIntInRange(1, numberFloors);
        int randomDestinationFloor = randomIntInRange(1, numberFloors);

        while (randomStartFloor == randomDestinationFloor) {
            randomDestinationFloor = randomIntInRange(1, numberFloors);
        }

        return new Human(randomId, randomWeight, randomStartFloor, randomDestinationFloor);
    }

    private int randomIntInRange(int start, int end) {
        return (int) (start + Math.random() * end);
    }
}