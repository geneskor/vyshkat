package ru.hse.vyshkat;

import org.junit.jupiter.api.Test;
import ru.hse.vyshkat.domain.ServiceCenter;
import ru.hse.vyshkat.service.FleetService;
import ru.hse.vyshkat.service.InventoryService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    @Test
    void shouldProcessInteractiveCommands() {
        String input = String.join("\n",
                "1", "1", "Электросамокат", "S-001", "8", "1.5",
                "1", "3", "Велосипед", "B-001", "9",
                "2", "1", "Шлем", "H-001",
                "2", "2", "Док-станция", "D-001", "2.5",
                "3", "4", "5", "6", "0"
        );

        ServiceCenter serviceCenter = transport -> true;
        InventoryService inventoryService = new InventoryService();
        FleetService fleetService = new FleetService(serviceCenter, inventoryService);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(input);

        ConsoleApplication application = new ConsoleApplication(fleetService, inventoryService, scanner, output);
        application.run();

        String result = buffer.toString(StandardCharsets.UTF_8);

        assertTrue(result.contains("Транспорт принят в парк."));
        assertTrue(result.contains("Инвентарь добавлен."));
        assertTrue(result.contains("Количество транспорта: 2"));
        assertTrue(result.contains("Суммарное суточное энергопотребление: 4.0 кВт·ч"));
        assertTrue(result.contains("Электросамокат [S-001]"));
        assertTrue(result.contains("Велосипед [B-001]"));
        assertTrue(result.contains("Шлем [H-001]"));
        assertTrue(result.contains("Док-станция [D-001]"));
        assertTrue(result.contains("Завершение работы."));
    }

    @Test
    void shouldRejectTransportWhenInspectionFails() {
        String input = String.join("\n",
                "1", "1", "Неисправный самокат", "S-002", "7", "0",
                "3", "6", "0"
        );

        ServiceCenter serviceCenter = transport -> false;
        InventoryService inventoryService = new InventoryService();
        FleetService fleetService = new FleetService(serviceCenter, inventoryService);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(input);

        ConsoleApplication application = new ConsoleApplication(fleetService, inventoryService, scanner, output);
        application.run();

        String result = buffer.toString(StandardCharsets.UTF_8);

        assertTrue(result.contains("Транспорт отклонён сервисным центром."));
        assertTrue(result.contains("Количество транспорта: 0"));
        assertFalse(result.contains("Неисправный самокат [S-002]"));
    }
}