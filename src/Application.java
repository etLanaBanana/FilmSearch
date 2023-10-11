
import service.Authorization;
import entity.*;
import exeption.InvalidCredentialsException;
import utils.*;

import java.util.Scanner;

import static entity.Film.*;
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
        FileUtils.readFileFilmRating(filmRatingList, "src\\listFilmRating");
        FileUtils.readFileFilm(listFilm, "src\\films", filmRatingList);
        FileUtils.readFilePersonalFilm(personalFilmList, "src\\personal");

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Выберите один из пунктов: \n" +
                "1. Войти \n" +
                "2. Регистрация");
        String input = keyboard.nextLine();

        while (!(input.equals("1")) & !(input.equals("2"))) {
            System.out.println("Введите 1 или 2");
            input = keyboard.nextLine();
        }

        int inputInt = Integer.parseInt(input);
        User user = null;
        if (inputInt == 1) {
            Pair<User, Menu> userMenuPair = tryToAuthoriseUser();
            menu = userMenuPair.getSecondValue();
            user = userMenuPair.getFirstValue();
            menu.printMenu();
        } else if (inputInt == 2) {
            Pair<User, Menu> userMenuPair = tryToRegistrationUser();
            menu = userMenuPair.getSecondValue();
            user = userMenuPair.getFirstValue();
            menu.printMenu();
        }


        if (user != null) {
            switch (User.getUserRole()) {
                case COMMON -> {

                    String input2 = keyboard.nextLine();
                    CommonUser common = new CommonUser(user);
                    while (!(input2.equals("1")) & !(input2.equals("2")) & !(input2.equals("3"))
                            & !(input2.equals("4")) & !(input2.equals("5")) & !(input2.equals("6"))
                            & !(input2.equals("7")) & !(input2.equals("8")) & !(input2.equals("9"))) {
                        System.out.println("Введите цифру от 1 до 9 в соответствии с выбранным пунктом меню!");
                        input2 = keyboard.nextLine();
                    }
                    int command = Integer.parseInt(input2);
                    switch (command) {
                        case 1 -> {
                            keyboard.nextLine();
                            System.out.println("Введите название фильма: ");
                            String findTitle = keyboard.next();
                            Film[] films = listFilm.getAll();
                            for (int i = 0; i < films.length && films[i] != null; i++) {
                                if (films[i].getTitle().equals(findTitle)) {
                                    System.out.println(films[i]);
                                }
                            }
                        }
                        case 2 -> {
                            keyboard.nextLine();
                            System.out.println("Введите год выпуска фильма: ");
                            String dateCreateFilm = keyboard.next();
                            Film[] films = listFilm.getAll();
                            for (int i = 0; i < films.length && films[i] != null; i++) {
                                if (films[i].getDate().equals(dateCreateFilm)) {
                                    System.out.println(films[i]);
                                }
                            }
                        }
                        case 3 -> {
                            keyboard.nextLine();
                            System.out.println("Введите страну: ");
                            String country = keyboard.next();
                            Film[] films = listFilm.getAll();
                            for (int i = 0; i < films.length && films[i] != null; i++) {
                                if (films[i].getCountry().equals(country)) {
                                    System.out.println(films[i]);
                                }
                            }
                        }
                        case 4 -> {
                            keyboard.nextLine();
                            System.out.println("Введите жанр: ");
                            String genre = keyboard.next();
                            Film[] films = listFilm.getAll();
                            for (int i = 0; i < films.length && films[i] != null; i++) {
                                if (films[i].getGenre().equals(genre)) {
                                    System.out.println(films[i]);
                                }
                            }
                        }
                        case 5 -> {
                            keyboard.nextLine();
                            System.out.println("Введите название фильма: ");
                            String title = keyboard.nextLine();
                            System.out.println("Введите оценку от 1 до 100: ");
                            int rating = Integer.parseInt(keyboard.nextLine());
                            while (!(rating > 0 & rating <= 100)) {
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
                                if (personalFilms[i].getLoginUser().equals(user.getLogin())) {
                                    for (int j = 0; j < films.length && films[j] != null; j++) {
                                        if (personalFilms[i].getTitleFilm().equals(films[j].getTitle())) {
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
                            while (!isHadIdFilmInIdFilmList(films, idFilm)) {
                                System.out.println("Такого id нет");
                                idFilm = keyboard.nextInt();
                            }

                            if (isHadIdFilmInIdFilmList(films, idFilm)) {
                                Film film = getFilmForIdFilm(films, idFilm);
                                for (int i = 0; i < personalFilms.length && personalFilms[i] != null; i++) {
                                    if (user.getLogin().equals(personalFilms[i].getLoginUser())) {
                                        listTitle.insert(personalFilms[i].getTitleFilm());
                                    }
                                }
                                int count = 0;
                                int it = 0;
                                for (int j = 0; j < massTitle.length && massTitle[j] != null; j++) {
                                    if (massTitle[j].equals(film.getTitle())) {
                                        System.out.println("Такой фильм есть!");
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
                            }
                        }
                        case 8 -> {
                            PersonalFilm[] personalFilms = personalFilmList.getAll();
                            System.out.println("Введите название фильма");
                            keyboard.nextLine();
                            String titleFilm = keyboard.nextLine();
                            int count = 0;
                            int index = 0;
                            for (int i = 0; i < personalFilms.length & personalFilms[i] != null; i++) {
                                if (personalFilms[i].getLoginUser().equals(user.getLogin()) & personalFilms[i].getTitleFilm().equals(titleFilm)) {
                                    personalFilmList.remove(personalFilms[i]);
                                    System.out.println("Фильм удален из вашего списка");
                                    personalFilmList.print();

                                } else {
                                    count++;
                                }
                                index = i;
                            }
                            if (count == index + 1) {

                                System.out.println("У вас нет фильма с таким названием");
                            }

                        }


                        case 9 -> {
                            keyboard.nextLine();
                            System.out.println("Введите ник :");
                            String nickName = keyboard.nextLine();
                            System.out.println("Введите логин: ");
                            String login = keyboard.nextLine();
                            System.out.println("Введите пароль: ");
                            String password = keyboard.nextLine();

                            UserRole role = UserRole.COMMON;
                            common.updateData(nickName, login, password, role);

                            listUser.replaceElement(user, common);
                        }


                    }
                }
                case ADMIN -> {

                    String input2 = keyboard.nextLine();
                    CommonUser common = new CommonUser(user);
                    while (!(input2.equals("1")) & !(input2.equals("2")) & !(input2.equals("3"))
                            & !(input2.equals("4")) & !(input2.equals("5")) & !(input2.equals("6"))
                            & !(input2.equals("7")) & !(input2.equals("8")) & !(input2.equals("9"))) {
                        System.out.println("Введите цифру от 1 до 9 в соответствии с выбранным пунктом меню!");
                        input2 = keyboard.nextLine();
                    }
                    int command = Integer.parseInt(input2);
                    switch (command) {
                        case 1 -> {
                            keyboard.nextLine();
                            System.out.println("Введите название фильма: ");
                            String findTitle = keyboard.next();
                            Film[] films = listFilm.getAll();
                            for (int i = 0; i < films.length && films[i] != null; i++) {
                                if (films[i].getTitle().equals(findTitle)) {
                                    System.out.println(films[i]);
                                }
                            }
                        }
                        case 2 -> {
                            keyboard.nextLine();
                            System.out.println("Введите год выпуска фильма: ");
                            String dateCreateFilm = keyboard.next();
                            Film[] films = listFilm.getAll();
                            for (int i = 0; i < films.length && films[i] != null; i++) {
                                if (films[i].getDate().equals(dateCreateFilm)) {
                                    System.out.println(films[i]);
                                }
                            }
                        }
                        case 3 -> {
                            keyboard.nextLine();
                            System.out.println("Введите страну: ");
                            String country = keyboard.next();
                            Film[] films = listFilm.getAll();
                            for (int i = 0; i < films.length && films[i] != null; i++) {
                                if (films[i].getCountry().equals(country)) {
                                    System.out.println(films[i]);
                                }
                            }
                        }
                        case 4 -> {
                            keyboard.nextLine();
                            System.out.println("Введите жанр: ");
                            String genre = keyboard.next();
                            Film[] films = listFilm.getAll();
                            for (int i = 0; i < films.length && films[i] != null; i++) {
                                if (films[i].getGenre().equals(genre)) {
                                    System.out.println(films[i]);
                                }
                            }
                        }
                        case 5 -> {
                            keyboard.nextLine();
                            System.out.println("Введите название фильма: ");
                            String title = keyboard.nextLine();
                            System.out.println("Введите оценку от 1 до 100: ");
                            int rating = Integer.parseInt(keyboard.nextLine());
                            while (!(rating > 0 & rating <= 100)) {
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
                                if (personalFilms[i].getLoginUser().equals(user.getLogin())) {
                                    for (int j = 0; j < films.length && films[j] != null; j++) {
                                        if (personalFilms[i].getTitleFilm().equals(films[j].getTitle())) {
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
                            while (!isHadIdFilmInIdFilmList(films, idFilm)) {
                                System.out.println("Такого id нет");
                                idFilm = keyboard.nextInt();
                            }

                            if (isHadIdFilmInIdFilmList(films, idFilm)) {
                                Film film = getFilmForIdFilm(films, idFilm);
                                for (int i = 0; i < personalFilms.length && personalFilms[i] != null; i++) {
                                    if (user.getLogin().equals(personalFilms[i].getLoginUser())) {
                                        listTitle.insert(personalFilms[i].getTitleFilm());
                                    }
                                }
                                int count = 0;
                                int it = 0;
                                for (int j = 0; j < massTitle.length && massTitle[j] != null; j++) {
                                    if (massTitle[j].equals(film.getTitle())) {
                                        System.out.println("Такой фильм есть!");
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
                            }
                        }
                        case 8 -> {
                            PersonalFilm[] personalFilms = personalFilmList.getAll();
                            System.out.println("Введите название фильма");
                            keyboard.nextLine();
                            String titleFilm = keyboard.nextLine();
                            int count = 0;
                            int index = 0;
                            for (int i = 0; i < personalFilms.length & personalFilms[i] != null; i++) {
                                if (personalFilms[i].getLoginUser().equals(user.getLogin()) & personalFilms[i].getTitleFilm().equals(titleFilm)) {
                                    personalFilmList.remove(personalFilms[i]);
                                    System.out.println("Фильм удален из вашего списка");
                                    personalFilmList.print();

                                } else {
                                    count++;
                                }
                                index = i;
                            }
                            if (count == index + 1) {

                                System.out.println("У вас нет фильма с таким названием");
                            }

                        }


                        case 9 -> {
                            keyboard.nextLine();
                            System.out.println("Введите ник :");
                            String nickName = keyboard.nextLine();
                            System.out.println("Введите логин: ");
                            String login = keyboard.nextLine();
                            System.out.println("Введите пароль: ");
                            String password = keyboard.nextLine();

                            UserRole role = UserRole.COMMON;
                            common.updateData(nickName, login, password, role);

                            listUser.replaceElement(user, common);
                        }
                        case 10 -> {
                            Film[] films = listFilm.getAll();
                            int IdFilm1;
                            int IdFilm;
                            while (true) {
                                IdFilm1 = (int) (Math.random() * 100000);
                                if (!isHadIdFilmInIdFilmList(films, IdFilm1)) {
                                    IdFilm = IdFilm1;
                                    break;
                                }
                            }
                            keyboard.nextLine();
                            System.out.println("Введите название фильма: ");
                            String title = keyboard.nextLine();

                            System.out.println("Введите жанр фильма: ");
                            String genre = keyboard.nextLine();
                            System.out.println("Введите страну производства фильма: ");
                            String country = keyboard.nextLine();
                            System.out.println("Введите год выпуска: ");
                            String date = keyboard.nextLine();
                            Film film = builder().buildIdFilm(IdFilm)
                                    .buildTitle(title)
                                    .buildGenre(genre)
                                    .buildCounty(country)
                                    .buildDate(date)
                                    .build();
                            listFilm.insert(film);
                            listFilm.print();
                        }
                        case 11 -> {
                            Film[] films = listFilm.getAll();
                            int IdFilm1 = 0;
                            int IdFilm;
                            System.out.println("Введите id: ");
                            while (true) {
                                IdFilm = keyboard.nextInt();
                                if (isHadIdFilmInIdFilmList(films, IdFilm1)) {
                                    IdFilm = IdFilm1;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        FileUtils.writeDataToFileUser(listUser, "src\\usersDO.txt");
        FileUtils.writeDataToFileFilm(listFilm, "src\\filmsDO.txt");
        FileUtils.writeDataToFileFilmRating(filmRatingList, "src\\filmRatingDO.txt");
        FileUtils.writeDataToFilePersonalFilm(personalFilmList, "src\\personalDO.txt");


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