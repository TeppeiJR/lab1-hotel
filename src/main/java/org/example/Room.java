package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {
    private int idRoom;
    private String typeRoom;
    private double pricePerNight;
    private final HashSet<LocalDate> bookDates = new HashSet<>();

    public Room() {
    }

    public Room(int idRoom, String typeRoom, double pricePerNight) {
        setIdRoom(idRoom);
        setTypeRoom(typeRoom);
        setPricePerNight(pricePerNight);
    }


    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        if (idRoom >= 0) {
            this.idRoom = idRoom;
        } else {
            throw new IllegalArgumentException("Номер кімнати не може бути від'ємним.");
        }
    }

    public String getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(String typeRoom) {
        if (typeRoom == null || typeRoom.isBlank()) {
            throw new IllegalArgumentException("Тип кімнати не може бути порожнім або складатися тільки з пробільних символів.");
        }
        this.typeRoom = typeRoom;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        if (pricePerNight >= 0) {
            this.pricePerNight = pricePerNight;
        } else {
            throw new IllegalArgumentException("Ціна за добу не може бути від'ємною.");
        }
    }

    public HashSet<LocalDate> getBookDates() {
        return bookDates;
    }

    public boolean isBooked() {

        return !bookDates.isEmpty();
    }

    // Перевірка, чи вільна кімната на вказані дати
    public boolean isAvailable(LocalDate checkIn, int nights) {
        for (int i = 0; i < nights; i++) {
            if (bookDates.contains(checkIn.plusDays(i))) {
                return false;
            }
        }
        return true;
    }

    public void reserveDates(LocalDate checkIn, int nights) {
        for (int i = 0; i < nights; i++) {
            bookDates.add(checkIn.plusDays(i));
        }
    }

    public void cancelDates(LocalDate checkIn, int nights) {
        for (int i = 0; i < nights; i++) {
            bookDates.remove(checkIn.plusDays(i));
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return idRoom == room.idRoom;
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(idRoom);
    }

    @Override
    public String toString() {
        return "Кімната (номер кімнати: " + idRoom + "; тип кімнати: " + typeRoom + "; ціна за добу: " + pricePerNight + ")";
    }

}
