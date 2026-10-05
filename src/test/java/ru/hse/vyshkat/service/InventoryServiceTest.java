package ru.hse.vyshkat.service;

import org.junit.jupiter.api.Test;
import ru.hse.vyshkat.domain.Bicycle;
import ru.hse.vyshkat.domain.ChargingCabinet;
import ru.hse.vyshkat.domain.DockingStation;
import ru.hse.vyshkat.domain.ElectricScooter;
import ru.hse.vyshkat.domain.Helmet;
import ru.hse.vyshkat.domain.InventoryItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryServiceTest {

    @Test
    void shouldAddInventoryItems() {
        InventoryService inventoryService = new InventoryService();

        inventoryService.addItem(new Helmet("Шлем", "H-001"));
        inventoryService.addItem(new ElectricScooter("Самокат", "S-001", 1.5, 8));

        assertEquals(2, inventoryService.getItems().size());
    }

    @Test
    void shouldStoreDifferentInventoryItemTypes() {
        InventoryService inventoryService = new InventoryService();

        inventoryService.addItem(new Helmet("Шлем", "H-001"));
        inventoryService.addItem(new DockingStation("Док-станция", "D-001", 2.0));
        inventoryService.addItem(new Bicycle("Велосипед", "B-001", 7));

        List<InventoryItem> items = inventoryService.getItems();

        assertEquals(3, items.size());
        assertEquals("H-001", items.get(0).getInventoryNumber());
        assertEquals("D-001", items.get(1).getInventoryNumber());
        assertEquals("B-001", items.get(2).getInventoryNumber());
    }

    @Test
    void shouldCalculateEnergyOnlyForEnergyConsumers() {
        InventoryService inventoryService = new InventoryService();

        inventoryService.addItem(new ElectricScooter("Самокат", "S-001", 1.5, 8));
        inventoryService.addItem(new DockingStation("Док-станция", "D-001", 2.0));
        inventoryService.addItem(new ChargingCabinet("Зарядный шкаф", "C-001", 3.0));
        inventoryService.addItem(new Bicycle("Велосипед", "B-001", 7));
        inventoryService.addItem(new Helmet("Шлем", "H-001"));

        assertEquals(6.5, inventoryService.getTotalDailyEnergyConsumption(), 0.0001);
    }

    @Test
    void shouldReturnZeroEnergyWhenThereAreNoEnergyConsumers() {
        InventoryService inventoryService = new InventoryService();

        inventoryService.addItem(new Helmet("Шлем", "H-001"));
        inventoryService.addItem(new Bicycle("Велосипед", "B-001", 7));

        assertEquals(0.0, inventoryService.getTotalDailyEnergyConsumption(), 0.0001);
    }

    @Test
    void shouldReturnCopyOfInventoryList() {
        InventoryService inventoryService = new InventoryService();
        inventoryService.addItem(new Helmet("Шлем", "H-001"));

        List<InventoryItem> items = inventoryService.getItems();
        items.clear();

        assertEquals(1, inventoryService.getItems().size());
        assertTrue(items.isEmpty());
    }
}