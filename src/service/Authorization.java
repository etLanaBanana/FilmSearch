package service;

import bootstrap.Application;
import entity.List;
import entity.Pair;
import entity.User;
import entity.UserRole;
import exeption.InvalidCredentialsException;
import utils.AdminUserMenu;
import utils.CommonUserMenu;
import utils.Menu;

import java.util.Scanner;

import static entity.User.hasUserListThisUser;


public class Authorization {
    private List<User> listUser;

    public Authorization(List<User> listUser) {
        this.listUser = listUser;
    }

    public User authenticate( String login, String password) {
        for (User user : listUser.getAll()) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    public Pair<User, Menu> tryToAuthoriseUser() {

        Menu menu;
        String login;
        String password;
        Scanner keyboard = new Scanner(System.in);


        System.out.println("Введите логин: ");
        login = keyboard.nextLine();
        System.out.println("Введите пароль: ");
        password = keyboard.nextLine();
        User authenticatedUser = authenticate(login, password);

        if (authenticatedUser == null) {
            throw new InvalidCredentialsException("Неверный логин или пароль");
        }

        UserRole userRole = authorize(authenticatedUser);

        menu = switch (userRole) {
            case COMMON -> new CommonUserMenu();
            case ADMIN -> new AdminUserMenu();
        };
        return new Pair<>(authenticatedUser, menu);
    }

    public UserRole authorize(User user) {
        return User.getUserRole();
    }
    public static Pair<User, Menu> tryToRegistrationUser() {
        Application application = new Application();
        User[] users = application.listUser.getAll();


        Menu menu;
        String login;
        String password;
        UserRole userRole;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Введите ник: ");
        String nickName = keyboard.nextLine();
        System.out.println("Введите логин: ");
        login = keyboard.nextLine();
        while (hasUserListThisUser(application.listUser, login)) {
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
        application.listUser.insert(newUser);
        application.listUser.print();
        return new Pair<>(newUser, menu);
    }
}