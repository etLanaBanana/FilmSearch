package utils;

import entity.*;

import java.io.*;
import java.nio.file.Paths;

public class FileUtils {

    static String[] getPartsOfLine(String line) {

        return line.split(", ");
    }

    public static List<User> readFileUser(String fileName) {
        List<User> listUser = new List<>(new User[20]);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            bufferedReader.readLine();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] partsOfLine = getPartsOfLine(line);

                UserRole userRole = switch (partsOfLine[3]) {
                    case "COMMON" -> UserRole.COMMON;
                    case "ADMIN" -> UserRole.ADMIN;
                    default -> throw new Exception("Неверные данные");
                };

                User user = new User(partsOfLine[0], partsOfLine[1], partsOfLine[2], userRole);


                listUser.insert(user);
            }

            listUser.print();
            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            try {
                assert bufferedReader != null;
                bufferedReader.close();
                fileReader.close();
            } catch (Exception er) {
                System.out.println("Произошла ошибка");
            }
            throw new RuntimeException("Такой файл не найден");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return listUser;
    }

    public static void writeDataToFileUser(List<User> listUser, String fileName) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            bufferedWriter = new BufferedWriter(fileWriter);

            User[] users = listUser.getAll();

            for (int i = 0; i < listUser.getSize(); i++) {
                bufferedWriter.write(users[i] + "\n");
            }

            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Film> readFileFilm(String fileName, List<FilmRating> filmRatingList) {
        List<Film> listFilm = new List<>(new Film[20]);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            bufferedReader.readLine();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] partsOfLine = getPartsOfLine(line);
                Film film = Film.builder().buildIdFilm(Integer.parseInt(partsOfLine[0]))
                        .buildTitle(partsOfLine[1])
                        .buildGenre(partsOfLine[2])
                        .buildCounty(partsOfLine[3])
                        .buildDate(partsOfLine[4])
                        .buildRating(ratingCount(filmRatingList, partsOfLine[1]))
                        .build();

                listFilm.insert(film);
            }

            listFilm.print();
            bufferedReader.close();
            fileReader.close();

        } catch(IOException e){
            try {
                assert bufferedReader != null;
                bufferedReader.close();
                fileReader.close();
            } catch (Exception er) {
                System.out.println("Произошла ошибка");
            }
            throw new RuntimeException("Такой файл не найден");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return listFilm;
    }

    public static void writeDataToFileFilm(List<Film> listFilm, String fileName) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            bufferedWriter = new BufferedWriter(fileWriter);

            Film[] films = listFilm.getAll();

            for (int i = 0; i < listFilm.getSize(); i++) {
                bufferedWriter.write(films[i] + "\n");
            }

            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<FilmRating> readFileFilmRating(String fileName) {
        List<FilmRating> listFilmRating = new List<>(new FilmRating[20]);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            bufferedReader.readLine();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] partsOfLine = getPartsOfLine(line);


                FilmRating filmRating = new FilmRating(Integer.parseInt(partsOfLine[1]), partsOfLine[2], partsOfLine[0], Integer.parseInt(partsOfLine[3]));


                listFilmRating.insert(filmRating);
            }

            listFilmRating.print();
            bufferedReader.close();
            fileReader.close();
            return listFilmRating;

        } catch (IOException e) {
            try {
                assert bufferedReader != null;
                bufferedReader.close();
                fileReader.close();
            } catch (Exception er) {
                System.out.println("Произошла ошибка");
            }
            throw new RuntimeException("Такой файл не найден");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return listFilmRating;
    }

    public static void writeDataToFileFilmRating(List<FilmRating> listFilmRating, String fileName) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            bufferedWriter = new BufferedWriter(fileWriter);

            FilmRating[] filmRatings = listFilmRating.getAll();

            for (int i = 0; i < listFilmRating.getSize(); i++) {
                bufferedWriter.write(filmRatings[i] + "\n");
            }

            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int ratingCount(List<FilmRating> filmRatingList, String title) {
        int count = 0;
        int sumRating = 0;

        FilmRating[] filmRatings = filmRatingList.getAll();

        for (int i = 0; i < filmRatings.length && filmRatings[i] != null; i++) {
            if(filmRatings[i].getTitle().equals(title)) {
                sumRating += filmRatings[i].getRating();
                ++count;
            }
        }
        if (count == 0) {
            return 0;
        }

        return (int) sumRating/count;
    }
    public static List<PersonalFilm> readFilePersonalFilm(String fileName) {
        List<PersonalFilm> listPersonalFilm = new List<>(new PersonalFilm[20]);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            bufferedReader.readLine();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] partsOfLine = getPartsOfLine(line);


                PersonalFilm filmRating = new PersonalFilm(partsOfLine[0], partsOfLine[1]);


                listPersonalFilm.insert(filmRating);
            }

            listPersonalFilm.print();
            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            try {
                assert bufferedReader != null;
                bufferedReader.close();
                fileReader.close();
            } catch (Exception er) {
                System.out.println("Произошла ошибка");
            }
            throw new RuntimeException("Такой файл не найден");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return listPersonalFilm;
    }

    public static void writeDataToFilePersonalFilm(List<PersonalFilm>  listPersonalFilm, String fileName) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            bufferedWriter = new BufferedWriter(fileWriter);

            PersonalFilm[] personalFilms = listPersonalFilm.getAll();

            for (int i = 0; i < listPersonalFilm.getSize(); i++) {
                bufferedWriter.write(personalFilms[i] + "\n");
            }

            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
//public class FileUtils {
//
//    public static <T> List<T> readFile(String fileName) {
//        try (FileInputStream fileIn = new FileInputStream(fileName); ObjectInputStream objectIn =
//                new ObjectInputStream(fileIn)) {
//            Object object = objectIn.readObject();
//            return (List<T>) object;
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException ex) {
//
//            var file = new File(fileName);
//            try {
//                boolean exists = file.exists();
//
//                if (!exists) {
//                    var newFile = file.createNewFile();
//
//                    if (!newFile) {
//                        System.out.println("Что-то пошло не так при создании файла");
//                        throw new RuntimeException("умер");
//                    } else {
//                        if (fileName.contains("users")) {
//                            var users = new List<User>(new User[20]);
//                            users.insert(new AdminUser(
//                                    "noName",
//                                            "infinity",
//                                            "qwerty", UserRole.ADMIN));
//
//
//                            writeDataToFile(users, fileName);
//
//                            return readFile(fileName);
//                        }
//                    }
//                }
//
//            } catch (IOException exc) {
//                System.out.println(exc.getMessage());
//            }
//        }
//        return null;
//    }
//
//    private static UserRole defineUserRole(String[] partsOfLine) {
//        return switch (partsOfLine[3]) {
//            case "COMMON" -> UserRole.COMMON;
//            case "ADMIN" -> UserRole.ADMIN;
//            default -> throw new IllegalArgumentException("Неверные данные");
//        };
//    }
//
//    public static <T extends Serializable> void writeDataToFile(List<T> list, String fileName)
//            throws IOException {
//        File file = Paths.get(fileName).toFile();
//        if (!file.exists() && !file.createNewFile()) {
//            throw new IOException("Не удалось создать файл " + fileName);
//        } try {
//            FileOutputStream fileOut = new FileOutputStream(file);
//            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//            objectOut.writeObject(list);
//            objectOut.close();
//            fileOut.close();
//        } catch (IOException e) {
//            //noinspection CallToPrintStackTrace
//            e.printStackTrace();
//        }
//    }
//}
