package ru.hse.vyshkat;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    @Test
    void shouldDemonstrateApplicationScenarios() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Main.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString(StandardCharsets.UTF_8);

        assertTrue(result.contains("Электросамокат [S-001] — принят"));
        assertTrue(result.contains("Электровелосипед [EB-001] — принят"));
        assertTrue(result.contains("Велосипед [B-001] — принят"));
        assertTrue(result.contains("Неисправный электросамокат [S-002] — отклонён"));
        assertTrue(result.contains("Количество транспорта: 3"));
        assertTrue(result.contains("Суммарное суточное энергопотребление: 9.0 кВт·ч"));
        assertTrue(result.contains("Электросамокат [S-001]"));
        assertTrue(result.contains("Велосипед [B-001]"));
        assertTrue(result.contains("Шлем [H-001]"));
        assertTrue(result.contains("Док-станция [D-001]"));
        assertTrue(result.contains("Зарядный шкаф [C-001]"));
        assertFalse(result.contains("Неисправный электросамокат [S-002]\nШлем"));
    }
}