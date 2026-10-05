package ru.hse.vyshkat.service;

import com.google.inject.Inject;
import ru.hse.vyshkat.domain.ServiceCenter;
import ru.hse.vyshkat.domain.Transport;

import java.util.ArrayList;
import java.util.List;

public class FleetService {
    private final ServiceCenter serviceCenter;
    private final InventoryService inventoryService;
    private final List<Transport> transports = new ArrayList<>();

    @Inject
    public FleetService(ServiceCenter serviceCenter, InventoryService inventoryService) {
        this.serviceCenter = serviceCenter;
        this.inventoryService = inventoryService;
    }

    public boolean addTransport(Transport transport) {
        if (!serviceCenter.inspect(transport)) {
            return false;
        }

        transports.add(transport);
        inventoryService.addItem(transport);
        return true;
    }

    public int getTransportCount() {
        return transports.size();
    }

    public List<Transport> getBeginnerSuitableTransports() {
        List<Transport> suitableTransports = new ArrayList<>();

        for (Transport transport : transports) {
            if (transport.isSuitableForBeginner()) {
                suitableTransports.add(transport);
            }
        }

        return suitableTransports;
    }
}