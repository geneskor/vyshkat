package ru.hse.vyshkat.domain;

public class DockingStation extends Thing implements EnergyConsumer {
    private final double dailyEnergyConsumption;

    public DockingStation(String name, String inventoryNumber, double dailyEnergyConsumption) {
        super(name, inventoryNumber);
        this.dailyEnergyConsumption = dailyEnergyConsumption;
    }

    @Override
    public double getDailyEnergyConsumption() {
        return dailyEnergyConsumption;
    }
}