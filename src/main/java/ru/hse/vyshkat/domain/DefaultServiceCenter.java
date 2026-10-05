package ru.hse.vyshkat.domain;

public class DefaultServiceCenter implements ServiceCenter {

    @Override
    public boolean inspect(Transport transport) {
        if (transport == null) {
            return false;
        }

        if (transport instanceof EnergyConsumer consumer) {
            return consumer.getDailyEnergyConsumption() > 0;
        }

        return true;
    }
}