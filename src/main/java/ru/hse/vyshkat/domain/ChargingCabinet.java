package ru.hse.vyshkat.domain;

public class ChargingCabinet extends Thing implements EnergyConsumer {
    private final double dailyEnergyConsumption;

    public ChargingCabinet(String name, String inventoryNumber, double dailyEnergyConsumption) {
        super(name, inventoryNumber);
        this.dailyEnergyConsumption = dailyEnergyConsumption;
    }

    @Override
    public double getDailyEnergyConsumption() {
        return dailyEnergyConsumption;
    }
}