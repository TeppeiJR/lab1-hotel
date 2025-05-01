package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Visitor {
    private String nameVisitor;
    private String surnameVisitor;
    private int age;
    private String phoneNumber;
    @JsonManagedReference
    private final List<Booking> bookings = new ArrayList<>();

    public Visitor() {
    }

    public Visitor(String nameVisitor, String surnameVisitor, int age, String phoneNumber) {
        setNameVisitor(nameVisitor);
        setSurnameVisitor(surnameVisitor);
        setAge(age);
        setPhoneNumber(phoneNumber);
    }


    public String getNameVisitor() {
        return nameVisitor;
    }

    public void setNameVisitor(String nameVisitor) {
        if (nameVisitor == null || nameVisitor.isBlank()) {
            throw new IllegalArgumentException("Ім'я не може бути порожнім або складатися тільки з пробільних символів.");
        }
        this.nameVisitor = nameVisitor;
    }

    public String getSurnameVisitor() {
        return surnameVisitor;
    }

    public void setSurnameVisitor(String surnameVisitor) {
        if (surnameVisitor == null || surnameVisitor.isBlank()) {
            throw new IllegalArgumentException("Прізвище не може бути порожнім або складатися тільки з пробільних символів.");
        }
        this.surnameVisitor = surnameVisitor;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 18 && age <= 150) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Вік людини повинен бути від 18 до 150 років.");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Номер телефону не може бути порожнім.");
        }
        if (!phoneNumber.matches("\\+?[0-9]{10,15}")) {
            throw new IllegalArgumentException("Невірний формат номера телефону.");
        }

        this.phoneNumber = phoneNumber;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return Objects.equals(phoneNumber, visitor.phoneNumber);

    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    @Override
    public String toString() {
        return "Відвідувач (Ім'я: " + nameVisitor + "; прізвище: " + surnameVisitor + "; вік: " + age + "; номер телефону: " + phoneNumber + ")";
    }
}
