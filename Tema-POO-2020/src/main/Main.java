package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import main.CustomComparatorAwards;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
  /**
   * for coding style
   */
  private Main() {
  }

  /**
   * Call the main checker and the coding style checker
   * @param args from command line
   * @throws IOException in case of exceptions to reading / writing
   */
  public static void main(final String[] args) throws IOException {
    File directory = new File(Constants.TESTS_PATH);
    Path path = Paths.get(Constants.RESULT_PATH);
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }

    File outputDirectory = new File(Constants.RESULT_PATH);

    Checker checker = new Checker();
    checker.deleteFiles(outputDirectory.listFiles());

    for (File file : Objects.requireNonNull(directory.listFiles())) {

      String filepath = Constants.OUT_PATH + file.getName();
      File out = new File(filepath);
      boolean isCreated = out.createNewFile();
      if (isCreated) {
        action(file.getAbsolutePath(), filepath);
      }
    }

    checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
    Checkstyle test = new Checkstyle();
    test.testCheckstyle();
  }

  /**
   * @param filePath1 for input file
   * @param filePath2 for output file
   * @throws IOException in case of exceptions to reading / writing
   */
  public static void action(final String filePath1,
                            final String filePath2) throws IOException {
    InputLoader inputLoader = new InputLoader(filePath1);
    Input input = inputLoader.readData();

    Writer fileWriter = new Writer(filePath2);
    JSONArray arrayResult = new JSONArray();

    //TODO add here the entry point to your implementation

    String output = null;

    int n;

    Hashtable<String, User> users = new Hashtable<String, User>();
    Hashtable<String, Movie> movies = new Hashtable<String, Movie>();
    Hashtable<String, Show> shows = new Hashtable<String, Show>();
    ArrayList<Actor> actors = new ArrayList<>();
    ArrayList<Actor> aux = new ArrayList<>();
    ArrayList<String> average = new ArrayList<>();
    List<String> awards;
    List<List<String>> filters;
    User user;
    Movie movie;
    Show serial;
    Actor actor;
    int flag;

    // Formarea unui hashtable de users
    for (int i = 0; i < input.getUsers().size(); i++) {
      user = new User(input.getUsers().get(i).getUsername(),
              input.getUsers().get(i).getSubscriptionType(),
              input.getUsers().get(i).getFavoriteMovies(),
              input.getUsers().get(i).getHistory());
      users.put(user.getUsername(), user);
    }

    // Formarea unui hashtable de filme
    for (int i = 0; i < input.getMovies().size(); i++) {
      movie = new Movie(input.getMovies().get(i).getTitle(),
              input.getMovies().get(i).getYear(),
              input.getMovies().get(i).getGenres(),
              input.getMovies().get(i).getCast(),
              input.getMovies().get(i).getDuration());
      movies.put(movie.getTitle(), movie);
    }

    // Formarea unui Hashtable de seriale
    for (int i = 0; i < input.getSerials().size(); i++) {
      // System.out.println("formeaza ht de seriale");
      serial = new Show(input.getSerials().get(i).getTitle(),
              input.getSerials().get(i).getYear(),
              input.getSerials().get(i).getGenres(),
              input.getSerials().get(i).getCast(),
              input.getSerials().get(i).getSeasons());
      shows.put(serial.getTitle(), serial);
    }

    // Formarea unui ArrayList de actori
    for (int i = 0; i < input.getActors().size(); i++) {
      actor = new Actor(input.getActors().get(i).getName(),
              input.getActors().get(i).getCareerDescription(),
              input.getActors().get(i).getFilmography(),
              input.getActors().get(i).getAwards());
      actors.add(actor);
    }

    // Parcurgerea comenzilor
    for (int i = 0; i < input.getCommands().size(); i++) {
      // System.out.println(input.getCommands().get(i).getType());
      // FAVORITE
      if (input.getCommands().get(i).getType() != null &&
              input.getCommands().get(i).getType().equals("favorite") &&
              input.getCommands().get(i).getActionType().equals("command")) {
        // System.out.println("intra in if");
        output = users.get(input.getCommands().get(i).getUsername()).
                addFavourite(input.getCommands().get(i).getTitle());

        if (output != null) {
          arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));

        }

      } else if (input.getCommands().get(i).getType() != null && // VIEW
                  input.getCommands().get(i).getType().equals("view") &&
                  input.getCommands().get(i).getActionType().equals("command")) {
        output = users.get(input.getCommands().get(i).getUsername()).
                addView(input.getCommands().get(i).getTitle());

        if (output != null) {
          arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
        }
      } else if (input.getCommands().get(i).getType() != null && // RATING
                  input.getCommands().get(i).getType().equals("rating") &&
                  input.getCommands().get(i).getActionType().equals("command")) {

        if (movies.get(input.getCommands().get(i).getTitle()) != null) { // PT FILME
          movie = movies.get(input.getCommands().get(i).getTitle());
          output = movie.addRating(input.getCommands().get(i).getGrade(), input.getCommands().get(i).getUsername(), users);

        } else if (shows.get(input.getCommands().get(i).getTitle()) != null) { // PT SERIALE
          serial = shows.get(input.getCommands().get(i).getTitle());
          output = serial.addRating(input.getCommands().get(i).getGrade(), input.getCommands().get(i).getSeasonNumber(),
                  input.getCommands().get(i).getUsername(), users);
        }

        if (output != null) {
          arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
        }
      } else if (input.getCommands().get(i).getActionType() != null && // AVERAGE ACTORI
                  input.getCommands().get(i).getCriteria() != null &&
                  input.getCommands().get(i).getCriteria().equals("average") &&
                  input.getCommands().get(i).getActionType().equals("query")) {

        n = input.getCommands().get(i).getNumber();

        aux = new ArrayList<>();
        aux.addAll(actors);

        for (Actor a : aux) {
          a.setQueryCriteria("average");
        }

        for (int j = 0; j < aux.size(); j++) {
          aux.get(j).makeRating(movies, shows);

          if (aux.get(j).getRating() == -1) {
            aux.remove(j);
            j--;
          }
        }

        if (input.getCommands().get(i).getSortType().equals("asc")) {
          Collections.sort(aux);
        } else if (input.getCommands().get(i).getSortType().equals("desc")) {
          Collections.sort(aux, Collections.reverseOrder());
        }

        for (int nr = 0; nr < n && nr < aux.size(); nr++) {
          average.add(aux.get(nr).getName());
        }

        output = Constants.queryResult + average;

        if (output != null) {
          arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
        }
       // System.out.println(output);
      } else if (input.getCommands().get(i).getActionType() != null && // AWARDS ACTORI
              input.getCommands().get(i).getCriteria() != null &&
              input.getCommands().get(i).getCriteria().equals("awards") &&
              input.getCommands().get(i).getActionType().equals("query")) {
        aux = new ArrayList<>();
        awards = new ArrayList<>();
        filters = input.getCommands().get(i).getFilters();
        awards = filters.get(3);

        for (Actor a : actors) {
          flag = 1;
          a.setRelevantAwards(awards);
          a.setQueryCriteria("awards");
          for (String s : awards) {
            if (a.getAwards().get(s) == null) {
              flag = 0;
            }
          }

          if (flag == 1) {
            aux.add(a);
          }
        }

        if (input.getCommands().get(i).getSortType().equals("asc")) {
          Collections.sort(aux);
        } else if (input.getCommands().get(i).getSortType().equals("desc")) {
          Collections.sort(aux, Collections.reverseOrder());
        }

        output = Constants.queryResult + aux;

        if (output != null) {
          arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
        }

      } else if (input.getCommands().get(i).getActionType() != null && // FILTER ACTORI
              input.getCommands().get(i).getCriteria() != null &&
              input.getCommands().get(i).getCriteria().equals("filter_description") &&
              input.getCommands().get(i).getActionType().equals("query")) {

      }
    }

    fileWriter.closeJSON(arrayResult);
  }
}
