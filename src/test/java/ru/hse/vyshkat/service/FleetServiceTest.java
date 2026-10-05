package ru.hse.vyshkat.service;

import org.junit.jupiter.api.Test;
import ru.hse.vyshkat.domain.Bicycle;
import ru.hse.vyshkat.domain.ElectricBike;
import ru.hse.vyshkat.domain.ElectricScooter;
import ru.hse.vyshkat.domain.ServiceCenter;
import ru.hse.vyshkat.domain.Transport;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FleetServiceTest {

    @Test
    void shouldAddTransportWhenInspectionPasses() {
        ServiceCenter serviceCenter = transport -> true;
        InventoryService inventoryService = new InventoryService();
        FleetService fleetService = new FleetService(serviceCenter, inventoryService);
        Transport transport = new ElectricScooter("Самокат", "S-001", 1.5, 8);

        boolean result = fleetService.addTransport(transport);

        assertTrue(result);
        assertEquals(1, fleetService.getTransportCount());
    }

    @Test
    void shouldAddAcceptedTransportToInventory() {
        ServiceCenter serviceCenter = transport -> true;
        InventoryService inventoryService = new InventoryService();
        FleetService fleetService = new FleetService(serviceCenter, inventoryService);
        Transport transport = new ElectricBike("Электровелосипед", "EB-001", 2.0, 7);

        fleetService.addTransport(transport);

        assertEquals(1, inventoryService.getItems().size());
        assertEquals("EB-001", inventoryService.getItems().get(0).getInventoryNumber());
    }

    @Test
    void shouldRejectTransportWhenInspectionFails() {
        ServiceCenter serviceCenter = transport -> false;
        InventoryService inventoryService = new InventoryService();
        FleetService fleetService = new FleetService(serviceCenter, inventoryService);
        Transport transport = new ElectricScooter("Самокат", "S-002", 1.5, 8);

        boolean result = fleetService.addTransport(transport);

        assertFalse(result);
        assertEquals(0, fleetService.getTransportCount());
        assertTrue(inventoryService.getItems().isEmpty());
    }

    @Test
    void shouldReturnOnlyTransportsSuitableForBeginners() {
        ServiceCenter serviceCenter = transport -> true;
        InventoryService inventoryService = new InventoryService();
        FleetService fleetService = new FleetService(serviceCenter, inventoryService);

        Transport suitableScooter = new ElectricScooter("Простой самокат", "S-003", 1.5, 6);
        Transport suitableBicycle = new Bicycle("Простой велосипед", "B-001", 9);
        Transport unsuitableBike = new ElectricBike("Сложный электровелосипед", "EB-002", 2.0, 5);

        fleetService.addTransport(suitableScooter);
        fleetService.addTransport(suitableBicycle);
        fleetService.addTransport(unsuitableBike);

        List<Transport> result = fleetService.getBeginnerSuitableTransports();

        assertEquals(2, result.size());
        assertTrue(result.contains(suitableScooter));
        assertTrue(result.contains(suitableBicycle));
        assertFalse(result.contains(unsuitableBike));
    }

    @Test
    void shouldTreatBeginnerEaseSixAsSuitable() {
        ServiceCenter serviceCenter = transport -> true;
        InventoryService inventoryService = new InventoryService();
        FleetService fleetService = new FleetService(serviceCenter, inventoryService);
        Transport transport = new ElectricScooter("Самокат", "S-004", 1.5, 6);

        fleetService.addTransport(transport);

        assertEquals(1, fleetService.getBeginnerSuitableTransports().size());
    }

    @Test
    void shouldCountAllAcceptedTransportTypes() {
        ServiceCenter serviceCenter = transport -> true;
        InventoryService inventoryService = new InventoryService();
        FleetService fleetService = new FleetService(serviceCenter, inventoryService);

        fleetService.addTransport(new ElectricScooter("Самокат", "S-005", 1.5, 8));
        fleetService.addTransport(new ElectricBike("Электровелосипед", "EB-003", 2.0, 7));
        fleetService.addTransport(new Bicycle("Велосипед", "B-002", 9));

        assertEquals(3, fleetService.getTransportCount());
    }
}