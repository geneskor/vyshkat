package ru.hse.vyshkat.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DomainModelTest {

    @Test
    void shouldStoreTransportData() {
        Transport scooter = new ElectricScooter("Электросамокат", "S-001", 1.5, 8);

        assertEquals("Электросамокат", scooter.getName());
        assertEquals("S-001", scooter.getInventoryNumber());
        assertEquals(8, scooter.getBeginnerEase());
    }

    @Test
    void shouldDetermineBeginnerSuitability() {
        Transport suitable = new Bicycle("Велосипед", "B-001", 6);
        Transport unsuitable = new Bicycle("Велосипед", "B-002", 5);

        assertTrue(suitable.isSuitableForBeginner());
        assertFalse(unsuitable.isSuitableForBeginner());
    }

    @Test
    void shouldStoreThingData() {
        Thing helmet = new Helmet("Шлем", "H-001");

        assertEquals("Шлем", helmet.getName());
        assertEquals("H-001", helmet.getInventoryNumber());
    }

    @Test
    void shouldStoreElectricScooterEnergyConsumption() {
        EnergyConsumer scooter = new ElectricScooter("Самокат", "S-001", 1.5, 8);

        assertEquals(1.5, scooter.getDailyEnergyConsumption(), 0.0001);
    }

    @Test
    void shouldStoreElectricBikeEnergyConsumption() {
        EnergyConsumer bike = new ElectricBike("Электровелосипед", "EB-001", 2.0, 7);

        assertEquals(2.0, bike.getDailyEnergyConsumption(), 0.0001);
    }

    @Test
    void shouldStoreDockingStationEnergyConsumption() {
        EnergyConsumer station = new DockingStation("Док-станция", "D-001", 2.5);

        assertEquals(2.5, station.getDailyEnergyConsumption(), 0.0001);
    }

    @Test
    void shouldStoreChargingCabinetEnergyConsumption() {
        EnergyConsumer cabinet = new ChargingCabinet("Зарядный шкаф", "C-001", 3.0);

        assertEquals(3.0, cabinet.getDailyEnergyConsumption(), 0.0001);
    }
}