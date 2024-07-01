import domain.building.Building;
import lombok.SneakyThrows;
import service.SystemController;
import storage.StatisticsPrinter;
import storage.SystemStorage;

import java.util.concurrent.TimeUnit;

public class Runner {
    @SneakyThrows
    public static void main(String[] args) {
        Building building = new Building();
        SystemStorage storage = new SystemStorage();
        SystemController controller = new SystemController(building, storage);
        controller.startSystem();

        TimeUnit.SECONDS.sleep(15);

        controller.stopSystem();

        TimeUnit.SECONDS.sleep(10);

        new StatisticsPrinter(storage, building.getElevators()).printStatistics();
    }
}