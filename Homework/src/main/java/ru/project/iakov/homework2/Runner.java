package ru.project.iakov.homework2;

import ru.project.iakov.homework2.dao.UserDao;
import ru.project.iakov.homework2.dao.UserDaoImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Runner {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserDao userDao = new UserDaoImpl();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = readInt("Выберите пункт меню: ");

            switch (choice) {
                case 1 -> createUser();
                case 2 -> findUserById();
                case 3 -> findAllUsers();
                case 4 -> updateUser();
                case 5 -> deleteUser();
                case 6 -> exitApp();
                default -> System.out.println("Неверный пункт меню. Попробуйте снова.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
                ===== Меню =====
                1. Создать пользователя
                2. Найти пользователя по ID
                3. Показать всех пользователей
                4. Обновить пользователя
                5. Удалить пользователя
                6. Выход
                """);
    }

    private static void createUser() {
        long id = readLong("ID: ");
        String name = readLine("Имя: ");
        String email = readLine("Email: ");
        int age = readInt("Возраст: ");

        User user = User.builder()
                .name(name)
                .email(email)
                .age(age)
                .createdAt(LocalDateTime.now())
                .build();

        userDao.create(user);
        System.out.println("Пользователь создан.");
    }

    private static void findUserById() {
        long id = readLong("Введите ID пользователя: ");
        Optional<User> userOpt = userDao.findById(id);
        userOpt.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Пользователь не найден.")
        );
    }

    private static void findAllUsers() {
        List<User> users = userDao.findAll();
        if (users.isEmpty()) {
            System.out.println("Нет пользователей в базе.");
        } else {
            users.forEach(System.out::println);
        }
    }

    private static void updateUser() {
        long id = readLong("ID обновляемого пользователя: ");
        Optional<User> userOpt = userDao.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String name = readLine("Новое имя (" + user.getName() + "): ");
            String email = readLine("Новый email (" + user.getEmail() + "): ");
            int age = readInt("Новый возраст (" + user.getAge() + "): ");

            user.setName(name.isEmpty() ? user.getName() : name);
            user.setEmail(email.isEmpty() ? user.getEmail() : email);
            user.setAge(age <= 0 ? user.getAge() : age);

            userDao.update(user);
            System.out.println("Пользователь обновлён.");
        } else {
            System.out.println("Пользователь с таким ID не найден.");
        }
    }

    private static void deleteUser() {
        long id = readLong("ID пользователя для удаления: ");
        userDao.delete(id);
        System.out.println("Пользователь удалён (если существовал).");
    }

    private static void exitApp() {
        System.out.println("Завершение работы.");
        System.exit(0);
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Введите целое число: ");
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private static long readLong(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextLong()) {
            scanner.nextLine();
            System.out.print("Введите корректный ID: ");
        }
        long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}