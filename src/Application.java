import service.Authorization;
import entity.*;
import exeption.*;
import utils.*;


import java.util.Iterator;
import java.util.Scanner;

public class Application {
    static List<User> listUser = new List<>(new User[10]);
    static List<Film> listFilm = new List<>(new Film[50]);

    static List<FilmRating> filmRatingList = new List<>(new FilmRating[10]);
    static Authorization authorization = new Authorization(listUser);

    public static void runApplication() {
        Menu menu = null;


        FileUtils.readFileUser(listUser,"src\\users1");
        FileUtils.readFileFilmRating(filmRatingList,"src\\ListFilmRating" );

        if (filmRatingList.getSize() != 0) {
            FileUtils.readFileFilm(listFilm, "src\\film1", filmRatingList);

        }
        listFilm.print();
        for (Film films:listFilm.getAll()) {
            System.out.println(films);

        }

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Выберите один из пунктов: \n" +
                "1. Войти \n" +
                "2. Регистрация");
        int a = keyboard.nextInt();
        User user = null;
        if (a == 1) {
            Pair<User, Menu> userMenuPair = tryToAuthoriseUser();
            menu = userMenuPair.getSecondValue();
            user = userMenuPair.getFirstValue();
            menu.printMenu();
        } else if (a == 2) {
            Pair<User, Menu> userMenuPair = tryToRegistrationUser();
        } else {
            System.out.println("1 или 2");
        }
//        menu.printMenu();


        if (user != null) {
            switch (user.getUserRole()) {
                case COMMON -> {
                    int command = keyboard.nextInt();
                    CommonUser common = new CommonUser(user);
                    switch (command) {
                        case 1 -> {
                            keyboard.nextLine();
                            System.out.println("Введите название фильма: ");
                            String findTitle = keyboard.next();
                            for (Film film : listFilm.getAll())
                                if (film.getTitle().equals(findTitle)) {
                                    System.out.println(film);
                                }
                        }
                        case 2 -> {
                            keyboard.nextLine();
                            System.out.println("Введите год выпуска фильма: ");
                            String dateCreateFilm = keyboard.next();
                            for (Film film : listFilm.getAll())
                                if (film.getDate().equals(dateCreateFilm)) {
                                    System.out.println(film);
                                }
                        }
                        case 3 -> {
                            keyboard.nextLine();
                            System.out.println("Введите страну: ");
                            String country = keyboard.next();
                            for (Film film : listFilm.getAll())
                                if (film.getCountry().equals(country)) {
                                    System.out.println(film);
                                }
                        }
                        case 4 -> {

                        }
                        case 5 -> {

                        }
                        case 6 -> {

                        }
                        case 7 -> {

                        }
                        case 8 -> {

                        }
                        case 9 -> {
                            System.out.println("""
                                    Введите через enter следующие поля:
                                    1. Ник
                                    2. Логин
                                    3. Пароль
                                    4. Роль
                                    """);
                            keyboard.nextLine();
                            String nickName = keyboard.nextLine();
                            String login = keyboard.nextLine();
                            String password = keyboard.nextLine();
                            UserRole role = UserRole.valueOf(keyboard.nextLine());
                            common.updateData(nickName, login, password, role);

                            listUser.replaceElement(user, common);
                        }

                    }
                }
                case ADMIN -> {
                    int command = keyboard.nextInt();

                    switch (command) {

                    }
                }
            }
        }

        FileUtils.writeDataToFileUser(listUser, "src\\usersDO.txt");


    }

    private static Pair<User, Menu> tryToAuthoriseUser() {
        Menu menu;
        String login;
        String password;
        Scanner keyboard = new Scanner(System.in);


        System.out.println("Введите логин: ");
        login = keyboard.nextLine();
        System.out.println("Введите пароль: ");
        password = keyboard.nextLine();
        User authenticatedUser = authorization.authenticate(login, password);

        if (authenticatedUser == null) {
            throw new InvalidCredentialsException("Неверный логин или пароль");
        }

        UserRole userRole = authorization.authorize(authenticatedUser);

        menu = switch (userRole) {
            case COMMON -> new CommonUserMenu();
            default -> new AdminUserMenu();
        };
        return new Pair<>(authenticatedUser, menu);
    }

    private static Pair<User, Menu> tryToRegistrationUser() {
        Menu menu;
        String login;
        String password;
        UserRole userRole;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Введите ник: ");
        String nickName = keyboard.nextLine();
        System.out.println("Введите логин: ");
        login = keyboard.nextLine();
        System.out.println("Введите пароль: ");
        password = keyboard.nextLine();
        System.out.println("Введите пароль админа: ");
        if (keyboard.nextLine().equals("123")) {
            menu = new AdminUserMenu();
            userRole = UserRole.ADMIN;
        } else {
            menu = new CommonUserMenu();
            userRole = UserRole.COMMON;
        }
        User newUser = new User(nickName, login, password, userRole);
//        for (User user : listUser.getAll()) {
//            if (user.getLogin().equals(login)) {
//                System.out.println("Такой пользователь уже есть!");
//
//            } else {
        listUser.insert(newUser);
//            }

//        }
        return new Pair<>(newUser, menu);
    }
}

