package domain.building.floor;

import domain.human.Human;
import domain.human.HumanStatus;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.google.common.base.Preconditions.checkArgument;

@Slf4j
@Getter
public class Floor {
    private final int number;
    private final Queue<Human> queueUp = new ConcurrentLinkedQueue<>();
    private final Queue<Human> queueDown = new ConcurrentLinkedQueue<>();

    public Floor(int number) {
        checkArgument(number > 0, "Floor number must be a positive");
        this.number = number;
    }

    public void addHumanToTheQueueUp(Human human) {
        queueUp.add(human);
        human.setStatus(HumanStatus.WAITING_FOR_AN_ELEVATOR);
        log.debug("Human: {} with a weight of {}, Start Floor: {}, Destination Floor: {} added to the queue UP on the floor № {}", human.getId(), human.getWeight(), human.getStartFloor(), human.getDestinationFloor(), human.getStartFloor());
    }

    public void addHumanToTheQueueDown(Human human) {
        queueDown.add(human);
        human.setStatus(HumanStatus.WAITING_FOR_AN_ELEVATOR);
        log.debug("Human: {} with a weight of {}, Start Floor: {}, Destination Floor: {} added to the queue DOWN on the floor № {}", human.getId(), human.getWeight(), human.getStartFloor(), human.getDestinationFloor(), human.getStartFloor());
    }

    public void deleteHumanInTheQueueUp(Human human) {
        queueUp.remove(human);
    }

    public void deleteHumanInTheQueueDown(Human human) {
        queueDown.remove(human);
    }
}