package ru.hse.vyshkat;

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

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleApplication {
    private final FleetService fleetService;
    private final InventoryService inventoryService;
    private final Scanner scanner;
    private final PrintStream output;

    public ConsoleApplication(FleetService fleetService, InventoryService inventoryService, Scanner scanner, PrintStream output) {
        this.fleetService = fleetService;
        this.inventoryService = inventoryService;
        this.scanner = scanner;
        this.output = output;
    }

    public void run() {
        while (true) {
            printMenu();

            String choice = readLine("Выберите действие: ");

            if (choice == null) {
                return;
            }

            try {
                switch (choice) {
                    case "1" -> addTransport();
                    case "2" -> addInventory();
                    case "3" -> output.println("Количество транспорта: " + fleetService.getTransportCount());
                    case "4" -> output.println("Суммарное суточное энергопотребление: " + inventoryService.getTotalDailyEnergyConsumption() + " кВт·ч");
                    case "5" -> showBeginnerTransports();
                    case "6" -> showInventory();
                    case "0" -> {
                        output.println("Завершение работы.");
                        return;
                    }
                    default -> output.println("Неизвестная команда.");
                }
            } catch (IllegalArgumentException exception) {
                output.println("Ошибка: " + exception.getMessage());
            }

            output.println();
        }
    }

    private void printMenu() {
        output.println("========== ВЫШКАТ ==========");
        output.println("1. Добавить транспорт");
        output.println("2. Добавить инвентарь");
        output.println("3. Показать количество транспорта");
        output.println("4. Показать суммарное энергопотребление");
        output.println("5. Показать транспорт для новичков");
        output.println("6. Показать весь инвентарь");
        output.println("0. Выход");
    }

    private void addTransport() {
        output.println("1. Электросамокат");
        output.println("2. Электровелосипед");
        output.println("3. Велосипед");

        String type = readRequiredLine("Тип транспорта: ");

        if (!type.equals("1") && !type.equals("2") && !type.equals("3")) {
            output.println("Неизвестный тип транспорта.");
            return;
        }

        String name = readRequiredLine("Название: ");
        String inventoryNumber = readRequiredLine("Инвентарный номер: ");
        int beginnerEase = readInt("Удобство для новичков (1–10): ");

        Transport transport;

        switch (type) {
            case "1" -> {
                double energy = readDouble("Энергопотребление (кВт·ч/сутки): ");
                transport = new ElectricScooter(name, inventoryNumber, energy, beginnerEase);
            }
            case "2" -> {
                double energy = readDouble("Энергопотребление (кВт·ч/сутки): ");
                transport = new ElectricBike(name, inventoryNumber, energy, beginnerEase);
            }
            default -> transport = new Bicycle(name, inventoryNumber, beginnerEase);
        }

        boolean accepted = fleetService.addTransport(transport);

        if (accepted) {
            output.println("Транспорт принят в парк.");
        } else {
            output.println("Транспорт отклонён сервисным центром.");
        }
    }

    private void addInventory() {
        output.println("1. Шлем");
        output.println("2. Док-станция");
        output.println("3. Зарядный шкаф");

        String type = readRequiredLine("Тип инвентаря: ");

        if (!type.equals("1") && !type.equals("2") && !type.equals("3")) {
            output.println("Неизвестный тип инвентаря.");
            return;
        }

        String name = readRequiredLine("Название: ");
        String inventoryNumber = readRequiredLine("Инвентарный номер: ");

        InventoryItem item;

        switch (type) {
            case "1" -> item = new Helmet(name, inventoryNumber);
            case "2" -> {
                double energy = readDouble("Энергопотребление (кВт·ч/сутки): ");
                item = new DockingStation(name, inventoryNumber, energy);
            }
            default -> {
                double energy = readDouble("Энергопотребление (кВт·ч/сутки): ");
                item = new ChargingCabinet(name, inventoryNumber, energy);
            }
        }

        inventoryService.addItem(item);
        output.println("Инвентарь добавлен.");
    }

    private void showBeginnerTransports() {
        output.println("=== Транспорт для новичков ===");

        for (Transport transport : fleetService.getBeginnerSuitableTransports()) {
            output.println(transport.getName() + " [" + transport.getInventoryNumber() + "]");
        }
    }

    private void showInventory() {
        output.println("=== Весь инвентарь ===");

        for (InventoryItem item : inventoryService.getItems()) {
            output.println(item.getName() + " [" + item.getInventoryNumber() + "]");
        }
    }

    private String readLine(String prompt) {
        output.print(prompt);

        if (!scanner.hasNextLine()) {
            return null;
        }

        return scanner.nextLine().trim();
    }

    private String readRequiredLine(String prompt) {
        String value = readLine(prompt);

        if (value == null) {
            throw new IllegalArgumentException("Ввод завершён");
        }

        if (value.isBlank()) {
            throw new IllegalArgumentException("Поле не должно быть пустым");
        }

        return value;
    }

    private int readInt(String prompt) {
        String value = readRequiredLine(prompt);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Введите целое число");
        }
    }

    private double readDouble(String prompt) {
        String value = readRequiredLine(prompt);

        try {
            double result = Double.parseDouble(value.replace(',', '.'));

            if (!Double.isFinite(result) || result < 0) {
                throw new IllegalArgumentException("Энергопотребление должно быть неотрицательным конечным числом");
            }

            return result;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Введите число");
        }
    }
}