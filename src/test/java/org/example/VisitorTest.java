package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitorTest {
    private Visitor visitor;
    private Room room;
    private Booking booking;

    @BeforeEach
    void setUp() {
        visitor = new Visitor("Олена", "Шевченко", 28, "+380671112233");
        room = new Room(2, "Двомісний номер", 150.99);
        booking = new Booking(room, visitor, 3, LocalDate.of(2025, 5, 1));
    }

    // Тест, що перевіряє додавання бронювання відвідувачу
    @Test
    public void testVisitorBookingList() {
        visitor.addBooking(booking);

        assertEquals(1, visitor.getBookings().size());
        assertEquals(room, visitor.getBookings().getFirst().getRoom());
    }
}
