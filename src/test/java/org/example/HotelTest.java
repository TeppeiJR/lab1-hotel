package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class HotelTest {
    private Hotel hotel;
    private Room room;
    private Visitor visitor;

    @BeforeEach
    void setUp() {
        hotel = new Hotel("Test Hotel", "Test Address");
        room = new Room(1, "Standard", 300.0);
        visitor = new Visitor("Оля", "Коваль", 25, "+380671234567");

        hotel.addRoom(room);
        hotel.addVisitor(visitor);
    }

    //Тест створення бронювання
    @Test
    void testBookRoom() {
        Booking booking = hotel.bookRoom(1, "+380671234567", 2, LocalDate.of(2025, 4, 20));
        assertNotNull(booking);
        assertEquals(1, hotel.getAllBookings().size());
        assertEquals(room, booking.getRoom());
        assertEquals(visitor, booking.getVisitor());
        assertFalse(room.isAvailable(LocalDate.of(2025, 4, 20), 2));
    }

    //Тест скасування бронювання
    @Test
    void testCancelBooking() {
        LocalDate date = LocalDate.of(2025, 9, 1);
        hotel.bookRoom(1, "+380671234567", 2, date);
        boolean result = hotel.cancelBooking(1, "+380671234567", date);

        assertTrue(result);
        assertEquals(0, hotel.getAllBookings().size());
        assertTrue(room.isAvailable(date, 2));
    }

    // Тест скасування неіснуючого бронювання
    @Test
    void testCancelNonExistingBooking() {
        boolean result = hotel.cancelBooking(1, "+380671234567", LocalDate.of(2025, 7, 1));
        assertFalse(result);
    }

    // Тест помилки при бронюванні неіснуючої кімнати
    @Test
    void testBookingNonExistingRoomThrowsException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                hotel.bookRoom(999, "+380671234567", 2, LocalDate.of(2025, 4, 20))
        );
        assertEquals("Кімната не знайдена", ex.getMessage());
    }

    // Тест для підрахунку заброньованих номерів
    @Test
    void testCountCurrentlyBookedRooms() {
        Room room1 = new Room(2, "Single", 200.0);
        Room room2 = new Room(3, "Double", 300.0);
        Room room3 = new Room(4, "Suite", 400.0);

        Visitor visitor1 = new Visitor("Іван", "Марченко", 25, "1234567890");
        Visitor visitor2 = new Visitor("Марія", "Цимбал", 45, "0987654321");


        hotel.addRoom(room1);
        hotel.addRoom(room2);
        hotel.addRoom(room3);

        hotel.addVisitor(visitor1);
        hotel.addVisitor(visitor2);

        hotel.bookRoom(2, "1234567890", 2, LocalDate.now());
        hotel.bookRoom(3, "0987654321", 3, LocalDate.now().plusDays(1));

        int bookedCount = hotel.countCurrentlyBookedRooms();

        assertEquals(2, bookedCount);
    }

    // Тест, коли немає бронювань
    @Test
    void testCountBookedRoomsWhenNoRooms() {
        int bookedCount = hotel.countCurrentlyBookedRooms();
        assertEquals(0, bookedCount);
    }

    // Тест помилки при накладанні дат бронювання
    @Test
    void testBookingOverlappingDates() {
        hotel.bookRoom(1, "+380671234567", 3, LocalDate.of(2025, 4, 20));

        Exception ex = assertThrows(IllegalStateException.class, () ->
                hotel.bookRoom(1, "+380671234567", 2, LocalDate.of(2025, 4, 22)));
        assertEquals("Кімната вже заброньована на ці дати", ex.getMessage());
    }
}
