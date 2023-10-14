import bootstrap.Application;
import entity.List;
import entity.User;
import utils.FileUtils;

import java.io.IOException;

import static entity.UserRole.ADMIN;
import static entity.UserRole.COMMON;

public class Main {

    public static void main(String[] args) throws IOException {

//        List<User> userList= new List<>(new User[10]);
//        userList.insert(new User("talala", "fill","qwerty", ADMIN));
//        userList.insert(new User("123", "123","123", ADMIN));
//        userList.insert(new User("3dloska", "roewds","qwerty", COMMON));
//        userList.insert(new User("hareck3500", "loghareck3500","qwerty", COMMON));
//        userList.insert(new User("ipHacker", "21kard","qwerty", COMMON));
//        FileUtils.<User>readFile("src\\usersNewFile.txt");
        System.out.println();
        Application.runApplication();
        
    }
}