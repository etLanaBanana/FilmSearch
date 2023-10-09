package entity;

public class PersonalFilm {
    protected String loginUser;
    protected String titleFilm;

    public PersonalFilm(String loginUser, String titleFilm) {
        this.loginUser = loginUser;
        this.titleFilm = titleFilm;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getTitleFilm() {
        return titleFilm;
    }

    public void setTitleFilm(String titleFilm) {
        this.titleFilm = titleFilm;
    }

    @Override
    public String toString() {
        return loginUser + " добавил фильм " + titleFilm;
    }


}
