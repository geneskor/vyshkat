package ru.hse.vyshkat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ru.hse.vyshkat.config.AppModule;
import ru.hse.vyshkat.service.FleetService;
import ru.hse.vyshkat.service.InventoryService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());

        FleetService fleetService = injector.getInstance(FleetService.class);
        InventoryService inventoryService = injector.getInstance(InventoryService.class);

        ConsoleApplication application = new ConsoleApplication(
                fleetService,
                inventoryService,
                new Scanner(System.in),
                System.out
        );

        application.run();
    }
}