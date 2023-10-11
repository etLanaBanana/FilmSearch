package bootstrap;

import service.Authorization;
import entity.*;
import exeption.InvalidCredentialsException;
import utils.*;


import java.util.Scanner;

import static entity.Film.*;
import static entity.User.hasUserListThisUser;

public class Application {
    public final List<User> listUser;
    public final List<Film> listFilm;
    public final List<PersonalFilm> personalFilmList;
    public final List<FilmRating> filmRatingList;
    public final Authorization authorization;

    public Application() {
        listUser = FileUtils.readFileUser( "src\\users");
        filmRatingList = FileUtils.readFileFilmRating( "src\\listFilmRating");
        listFilm = FileUtils.readFileFilm( "src\\films", filmRatingList);
        personalFilmList = FileUtils.readFilePersonalFilm( "src\\personal");
        authorization = new Authorization(listUser);
    }

    public static void runApplication() {
        Application application = new Application();
        Menu menu = null;

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
            Pair<User, Menu> userMenuPair =  application.authorization.tryToAuthoriseUser();
            menu = userMenuPair.getSecondValue();
            user = userMenuPair.getFirstValue();
        } else if (inputInt == 2) {
            Pair<User, Menu> userMenuPair = application.authorization.tryToRegistrationUser();
            menu = userMenuPair.getSecondValue();
            user = userMenuPair.getFirstValue();
        }

        menu.printMenu();
        menu.handleMenu(application, user, keyboard);




        FileUtils.writeDataToFileUser(application.listUser, "src\\usersDO.txt");
        FileUtils.writeDataToFileFilm(application.listFilm, "src\\filmsDO.txt");
        FileUtils.writeDataToFileFilmRating(application.filmRatingList, "src\\filmRatingDO.txt");
        FileUtils.writeDataToFilePersonalFilm(application.personalFilmList, "src\\personalDO.txt");


    }


}
