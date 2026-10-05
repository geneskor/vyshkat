package ru.hse.vyshkat.domain;

public class ElectricBike extends Transport implements EnergyConsumer {
    private final double dailyEnergyConsumption;

    public ElectricBike(String name, String inventoryNumber, double dailyEnergyConsumption, int beginnerEase) {
        super(name, inventoryNumber, beginnerEase);
        this.dailyEnergyConsumption = dailyEnergyConsumption;
    }

    @Override
    public double getDailyEnergyConsumption() {
        return dailyEnergyConsumption;
    }
}