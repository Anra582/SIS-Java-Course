package pro.sisit.javacourse;

import pro.sisit.javacourse.entity.DeliveryTask;
import pro.sisit.javacourse.entity.Route;
import pro.sisit.javacourse.entity.Transport;

import java.util.Comparator;
import java.util.List;

public class PathFinder {

    public Transport getOptimalTransport(DeliveryTask deliveryTask, List<Transport> transports) {
        return transports.stream().filter(transport -> isTransportSuitable(transport, deliveryTask))
                .min(Comparator.comparingDouble(transport -> calcFinalDeliveryCost(transport, deliveryTask)))
                .orElseThrow(() -> new RuntimeException("Optimal transport not found."));

    }

    private static boolean isTransportSuitable(Transport transport, DeliveryTask deliveryTask) {
        return transport.getVolume() >= deliveryTask.getVolume()
                && deliveryTask.getRoutes().stream().anyMatch(route -> route.getType() == transport.getType());
    }

    private static double calcFinalDeliveryCost(Transport transport, DeliveryTask deliveryTask) {
        return transport.getPrice() * deliveryTask.getRoutes()
                .stream().filter(route -> route.getType() == transport.getType()).findFirst()
                .get().getLength();
    }
}
