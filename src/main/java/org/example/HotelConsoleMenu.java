package org.example;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class HotelConsoleMenu {
    private final Hotel hotel;
    private final Scanner scanner = new Scanner(System.in);

    public HotelConsoleMenu(Hotel hotel) {

        this.hotel = hotel;
    }

    public void start() {
        System.out.println("Вас вітає " + hotel);

        while (true) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Кімнати (CRUD)");
            System.out.println("2. Відвідувачі (CRUD)");
            System.out.println("3. Забронювати номер");
            System.out.println("4. Скасувати бронювання");
            System.out.println("5. Переглянути всі бронювання");
            System.out.println("6. Статистика заброньованих номерів");
            System.out.println("7. Імпорт / Експорт");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> roomMenu();
                case "2" -> visitorMenu();
                case "3" -> bookRoom();
                case "4" -> cancelBooking();
                case "5" -> showAllBookings();
                case "6" -> countBookedRooms();
                case "7" -> dataMenu();
                case "0" -> {
                    System.out.println("До побачення!");
                    return;
                }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private void roomMenu() {
        while (true) {
            System.out.println("\n--- КІМНАТИ ---");
            System.out.println("1. Додати кімнату");
            System.out.println("2. Прочитати кімнату");
            System.out.println("3. Оновити кімнату");
            System.out.println("4. Видалити кімнату");
            System.out.println("0. Назад");
            System.out.print("Ваш вибір: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addRoom();
                case "2" -> readRoom();
                case "3" -> updateRoom();
                case "4" -> deleteRoom();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private void visitorMenu() {
        while (true) {
            System.out.println("\n--- ВІДВІДУВАЧІ ---");
            System.out.println("1. Додати відвідувача");
            System.out.println("2. Прочитати відвідувача");
            System.out.println("3. Оновити відвідувача");
            System.out.println("4. Видалити відвідувача");
            System.out.println("0. Назад");
            System.out.print("Ваш вибір: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addVisitor();
                case "2" -> readVisitor();
                case "3" -> updateVisitor();
                case "4" -> deleteVisitor();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private void bookRoom() {
        try {
            System.out.print("ID кімнати: ");
            int roomId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Телефон відвідувача: ");
            String phone = scanner.nextLine();
            System.out.print("Кількість ночей: ");
            int nights = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Дата заселення (рік, місяць, день): ");
            int year = scanner.nextInt();
            int month = scanner.nextInt();
            int day = scanner.nextInt();
            scanner.nextLine();

            LocalDate checkIn = LocalDate.of(year, month, day);


            Booking booking = hotel.bookRoom(roomId, phone, nights, checkIn);
            System.out.println("Бронювання успішне!");
            System.out.println(booking);

        } catch (IllegalArgumentException a) {
            System.out.println("Помилка аргумента при бронюванні: " + a.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Помилка при бронюванні: " + e.getMessage());
        }
    }

    private void cancelBooking() {
        try {
            System.out.print("ID кімнати: ");
            int roomId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Телефон відвідувача: ");
            String phone = scanner.nextLine();
            System.out.print("Дата заселення (рік, місяць, день): ");
            int year = scanner.nextInt();
            int month = scanner.nextInt();
            int day = scanner.nextInt();
            scanner.nextLine();

            LocalDate checkIn = LocalDate.of(year, month, day);

            boolean cancel = hotel.cancelBooking(roomId, phone, checkIn);
            if (cancel) {
                System.out.println("Бронювання скасовано.");
            } else {
                System.out.println("Бронювання не знайдено.");
            }

        } catch (IllegalArgumentException a) {
            System.out.println("Помилка аргумента при скасуванні: " + a.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Помилка при скасуванні: " + e.getMessage());
        }
    }

    private void countBookedRooms() {
        int count = hotel.countCurrentlyBookedRooms();
        System.out.println("Зараз заброньовано номерів: " + count);
        System.out.println("Всього номерів: " + Hotel.getTotalRooms());
        System.out.println("Доступних номерів: " + hotel.getAvailableRoomsCount());
    }

    private void showAllBookings() {
        List<Booking> allBookings = hotel.getAllBookings();
        if (allBookings.isEmpty()) {
            System.out.println("Немає активних бронювань.");
        } else {
            for (Booking booking : allBookings) {
                System.out.println(booking);
            }
        }
    }

    private void dataMenu() {
        DataExporter exporter = new DataExporter();
        while (true) {
            System.out.println("\n--- ІМПОРТ / ЕКСПОРТ ---");
            System.out.println("1. Експортувати кімнати");
            System.out.println("2. Імпортувати кімнати");
            System.out.println("3. Експортувати відвідувачів");
            System.out.println("4. Імпортувати відвідувачів");
            System.out.println("0. Назад");
            System.out.print("Ваш вибір: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> {
                        List<Room> sortedRooms = new ArrayList<>(hotel.getAllRooms().values());
                        exporter.exportRooms(sortedRooms, new File("rooms.json"), Comparator.comparing(Room::getIdRoom));
                        System.out.println("Кімнати збережено у файл.");
                    }

                    case "2" -> {
                        List<Room> importedRooms = exporter.importRooms(new File("rooms.json"));
                        for (Room room : importedRooms) hotel.addRoom(room);
                        System.out.println("Кімнати імпортовано.");
                    }
                    case "3" -> {
                        List<Visitor> sortedVisitors = new ArrayList<>(hotel.getAllVisitors().values());
                        exporter.exportVisitors(sortedVisitors, new File("visitors.json"), Comparator.comparing(Visitor::getNameVisitor));
                        System.out.println("Відвідувачі збережено у файл.");
                    }
                    case "4" -> {
                        List<Visitor> importedVisitors = exporter.importVisitors(new File("visitors.json"));
                        for (Visitor visitor : importedVisitors) hotel.addVisitor(visitor);
                        System.out.println("Відвідувачів імпортовано.");
                    }
                    case "0" -> {
                        return;
                    }
                    default -> System.out.println("Невірний вибір.");
                }
            } catch (IOException e) {
                System.out.println("Сталася помилка при роботі з файлом: " + e.getMessage());
            }
        }
    }

    private void addRoom() {
        try {
            System.out.print("ID кімнати: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Тип: ");
            String type = scanner.nextLine();
            System.out.print("Ціна за ніч: ");
            double price = scanner.nextDouble();
            scanner.nextLine();
            hotel.addRoom(new Room(id, type, price));
            System.out.println("Кімнату додано.");
        } catch (IllegalArgumentException a) {
            System.out.println("Помилка аргумента при додаванні кімнати: " + a.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Невідома помилка: " + e.getMessage());
        }
    }

    private void readRoom() {
        try {
            System.out.print("ID кімнати: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Room room = hotel.readRoom(id);
            System.out.println(room);
        } catch (IllegalArgumentException a) {
            System.out.println("Помилка аргумента при перегляді кімнати: " + a.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Невідома помилка: " + e.getMessage());
        }
    }

    private void updateRoom() {
        try {
            System.out.print("ID кімнати: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Новий тип: ");
            String type = scanner.nextLine();
            System.out.print("Нова ціна: ");
            double price = scanner.nextDouble();
            scanner.nextLine();
            Room updated = new Room(id, type, price);
            hotel.updateRoom(updated);
            System.out.println("Кімнату оновлено.");
        } catch (IllegalArgumentException a) {
            System.out.println("Помилка аргумента при оновленні кімнати: " + a.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Невідома помилка: " + e.getMessage());
        }
    }

    private void deleteRoom() {
        try {
            System.out.print("ID кімнати: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            hotel.deleteRoom(id);
            System.out.println("Кімнату видалено.");
        } catch (IllegalArgumentException a) {
            System.out.println("Помилка аргумента при видаленні кімнати: " + a.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Невідома помилка: " + e.getMessage());
        }
    }

    private void addVisitor() {
        try {
            System.out.print("Телефон: ");
            String phone = scanner.nextLine();
            System.out.print("Ім'я: ");
            String name = scanner.nextLine();
            System.out.print("Прізвище: ");
            String surnameVisitor = scanner.nextLine();
            System.out.print("Вік: ");
            int age = scanner.nextInt();
            scanner.nextLine();
            hotel.addVisitor(new Visitor(name, surnameVisitor, age, phone));
            System.out.println("Відвідувача додано.");
        } catch (IllegalArgumentException a) {
            System.out.println("Помилка аргумента при додаванні відвідувача: " + a.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Невідома помилка: " + e.getMessage());
        }
    }

    private void readVisitor() {
        try {
            System.out.print("Телефон: ");
            String phone = scanner.nextLine();
            Visitor visitor = hotel.readVisitor(phone);
            System.out.println(visitor);
        } catch (IllegalArgumentException a) {
            System.out.println("Помилка аргумента при перегляді відвідувача: " + a.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Невідома помилка: " + e.getMessage());
        }
    }

    private void updateVisitor() {
        try {
            System.out.print("Телефон: ");
            String phone = scanner.nextLine();
            System.out.print("Нове ім'я: ");
            String name = scanner.nextLine();
            System.out.print("Нове прізвище: ");
            String surnameVisitor = scanner.nextLine();
            System.out.print("Вік: ");
            int age = scanner.nextInt();
            scanner.nextLine();
            hotel.updateVisitor(new Visitor(name, surnameVisitor, age, phone));
            System.out.println("Відвідувача оновлено.");
        } catch (IllegalArgumentException a) {
            System.out.println("Помилка аргумента при оновленні відвідувача: " + a.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Невідома помилка: " + e.getMessage());
        }
    }

    private void deleteVisitor() {
        try {
            System.out.print("Телефон: ");
            String phone = scanner.nextLine();
            hotel.deleteVisitor(phone);
            System.out.println("Відвідувача видалено.");
        } catch (IllegalArgumentException a) {
            System.out.println("Помилка аргумента при видаленні відвідувача: " + a.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Невідома помилка: " + e.getMessage());
        }
    }
}

