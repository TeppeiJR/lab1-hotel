package org.example;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Objects;
import java.time.LocalDate;

public class Booking {
    private Room room;
    @JsonBackReference
    private Visitor visitor;
    private int nights;
    private double totalPrice;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;


    public Booking(Room room, Visitor visitor, int nights, LocalDate checkInDate) {
        setRoom(room);
        setVisitor(visitor);
        setNights(nights);
        setCheckInDate(checkInDate);
        calculateBookingDetails();
    }

    public Booking() {

    }

    // Обчислення загальної вартості
    public void calculateBookingDetails() {
        if (checkInDate != null && nights > 0) {
            checkOutDate = checkInDate.plusDays(nights);
            totalPrice = room != null ? room.getPricePerNight() * nights : 0;
        }
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        if (nights <= 0) {
            throw new IllegalArgumentException("Кількість ночей не може бути від'ємною.");
        }
        this.nights = nights;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        if (checkInDate == null) {
            throw new IllegalArgumentException("Дата заселення не може бути порожньою.");
        }
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(room, booking.room) &&
                Objects.equals(visitor, booking.visitor) &&
                nights == booking.nights &&
                Objects.equals(checkInDate, booking.checkInDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, visitor, nights, checkInDate);
    }

    @Override
    public String toString() {
        return "Бронювання (кімната: " + room + "; відвідувач: " + visitor + "; кількість ночей: " + nights +
                "; загальна вартість: " + totalPrice + "; дата заселення: " + checkInDate +
                "; дата виїзду: " + checkOutDate + " )";
    }
}
