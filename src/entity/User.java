package entity;

import java.io.Serializable;

public class User implements Serializable {
    protected String nickName;
    protected String login;
    protected String password;
    protected static UserRole userRole;
    protected List<FilmRating> listUserFilmRating;

    public List<FilmRating> getListFilmUser() {
        return listUserFilmRating;
    }

    public void setListFilmUser(List<FilmRating> listFilmUser) {
        this.listUserFilmRating = listFilmUser;
    }

    public User(String nickName, String login, String password, UserRole userRole) {
        this.nickName = nickName;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
    }



    public String getNickName() {

        return nickName;
    }

    public void setNickName(String nickName) {

        this.nickName = nickName;
    }

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {

        this.login = login;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public static UserRole getUserRole() {

        return userRole;
    }

    public void setUserRole(UserRole userRole) {

        this.userRole = userRole;
    }

    public static boolean hasUserListThisUser(List<User> userList, String login) {
        User[] users = userList.getAll();

        for (int i = 0; i < users.length && users[i] != null; i++) {
            if (users[i].getLogin().equals(login)) {
                System.out.println("Такой пользователь уже есть!");
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return String.format("Человек с ником %s, логином %s, ролью %s", nickName, login, userRole);
    }
}
