package ru.hse.vyshkat.domain;

public abstract class Thing implements InventoryItem {
    private final String name;
    private final String inventoryNumber;

    protected Thing(String name, String inventoryNumber) {
        if (inventoryNumber == null || inventoryNumber.isBlank()) {
            throw new IllegalArgumentException("Inventory number must not be empty");
        }

        this.name = name;
        this.inventoryNumber = inventoryNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInventoryNumber() {
        return inventoryNumber;
    }
}