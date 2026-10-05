package ru.hse.vyshkat.domain;

public abstract class Transport implements InventoryItem {
    private final String name;
    private final String inventoryNumber;
    private final int beginnerEase;

    protected Transport(String name, String inventoryNumber, int beginnerEase) {
        if (inventoryNumber == null || inventoryNumber.isBlank()) {
            throw new IllegalArgumentException("Inventory number must not be empty");
        }

        if (beginnerEase < 1 || beginnerEase > 10) {
            throw new IllegalArgumentException("Beginner ease must be between 1 and 10");
        }

        this.name = name;
        this.inventoryNumber = inventoryNumber;
        this.beginnerEase = beginnerEase;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public int getBeginnerEase() {
        return beginnerEase;
    }

    public boolean isSuitableForBeginner() {
        return beginnerEase >= 6;
    }
}