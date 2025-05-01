package org.example;


public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("У Романа", "м. Київ, вул. Хрещатик 1");
        HotelConsoleMenu menu = new HotelConsoleMenu(hotel);
        menu.start();
    }
}
