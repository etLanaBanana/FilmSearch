package entity;

import java.io.Serializable;

public class Film implements Serializable {
    protected int IdFilm;
    protected String title;
    protected String genre;
    protected String country;
    protected String date;
    protected int rating;
    //    public Film(int idFilm, String title, String genre, String country, String date, int rating) {
//        this.IdFilm = idFilm;
//        this.title = title;
//        this.genre = genre;
//        this.country = country;
//        this.date = date;
//        this.rating = rating;
//    }
    static public FilmBuilder builder() {
        return new FilmBuilder(); }
    public int getIdFilm() {
        return IdFilm;
    }

    public void setIdFilm(int idFilm) {
        IdFilm = idFilm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRating() {

        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public static int getIdFilmForTitle(List<Film> listFilm, String title) {
        Film[] films = listFilm.getAll();
        for (int i = 0; i < films.length & films[1] != null; i++) {
            if (films[i].getTitle().equals(title)) {
                return films[1].getIdFilm();
            }
        }
        return 0;
    }
    @Override
    public String toString() {
        return String.format("Фильм с ID %d, Название: %s, Жанр: %s, Страна: %s, Год выпуска: %s, Рейтинг: %d",
                IdFilm, title, genre, country, date, rating);
    }
    public static class FilmBuilder {
        private Film newFilm;

        public FilmBuilder() {
            newFilm = new Film();
        }

        public FilmBuilder buildIdFilm(int IdFilm){
            newFilm.IdFilm =IdFilm;
            return this;
        }
        public FilmBuilder buildTitle(String title){
            newFilm.title = title;
            return this;
        }
        public FilmBuilder buildGenre(String genre){
            newFilm.genre = genre;
            return this;
        }
        public FilmBuilder buildCounty(String country){
            newFilm.country = country;
            return this;
        }
        public FilmBuilder buildDate(String date){
            newFilm.date = date;
            return this;
        }
        public FilmBuilder buildRating(int rating){
            newFilm.rating = rating;
            return this;
        }

        public Film build() {
            return newFilm;
        }

    }
}

