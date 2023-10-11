package service;

import entity.List;
import entity.User;
import entity.UserRole;


public class Authorization {
    private List<User> listUser;

    public Authorization(List<User> listUser) {
        this.listUser = listUser;
    }

    public User authenticate(String login, String password) {
        User[] users = new User[10];
        for (int i = 0; i < users.length; i++) {

        }
        for (User user : listUser.getAll()) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public UserRole authorize(User user) {
        return user.getUserRole();
    }


}
