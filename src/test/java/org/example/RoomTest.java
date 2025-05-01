package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    private Room room;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        room = new Room(1, "Стандарт", 500.0);
        date = LocalDate.of(2025, 4, 20);
    }

    // Тест перевірки доступності кімнати
    @Test
    void testIsAvailable() {
        assertTrue(room.isAvailable(date, 2));
        room.reserveDates(date, 2);
        assertFalse(room.isAvailable(date, 2));
    }

    // Тест бронювання дати
    @Test
    void testReserveDates() {
        room.reserveDates(date, 3);
        assertFalse(room.isAvailable(date, 3));
    }

    // Тест скасування бронювання
    @Test
    void testCancelDates() {
        room.reserveDates(date, 2);
        room.cancelDates(date, 2);
        assertTrue(room.isAvailable(date, 2));
    }
}
