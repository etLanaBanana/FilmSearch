package bootstrap;

import service.Authorization;
import entity.*;
import utils.*;
import java.io.IOException;
import java.util.Scanner;

public class Application {
    public final List<User> listUser;
    public final List<Film> listFilm;
    public final List<PersonalFilm> personalFilmList;
    public List<FilmRating> filmRatingList;
    public final Authorization authorization;

    public Application() {
        listUser = FileUtils.readFileUser("src\\users");
        filmRatingList = FileUtils.readFileFilmRating("src\\listFilmRating");
        listFilm = FileUtils.readFileFilm( "src\\films", filmRatingList);
        personalFilmList = FileUtils.readFilePersonalFilm( "src\\personal");
        authorization = new Authorization(listUser);
    }

    public static void runApplication() {
        Application application = new Application();
        Menu menu = null;
        Scanner keyboard = new Scanner(System.in);

        System.out.println("""
                Выберите один из пунктов:\s
                1. Войти\s
                2. Регистрация""");

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
            Pair<User, Menu> userMenuPair = Authorization.tryToRegistrationUser();
            menu = userMenuPair.getSecondValue();
            user = userMenuPair.getFirstValue();
        }

        assert menu != null;
        menu.printMenu();
        menu.handleMenu(application, user, keyboard);


        FileUtils.writeDataToFileUser(application.listUser, "src\\usersDO.txt");
        FileUtils.writeDataToFileFilm(application.listFilm, "src\\filmsDO.txt");
        FileUtils.writeDataToFileFilmRating(application.filmRatingList, "src\\filmRatingDO.txt");
        FileUtils.writeDataToFilePersonalFilm(application.personalFilmList, "src\\personalDO.txt");


    }
}
