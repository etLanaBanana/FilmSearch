package utils;
import bootstrap.Application;
import entity.*;

import java.util.Scanner;

import static entity.Film.*;

public class AdminUserMenu implements Menu {

    @Override
    public void printMenu() {
        System.out.println("1. Поиск фильма по названию. \n" +
                "2. Поиск фильма по году \n" +
                "3. Поиск фильма по стране \n" +
                "4. Поиск фильма по жанру \n" +
                "5. Выставить оценку фильму \n" +
                "6. Вывести мой список фильмов \n" +
                "7. Добавить фильм по id \n" +
                "8. Удалить фильм по id \n" +
                "9. Сменить данные о себе \n" +
                "10. Добавить фильм в общий список \n" +
                "11. Удалить из общего списка по id");
    }
    @Override
    public void handleMenu(Application application, User user, Scanner keyboard) {
        String input2 = keyboard.nextLine();
        AdminUser adminUser = new AdminUser(user);
        while (!(input2.equals("1")) & !(input2.equals("2")) & !(input2.equals("3"))
                & !(input2.equals("4")) & !(input2.equals("5")) & !(input2.equals("6"))
                & !(input2.equals("7")) & !(input2.equals("8")) & !(input2.equals("9"))
                & !(input2.equals("10")) & !(input2.equals("11"))) {
            System.out.println("Введите цифру от 1 до 11 в соответствии с выбранным пунктом меню!");
            input2 = keyboard.nextLine();
        }
        int command = Integer.parseInt(input2);
        switch (command) {
            case 1 -> {
                System.out.println("Введите название фильма: ");
                String findTitle = keyboard.next();
                Film[] films = application.listFilm.getAll();
                for (int i = 0; i < films.length && films[i] != null; i++) {
                    if (films[i].getTitle().equals(findTitle)) {
                        System.out.println(films[i]);
                    }
                }
            }
            case 2 -> {
                System.out.println("Введите год выпуска фильма: ");
                String dateCreateFilm = keyboard.next();
                Film[] films = application.listFilm.getAll();
                for (int i = 0; i < films.length && films[i] != null; i++) {
                    if (films[i].getDate().equals(dateCreateFilm)) {
                        System.out.println(films[i]);
                    }
                }
            }
            case 3 -> {
                System.out.println("Введите страну: ");
                String country = keyboard.next();
                Film[] films = application.listFilm.getAll();
                for (int i = 0; i < films.length && films[i] != null; i++) {
                    if (films[i].getCountry().equals(country)) {
                        System.out.println(films[i]);
                    }
                }
            }
            case 4 -> {
                System.out.println("Введите жанр: ");
                String genre = keyboard.next();
                Film[] films = application.listFilm.getAll();
                for (int i = 0; i < films.length && films[i] != null; i++) {
                    if (films[i].getGenre().equals(genre)) {
                        System.out.println(films[i]);
                    }
                }
            }
            case 5 -> {
                System.out.println("Введите название фильма: ");
                String title = keyboard.nextLine();
                System.out.println("Введите оценку от 1 до 100: ");
                int rating = Integer.parseInt(keyboard.nextLine());
                while (!(rating > 0 & rating <= 100)) {
                    System.out.println("Введите оценку от 1 до 100: ");
                    rating = Integer.parseInt(keyboard.nextLine());
                }
                FilmRating newFilmRating = new FilmRating(getIdFilmForTitle(application.listFilm, title), title, user.getLogin(), rating);
                application.filmRatingList.insert(newFilmRating);

            }
            case 6 -> {
                PersonalFilm[] personalFilms = application.personalFilmList.getAll();
                Film[] films = application.listFilm.getAll();

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
                PersonalFilm[] personalFilms = application.personalFilmList.getAll();
                Film[] films = application.listFilm.getAll();
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
                        application.personalFilmList.insert(personalFilm);
                        application.personalFilmList.print();
                    }
                }
            }
            case 8 -> {
                PersonalFilm[] personalFilms = application.personalFilmList.getAll();
                System.out.println("Введите название фильма");
                String titleFilm = keyboard.nextLine();
                int count = 0;
                int index = 0;
                for (int i = 0; i < personalFilms.length & personalFilms[i] != null; i++) {
                    if (personalFilms[i].getLoginUser().equals(user.getLogin()) & personalFilms[i].getTitleFilm().equals(titleFilm)) {
                        application.personalFilmList.remove(personalFilms[i]);
                        System.out.println("Фильм удален из вашего списка");
                        application.personalFilmList.print();

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
                System.out.println("Введите ник :");
                String nickName = keyboard.nextLine();
                System.out.println("Введите логин: ");
                String login = keyboard.nextLine();
                System.out.println("Введите пароль: ");
                String password = keyboard.nextLine();

                UserRole role = UserRole.ADMIN;
                adminUser.updateData(nickName, login, password, role);

                application.listUser.replaceElement(user, adminUser);
            }
            case 10 -> {
                Film[] films = application.listFilm.getAll();
                int IdFilm1;
                int IdFilm;
                while (true) {
                    IdFilm1 = (int) (Math.random() * 100000);
                    if (!isHadIdFilmInIdFilmList(films, IdFilm1)) {
                        IdFilm = IdFilm1;
                        break;
                    }
                }
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
                application.listFilm.insert(film);
                application.listFilm.print();
            }
            case 11 -> {
                Film[] films = application.listFilm.getAll();
                int IdFilm = 0;

                System.out.println("Введите id: ");
                IdFilm = keyboard.nextInt();
                for (int i = 0; i < films.length && films[i] != null; i++) {
                        if(films[i].getIdFilm() == IdFilm) {
                            application.listFilm.remove(films[i]);
                            application.listFilm.print();
                    }
                }
            }
        }
    }
}
