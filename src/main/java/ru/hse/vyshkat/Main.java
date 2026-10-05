package ru.hse.vyshkat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ru.hse.vyshkat.config.AppModule;
import ru.hse.vyshkat.domain.Bicycle;
import ru.hse.vyshkat.domain.ChargingCabinet;
import ru.hse.vyshkat.domain.DockingStation;
import ru.hse.vyshkat.domain.ElectricBike;
import ru.hse.vyshkat.domain.ElectricScooter;
import ru.hse.vyshkat.domain.Helmet;
import ru.hse.vyshkat.domain.InventoryItem;
import ru.hse.vyshkat.domain.Transport;
import ru.hse.vyshkat.service.FleetService;
import ru.hse.vyshkat.service.InventoryService;

public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());

        FleetService fleetService = injector.getInstance(FleetService.class);
        InventoryService inventoryService = injector.getInstance(InventoryService.class);

        Transport scooter = new ElectricScooter("Электросамокат", "S-001", 1.5, 8);
        Transport electricBike = new ElectricBike("Электровелосипед", "EB-001", 2.0, 5);
        Transport bicycle = new Bicycle("Велосипед", "B-001", 9);
        Transport brokenScooter = new ElectricScooter("Неисправный электросамокат", "S-002", 0.0, 7);

        System.out.println("=== Приём транспорта ===");
        printAcceptanceResult(scooter, fleetService.addTransport(scooter));
        printAcceptanceResult(electricBike, fleetService.addTransport(electricBike));
        printAcceptanceResult(bicycle, fleetService.addTransport(bicycle));
        printAcceptanceResult(brokenScooter, fleetService.addTransport(brokenScooter));

        inventoryService.addItem(new Helmet("Шлем", "H-001"));
        inventoryService.addItem(new DockingStation("Док-станция", "D-001", 2.5));
        inventoryService.addItem(new ChargingCabinet("Зарядный шкаф", "C-001", 3.0));

        System.out.println("\n=== Отчёт по парку ===");
        System.out.println("Количество транспорта: " + fleetService.getTransportCount());
        System.out.println("Суммарное суточное энергопотребление: " + inventoryService.getTotalDailyEnergyConsumption() + " кВт·ч");

        System.out.println("\n=== Техника для новичков ===");
        for (Transport transport : fleetService.getBeginnerSuitableTransports()) {
            System.out.println(transport.getName() + " [" + transport.getInventoryNumber() + "]");
        }

        System.out.println("\n=== Весь инвентарь ===");
        for (InventoryItem item : inventoryService.getItems()) {
            System.out.println(item.getName() + " [" + item.getInventoryNumber() + "]");
        }
    }

    private static void printAcceptanceResult(Transport transport, boolean accepted) {
        String result = accepted ? "принят" : "отклонён";
        System.out.println(transport.getName() + " [" + transport.getInventoryNumber() + "] — " + result);
    }
}