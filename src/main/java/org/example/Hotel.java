package org.example;

import java.time.LocalDate;
import java.util.*;

public class Hotel {
    private String nameHotel;
    private String address;
    private final Map<Integer, Room> rooms = new HashMap<>();
    private final Map<String, Visitor> visitors = new HashMap<>();
    private final List<Booking> bookings = new ArrayList<>();
    private static final int TOTAL_ROOMS = 200;
    private static int roomBook = 0;

    public Hotel(String nameHotel, String address) {
        setNameHotel(nameHotel);
        setAddress(address);
    }

    public Booking bookRoom(int roomId, String visitorPhone, int nights, LocalDate checkInDate) {
        Room room = rooms.get(roomId);
        Visitor visitor = visitors.get(visitorPhone);

        if (roomBook >= TOTAL_ROOMS) {
            throw new IllegalStateException("Усі кімнати зайняті. Немає доступних номерів.");
        }
        if (room == null) {
            throw new IllegalArgumentException("Кімната не знайдена");
        }
        if (visitor == null) {
            throw new IllegalArgumentException("Відвідувач не знайдений");
        }
        // Перевірка наявності кімнати на вказані дати
        if (!room.isAvailable(checkInDate, nights)) {
            throw new IllegalStateException("Кімната вже заброньована на ці дати");
        }


        Booking booking = new Booking(room, visitor, nights, checkInDate);
        bookings.add(booking);
        visitor.addBooking(booking);
        roomBook++;
        room.reserveDates(checkInDate, nights);
        return booking;
    }

    private Booking findBooking(Room room, Visitor visitor, LocalDate checkInDate) {
        for (Booking booking : bookings) {
            if (booking.getRoom().equals(room) && booking.getVisitor().equals(visitor) &&
                    booking.getCheckInDate().equals(checkInDate)) {
                return booking;
            }
        }
        return null;
    }

    public boolean cancelBooking(int roomId, String visitorPhone, LocalDate checkInDate) {
        Room room = rooms.get(roomId);
        Visitor visitor = visitors.get(visitorPhone);

        if (room == null || visitor == null) {
            throw new IllegalArgumentException("Не знайдено кімнату або відвідувача");
        }

        Booking bookingToCancel = findBooking(room, visitor, checkInDate);

        if (bookingToCancel == null) {
            return false;  // Логіка: бронювання не знайдено
        }

        room.cancelDates(checkInDate, bookingToCancel.getNights());
        bookings.remove(bookingToCancel);
        roomBook--;
        return true;
    }

    // CRUD-операції з кімнатами
    public void addRoom(Room room) {
        if (rooms.size() >= TOTAL_ROOMS) {
            throw new IllegalStateException("Максимальна кількість кімнат: " + TOTAL_ROOMS);
        }
        if (rooms.containsKey(room.getIdRoom())) {
            throw new IllegalArgumentException("Кімната з таким ID вже існує.");
        }
        rooms.put(room.getIdRoom(), room);
    }

    public Room readRoom(int roomID) {
        if (!rooms.containsKey(roomID)) {
            throw new IllegalArgumentException("Кімнати з таким ID не знайдено.");
        }
        return rooms.get(roomID);
    }

    public void updateRoom(Room updatedRoom) {
        if (!rooms.containsKey(updatedRoom.getIdRoom())) {
            throw new IllegalArgumentException("Кімната не знайдена.");
        }
        rooms.put(updatedRoom.getIdRoom(), updatedRoom);
    }

    public void deleteRoom(int roomID) {
        if (!rooms.containsKey(roomID)) {
            throw new IllegalArgumentException("Кімнати з таким ID не існує.");
        }
        rooms.remove(roomID);
    }

    // CRUD-операції з відвідувачами
    public void addVisitor(Visitor visitor) {
        if (visitors.containsKey(visitor.getPhoneNumber())) {
            throw new IllegalArgumentException("Відвідувач з таким номером телефона вже існує.");
        }
        visitors.put(visitor.getPhoneNumber(), visitor);
    }

    public Visitor readVisitor(String visitorPhone) {
        if (!visitors.containsKey(visitorPhone)) {
            throw new IllegalArgumentException("Відвідувача з таким номером телефона не знайдено.");
        }
        return visitors.get(visitorPhone);
    }

    public void updateVisitor(Visitor updatedVisitor) {
        if (!visitors.containsKey(updatedVisitor.getPhoneNumber())) {
            throw new IllegalArgumentException("Відвідувач не знайдений.");
        }
        visitors.put(updatedVisitor.getPhoneNumber(), updatedVisitor);
    }

    public void deleteVisitor(String visitorPhone) {
        if (!visitors.containsKey(visitorPhone)) {
            throw new IllegalArgumentException("Відвідувача з таким номером телефона не існує.");
        }
        visitors.remove(visitorPhone);
    }

    // Метод для підрахунку кількості активних бронювань
    public int countCurrentlyBookedRooms() {
        int count = 0;
        for (Room room : rooms.values()) {
            if (room.isBooked()) {
                count++;
            }
        }
        return count;
    }


    // Геттер для кімнат
    public Map<Integer, Room> getAllRooms() {
        return rooms;
    }

    // Геттер для відвідувачів
    public Map<String, Visitor> getAllVisitors() {
        return visitors;
    }

    public List<Booking> getAllBookings() {
        return bookings;  // Повертаємо всі бронювання
    }

    public static int getTotalRooms() {
        return TOTAL_ROOMS;
    }

    // Метод для отримання кількості вільних номерів
    public int getAvailableRoomsCount() {
        return TOTAL_ROOMS - countCurrentlyBookedRooms();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Адреса готелю не може бути порожньою або складатися тільки з пробільних символів.");
        }
        this.address = address;
    }

    public String getNameHotel() {
        return nameHotel;
    }

    public void setNameHotel(String nameHotel) {
        if (nameHotel == null || nameHotel.isBlank()) {
            throw new IllegalArgumentException("Назва готелю не може бути порожньою або складатися тільки з пробільних символів.");
        }
        this.nameHotel = nameHotel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(nameHotel, hotel.nameHotel) && Objects.equals(address, hotel.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nameHotel, address);
    }

    @Override
    public String toString() {
        return "Готель (назва: " + nameHotel + "; адреса: " + address + ")";
    }
}
