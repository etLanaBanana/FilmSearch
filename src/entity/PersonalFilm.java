package entity;

public class PersonalFilm {
    protected String loginUser;
    protected String titleFim;

    public PersonalFilm(String loginUser, String titleFim) {
        this.loginUser = loginUser;
        this.titleFim = titleFim;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public void setTitleFim(String titleFim) {
        this.titleFim = titleFim;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public String getTitleFim() {
        return titleFim;
    }

    @Override
    public String toString() {
        return loginUser + " добавил фильм " + titleFim;
    }
}
