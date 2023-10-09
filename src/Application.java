import exeption.InvalidCredentialsException;
import service.Authorization;
import entity.*;
import utils.*;


import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.Scanner;

import static entity.Film.getFilmForIdFilm;
import static entity.Film.getIdFilmForTitle;
import static entity.User.hasUserListThisUser;

public class Application {
    static List<User> listUser = new List<>(new User[10]);
    static List<Film> listFilm = new List<>(new Film[50]);
    static List<PersonalFilm> personalFilmList =new List<>(new PersonalFilm[10]);

    static List<FilmRating> filmRatingList = new List<>(new FilmRating[10]);
    static Authorization authorization = new Authorization(listUser);

    public static void runApplication() {
        Menu menu = null;


        FileUtils.readFileUser(listUser, "src\\users");
        FileUtils.readFileFilmRating(filmRatingList, "src\\ListFilmRating");
        FileUtils.readFileFilm(listFilm, "src\\films", filmRatingList);
        FileUtils.readFilePersonalFilm(personalFilmList, "src\\personal");

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Выберите один из пунктов: \n" +
                "1. Войти \n" +
                "2. Регистрация");

            int a = keyboard.nextInt(); //если ввести не числа
            while (!(a == 1 || a == 2)) {
                System.out.println("Введите только 1 или 2!");
                a = keyboard.nextInt();
            }
        User user = null;
        if (a == 1) {
            Pair<User, Menu> userMenuPair = tryToAuthoriseUser();
            menu = userMenuPair.getSecondValue();
            user = userMenuPair.getFirstValue();
            menu.printMenu();
        } else if (a == 2) {
            Pair<User, Menu> userMenuPair = tryToRegistrationUser();
        }


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
                            keyboard.nextLine();
                            System.out.println("Введите жанр: ");
                            String genre = keyboard.next();
                            for (Film film : listFilm.getAll())
                                if (film.getGenre().equals(genre)) {
                                    System.out.println(film);
                                }
                        }
                        case 5 -> {
                            keyboard.nextLine();
                            System.out.println("Введите название фильма: ");
                            String title = keyboard.nextLine();
                            System.out.println("Введите оценку от 1 до 100: ");
                            int rating = Integer.parseInt(keyboard.nextLine());
                            while (!(rating > 0 & rating <= 100))
                            {
                                System.out.println("Введите оценку от 1 до 100: ");
                                rating = Integer.parseInt(keyboard.nextLine());
                            }
                            FilmRating newFilmRating = new FilmRating(getIdFilmForTitle(listFilm, title), title, user.getLogin(), rating);
                            filmRatingList.insert(newFilmRating);

                        }
                        case 6 -> {
                            PersonalFilm[] personalFilms = personalFilmList.getAll();
                            Film[] films = listFilm.getAll();

                            for (int i = 0; i < personalFilms.length && personalFilms[i] != null; i++) {
                                if(personalFilms[i].getLoginUser().equals(user.getLogin())) {
                                    for (int j = 0; j < films.length && films[j] != null; j++){
                                        if(personalFilms[i].getTitleFilm().equals(films[j].getTitle())) {
                                            System.out.println(films[j].toString());
                                        }
                                    }
                                }
                            }
                        }
                        case 7 -> {
                            PersonalFilm[] personalFilms = personalFilmList.getAll();
                            Film[] films = listFilm.getAll();
                            List<String> listTitle = new List<>(new String[100]);
                            String[] massTitle = listTitle.getAll();
                            System.out.println("Введите id фильма, который хотите добавить: ");
                            int idFilm = keyboard.nextInt();

                            Film film = getFilmForIdFilm(films, idFilm);
                            for (int i = 0; i < personalFilms.length && personalFilms[i] != null; i++) {
                                if (user.getLogin().equals(personalFilms[i].getLoginUser())) {
                                    listTitle.insert(personalFilms[i].getTitleFilm());
                                }
                            }
                            int count = 0;
                            int it = 0;
                            for (int j = 0; j < massTitle.length; j++) {
                                if (massTitle[j].equals(film.getTitle())) {
                                    System.out.println("Такой фильм уже в вашем списке!");
                                } else {
                                    count++;
                                }
                                it = j;
                            }
                            if (count == it + 1) {
                                PersonalFilm personalFilm = new PersonalFilm(user.getLogin(), film.getTitle());
                                personalFilmList.insert(personalFilm);
                                personalFilmList.print();
                            }
//                            int switcher = 0;
//                            int count = 0;
//                            while (switcher == 0) {
//                                if (count > 1) {
//                                    System.out.println("Вы ввели неверный ID");
//                                }
//                            }
//                                for (int i = 0; i < films.length && films[i] != null; i++) {
//                                    if (films[i].getIdFilm() != idFilm) {
//                                        System.out.println("Вы ввели неверный ID");
//                                        idFilm = Integer.parseInt(keyboard.nextLine());
//                                    }
//                                }
//                            }
//                            for (int i = 0; i < films.length && films[i] != null; i++) {
//                                if (films[i].getIdFilm() == idFilm) {
//                                    for (int j = 0; j < personalFilms.length && films[j] != null; i++) {
//                                        if (personalFilms[j].getLoginUser().equals(user.getLogin())) {
//                                            for (int k = 0; k < personalFilms.length && personalFilms[k] != null; k++) {
//
//                                            }
//                                            PersonalFilm newPersonalFilm = new PersonalFilm(user.getLogin(), films[i].getTitle());
//                                            personalFilmList.insert(newPersonalFilm);
//                                            break;
//                                        }
//                                        if (personalFilms[j].getLoginUser().equals(user.getLogin()) && !personalFilms[j].getTitleFilm().equals(films[i].getTitle())) {
//                                            PersonalFilm newPersonalFilm = new PersonalFilm(user.getLogin(), films[i].getTitle());
//                                            personalFilmList.insert(newPersonalFilm);
//                                            //switcher++;
//                                        }
//                                    }
//                                }
//                            }
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
        User[] users = listUser.getAll();


        Menu menu;
        String login;
        String password;
        UserRole userRole;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Введите ник: ");
        String nickName = keyboard.nextLine();
        System.out.println("Введите логин: ");
        login = keyboard.nextLine();
        while (hasUserListThisUser(listUser, login)) {
            System.out.println("Введите логин: ");
            login = keyboard.nextLine();
        }
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
        listUser.insert(newUser);
        listUser.print();
        return new Pair<>(newUser, menu);
    }
}