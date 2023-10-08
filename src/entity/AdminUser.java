package entity;

public class AdminUser extends User {


    public AdminUser(String nickName, String login, String password, UserRole userRole) {
        super(nickName, login, password, userRole);
    }
    @Override
    public String toString() {
        return "Человек с " +
                "ником: " + nickName + '\'' +
                "логином: " + login + '\'' +
                "ролью: " + userRole;
    }
}
