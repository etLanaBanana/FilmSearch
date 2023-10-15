package utils;

import bootstrap.Application;
import entity.User;
import java.util.Scanner;

public interface Menu {
    public void printMenu();
    void handleMenu(Application application, User user, Scanner scanner);
}
