package pro.sisit.javacourse;

import pro.sisit.javacourse.entity.DeliveryTask;
import pro.sisit.javacourse.entity.Route;
import pro.sisit.javacourse.entity.Transport;

import java.util.Comparator;
import java.util.List;

public class PathFinder {

    public Transport getOptimalTransport(DeliveryTask deliveryTask, List<Transport> transports) {
        return transports.stream().filter(transport -> transport.getVolume() >= deliveryTask.getVolume()
            && deliveryTask.getRoutes().stream().anyMatch(route -> route.getType() == transport.getType()))
                .min(Comparator.comparingDouble(transport -> transport.getPrice() * deliveryTask.getRoutes()
                        .stream().filter(route -> route.getType() == transport.getType()).findFirst()
                        .get().getLength())).orElseThrow(() -> new RuntimeException("Optimal transport not found."));

    }

}
