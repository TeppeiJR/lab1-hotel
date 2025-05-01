package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingTest {
    //Правильність обчислення суми бронювання
    @Test
    void testCalculateBookingDetails() {
        Room room = new Room(1, "Люкс", 1000.0);
        Visitor visitor = new Visitor("Іван", "Петренко", 30, "+380991112233");
        Booking booking = new Booking(room, visitor, 3, LocalDate.of(2025, 7, 1));

        booking.calculateBookingDetails();

        double result = 1000.0 * 3;
        double actual = booking.getTotalPrice();

        assertEquals(result, actual);
    }
}
