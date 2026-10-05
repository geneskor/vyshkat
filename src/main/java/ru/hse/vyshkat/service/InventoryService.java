package ru.hse.vyshkat.service;

import ru.hse.vyshkat.domain.EnergyConsumer;
import ru.hse.vyshkat.domain.InventoryItem;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private final List<InventoryItem> items = new ArrayList<>();

    public void addItem(InventoryItem item) {
        items.add(item);
    }

    public List<InventoryItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getTotalDailyEnergyConsumption() {
        double total = 0;

        for (InventoryItem item : items) {
            if (item instanceof EnergyConsumer consumer) {
                total += consumer.getDailyEnergyConsumption();
            }
        }

        return total;
    }
}