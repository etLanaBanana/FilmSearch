package entity;

public class CommonUser extends User {


    public CommonUser(String nickName, String login, String password, UserRole userRole) {
        super(nickName, login, password, userRole);
    }

    public CommonUser(User user) {
        super(user.nickName, user.login, user.getPassword(), user.getUserRole() );
    }

    public void updateData(String nickName, String login, String password, UserRole userRole) {
        this.nickName = nickName;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return String.format("Человек с ником %s, логином %s, ролью %s" , nickName, login, userRole);
    }

}
