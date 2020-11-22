package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import main.CustomComparatorAwards;
import utils.Utils;

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

    Database data = new Database();
    ArrayList<String> result = new ArrayList<>();
    Util util = new Util();
    Movie movie;
    Show serial;
    // Formarea unui hashtable de users
    data.initUsers(input);
    // Formarea unui hashtable de filme
    data.initMovies(input);
    // Formarea unui Hashtable de seriale
    data.initShows(input);
    // Formarea unui ArrayList de actori
    data.initActors(input);
    // Parcurgerea comenzilor
    for (int i = 0; i < input.getCommands().size(); i++) {
      // FAVORITE
      if (input.getCommands().get(i).getType() != null &&
              input.getCommands().get(i).getType().equals("favorite") &&
              input.getCommands().get(i).getActionType().equals("command")) {
        output = data.getUsers().get(input.getCommands().get(i).getUsername()).
                addFavourite(input.getCommands().get(i).getTitle());
        if (output != null) {
          arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));

        }
      } else if (input.getCommands().get(i).getType() != null && // VIEW
                  input.getCommands().get(i).getType().equals("view") &&
                  input.getCommands().get(i).getActionType().equals("command")) {
        output = data.getUsers().get(input.getCommands().get(i).getUsername()).
                addView(input.getCommands().get(i).getTitle());
        if (output != null) {
          arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
        }
      } else if (input.getCommands().get(i).getType() != null && // RATING
                  input.getCommands().get(i).getType().equals("rating") &&
                  input.getCommands().get(i).getActionType().equals("command")) {

        if (data.getMovies().get(input.getCommands().get(i).getTitle()) != null) { // PT FILME
          movie = data.getMovies().get(input.getCommands().get(i).getTitle());
          output = movie.addRating(input.getCommands().get(i).getGrade(), input.getCommands().get(i).getUsername(), data.getUsers());

        } else if (data.getShows().get(input.getCommands().get(i).getTitle()) != null) { // PT SERIALE
          serial = data.getShows().get(input.getCommands().get(i).getTitle());
          output = serial.addRating(input.getCommands().get(i).getGrade(), input.getCommands().get(i).getSeasonNumber(),
                  input.getCommands().get(i).getUsername(), data.getUsers());
        }
        if (output != null) {
          arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
        }
      } else if (input.getCommands().get(i).getActionType() != null && // AVERAGE ACTORI
                  input.getCommands().get(i).getCriteria() != null &&
                  input.getCommands().get(i).getCriteria().equals("average") &&
                  input.getCommands().get(i).getActionType().equals("query")) {
        output = Constants.queryResult + util.averageActors(input, data, i);
        if (output != null) {
          arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
        }
      } else if (input.getCommands().get(i).getActionType() != null && // AWARDS ACTORI
              input.getCommands().get(i).getCriteria() != null &&
              input.getCommands().get(i).getCriteria().equals("awards") &&
              input.getCommands().get(i).getActionType().equals("query")) {
        output = Constants.queryResult + util.awardActors(input, data, i);

        if (output != null) {
          arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
        }
      } else if (input.getCommands().get(i).getActionType() != null && // FILTER ACTORI
              input.getCommands().get(i).getCriteria() != null &&
              input.getCommands().get(i).getCriteria().equals("filter_description") &&
              input.getCommands().get(i).getActionType().equals("query")) {
        output = Constants.queryResult + util.filteredActors(input, data, i);
        if (output != null) {
          arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
        }
      } else if (input.getCommands().get(i).getActionType() != null && // RATING videos
              input.getCommands().get(i).getCriteria() != null &&
              input.getCommands().get(i).getActionType().equals("query") &&
              input.getCommands().get(i).getCriteria().equals("ratings")) {
        output = Constants.queryResult + util.videoRating(input, data, i);
        arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
      } else if (input.getCommands().get(i).getActionType() != null && // fav videos
              input.getCommands().get(i).getCriteria() != null &&
              input.getCommands().get(i).getActionType().equals("query") &&
              input.getCommands().get(i).getCriteria().equals("favorite")) {
        if (input.getCommands().get(i).getObjectType().equals("movies")) {
          result = util.favMovies(input, data, i);
        } else if (input.getCommands().get(i).getObjectType().equals("shows")) {
          result = util.favShows(input, data, i);
        }
        if (result != null) {
          output = Constants.queryResult + result;
        }
        arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
      } else if (input.getCommands().get(i).getActionType() != null && // longest videos
              input.getCommands().get(i).getCriteria() != null &&
              input.getCommands().get(i).getActionType().equals("query") &&
              input.getCommands().get(i).getCriteria().equals("longest")) {
        if (input.getCommands().get(i).getObjectType().equals("movies")) {
          output = Constants.queryResult + util.longestMovie(input, data, i);
        } else if (input.getCommands().get(i).getObjectType().equals("shows")) {
          output = Constants.queryResult + util.longestShow(input, data, i);
        }
        arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
      } else if (input.getCommands().get(i).getActionType() != null && // most viewed videos
              input.getCommands().get(i).getCriteria() != null &&
              input.getCommands().get(i).getActionType().equals("query") &&
              input.getCommands().get(i).getCriteria().equals("most_viewed")) {
        if (input.getCommands().get(i).getObjectType().equals("movies")) {
          result = util.mostViewedMovies(input, data, i);
        } else if (input.getCommands().get(i).getObjectType().equals("shows")) {
          result = util.mostViewedShows(input, data, i);
        }
        output = Constants.queryResult + result;
        arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
      } else if (input.getCommands().get(i).getActionType() != null && // user ratings
              input.getCommands().get(i).getCriteria() != null &&
              input.getCommands().get(i).getActionType().equals("query") &&
              input.getCommands().get(i).getCriteria().equals("num_ratings")) {
        output = Constants.queryResult + util.userRatings(input, data, i);
        arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
      } else if (input.getCommands().get(i).getActionType() != null &&    // recommended standard
              input.getCommands().get(i).getType() != null &&
              input.getCommands().get(i).getActionType().equals("recommendation") &&
              input.getCommands().get(i).getType().equals("standard")) {
        output = null;
        int flag = 0;
        String recommended = null;

        if (data.getUsers().get(input.getCommands().get(i).getUsername()) == null) {
          output = Constants.standardRecommendation + Constants.applied;
          flag = 2;
        }
        if (flag == 0) {
          for (Movie m : data.getMovies().values()) {
            if (data.getUsers().get(input.getCommands().get(i).getUsername()).getViews().get(m.getTitle()) == null) {
              // System.out.println("Utilizatorul " + input.getCommands().get(i).getUsername() + " n a vazut " + );
              flag = 1;
              recommended = m.getTitle();
              break;
            }
          }
        }

        if (flag == 0) {
          for (Show sh : data.getShows().values()) {
            if (data.getUsers().get(input.getCommands().get(i).getUsername()).getViews().get(sh.getTitle()) == null) {
              flag = 1;
              recommended = sh.getTitle();
              break;
            }
          }
        }

        if (flag == 1) {
          output = Constants.standardRecommendation + Constants.result + recommended;
        }
        if (output == null) {
          output = Constants.standardRecommendation + Constants.applied;
        }
        arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
      } else if (input.getCommands().get(i).getActionType() != null &&    // recommended best
              input.getCommands().get(i).getType() != null &&
              input.getCommands().get(i).getActionType().equals("recommendation") &&
              input.getCommands().get(i).getType().equals("best_unseen")) {
        ArrayList<Movie> unseeenMovies = new ArrayList<>();
        ArrayList<Show> unseenShows = new ArrayList<>();
        for (Movie m : data.getMovies().values()) {
          if (data.getUsers().get(input.getCommands().get(i).getUsername()).getViews().get(m.getTitle()) == null) {
            m.setCriteria("ratings_recom");
            unseeenMovies.add(m);
            double rating = unseeenMovies.get(unseeenMovies.size() - 1).makeMovieRating();
            unseeenMovies.get(unseeenMovies.size() - 1).setVideoRating(rating);
          }
        }

        for (Show sh : data.getShows().values()) {
          if (data.getUsers().get(input.getCommands().get(i).getUsername()).getViews().get(sh.getTitle()) == null) {
            sh.setCriteria("ratings_recom");
            unseenShows.add(sh);
            double rating = unseenShows.get(unseenShows.size() - 1).makeShowRating();
            unseenShows.get(unseenShows.size() - 1).setVideoRating(rating);
          }
        }
        Collections.sort(unseeenMovies, Collections.reverseOrder());
        Collections.sort(unseenShows, Collections.reverseOrder());
        // System.out.println("Pt userul " + input.getCommands().get(i).getUsername() + " filmele sunt " + unseeenMovies + " iar serialele sunt " + unseenShows);
        if (unseeenMovies.size() != 0 && unseenShows.size() != 0) {
         // System.out.println("Pt " + input.getCommands().get(i).getUsername() + " filmul este " + unseeenMovies.get(0).getTitle() + " iar serialul este " + unseenShows.get(0).getTitle());
          if (unseenShows.get(0).getVideoRating() > unseeenMovies.get(0).getVideoRating()) {
            output = Constants.bestRatedRecommendation + Constants.result + unseenShows.get(0).getTitle();
          } else {
            output = Constants.bestRatedRecommendation + Constants.result + unseeenMovies.get(0).getTitle();
          }
        } else if (unseeenMovies.size() != 0) {
          output = Constants.bestRatedRecommendation + Constants.result + unseeenMovies.get(0).getTitle();
        } else if (unseenShows.size() != 0) {
          output = Constants.bestRatedRecommendation + Constants.result + unseenShows.get(0).getTitle();
        } else {
          output = Constants.bestRatedRecommendation + Constants.applied;
        }
        arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
      } else if (input.getCommands().get(i).getActionType() != null &&    // popular recom
              input.getCommands().get(i).getType() != null &&
              input.getCommands().get(i).getActionType().equals("recommendation") &&
              input.getCommands().get(i).getType().equals("popular")) {
        int flag = 0;
        String recommended = null;
        // System.out.println("userul " + input.getCommands().get(i).getUsername() + " este " + data.getUsers().get(input.getCommands().get(i).getUsername()).getCategory());
        if (data.getUsers().get(input.getCommands().get(i).getUsername()) == null
        || data.getUsers().get(input.getCommands().get(i).getUsername()).getCategory().equals("BASIC")) {
          // System.out.println("intra pt userul " + input.getCommands().get(i).getUsername());
          output = Constants.popularRecommendation + Constants.applied;
        } else {
          ArrayList<Movie> unseenMovies = new ArrayList<>();
          ArrayList<Show> unseenShows = new ArrayList<>();
          HashMap<String, Genres> popularGenres = new HashMap<String, Genres>();
          for (Movie m : data.getMovies().values()) {
            if (data.getUsers().get(input.getCommands().get(i).getUsername()).getViews().get(m.getTitle()) == null) {
              unseenMovies.add(m);
              for (String s : m.getGenres()) {
                if (popularGenres.get(s) != null) {
                  popularGenres.get(s).setApparitions(popularGenres.get(s).getApparitions() + 1);
                  popularGenres.get(s).setApparitions(popularGenres.get(s).getApparitions() + 1);
                } else {
                  Genres g = new Genres(s, 1);
                  popularGenres.put(g.getName(), g);
                }
              }
            }
          }

          for (Show sh : data.getShows().values()) {
            if (data.getUsers().get(input.getCommands().get(i).getUsername()).getViews().get(sh.getTitle()) == null) {
              unseenShows.add(sh);
              for (String s : sh.getGenres()) {
                if (popularGenres.get(s) != null) {
                  popularGenres.get(s).setApparitions(popularGenres.get(s).getApparitions() + 1);
                  popularGenres.get(s).setApparitions(popularGenres.get(s).getApparitions() + 1);
                } else {
                  Genres g = new Genres(s, 1);
                  popularGenres.put(g.getName(), g);
                }
              }
            }
          }
          ArrayList<Genres> genreOrder = new ArrayList<Genres>(popularGenres.values());
          Collections.sort(genreOrder, Collections.reverseOrder());
          flag = 0;

          for (Genres g : genreOrder) {
            for (Movie m : unseenMovies) {
              if (m.getGenres().contains(g.getName())) {
                flag = 1;
                output = Constants.popularRecommendation + Constants.result + m.getTitle();
                break;
              }
            }
            if (flag == 1) {
              break;
            }
            for (Show sh : unseenShows) {
              if (sh.getGenres().contains(g.getName())) {
                flag = 1;
                output = Constants.popularRecommendation + Constants.result + sh.getTitle();
                break;
              }
              if (flag == 1) {
                break;
              }
            }
          }

          if (flag == 0) {
            output = Constants.popularRecommendation + Constants.applied;
          }
        }
        // System.out.println("pt userul " + input.getCommands().get(i).getUsername() + " outputul este " + output);
        arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
      } else if (input.getCommands().get(i).getActionType() != null &&    // fav recom
              input.getCommands().get(i).getType() != null &&
              input.getCommands().get(i).getActionType().equals("recommendation") &&
              input.getCommands().get(i).getType().equals("favorite")) {
        User user = data.getUsers().get(input.getCommands().get(i).getUsername());
        ArrayList<Movie> unseenMovies = new ArrayList<>();
        ArrayList<Show> unseenShows = new ArrayList<>();
        if (user == null || user.getCategory().equals("BASIC")) {
          output = Constants.favoriteRecommendation + Constants.applied;
        } else {
       //   System.out.println("PT USERUL " + user.getUsername());
          for (Movie m : data.getMovies().values()) {
            if (user.getViews().get(m.getTitle()) == null) {
              m.setNoFavs(0);
              unseenMovies.add(m);
              if (m.getTitle().equals("Ed Wood")) {
             //   System.out.println("la ed wood la adaugare " + m.getNoFavs());
              }
            }
          }

          for (Show sh : data.getShows().values()) {
            if (user.getViews().get(sh.getTitle()) == null) {
              sh.setNoFavs(0);
              unseenShows.add(sh);
            }
          }
         // System.out.println("Userul " + user.getUsername() + " n a vazut " + unseenMovies);
          for (User u : data.getUsers().values()) {
            for (String s : u.getFavs()) {
              for (Movie m : unseenMovies) {
                m.setCriteria("favorite_recom");
                if (m.getTitle().equals(s)) {
           //       System.out.println("Current user inainte de if " + user.getUsername() + " la filmul " + m.getTitle() + " e la fav de " + m.getNoFavs());
                  m.addFav();
             //     System.out.println("Current user " + user.getUsername() + " la filmul " + m.getTitle() + " e la fav de " + m.getNoFavs());
                }
              }
            }
          }

          for (User u : data.getUsers().values()) {
            for (String s : u.getFavs()) {
              for (Show sh : unseenShows) {
                sh.setCriteria("favorite_recom");
                if (sh.getTitle().equals(s)) {
                  sh.addFav();
                }
              }
            }
          }

          for (int j = 0; j < unseenMovies.size(); j++) {
            if (unseenMovies.get(j).getNoFavs() == 0) {
              unseenMovies.remove(j);
              j--;
            }
          }
          for (int j = 0; j < unseenShows.size(); j++) {
            if (unseenShows.get(j).getNoFavs() == 0) {
              unseenShows.remove(j);
              j--;
            }
          }
          Collections.sort(unseenMovies, Collections.reverseOrder());
          Collections.sort(unseenShows, Collections.reverseOrder());

        //    System.out.println("Pt userul " + user.getUsername() + " filmele recom fav sunt " + unseenMovies + " si serialele sunt " + unseenShows);
     //     for (Movie m : unseenMovies) {
          //  System.out.println(m.getTitle() +" " + m.getNoFavs());
       //   }
         // for (User u : data.getUsers().values()) {
           // System.out.println(u.getFavs());
       //   }
          if (unseenMovies.size() != 0) {
            if (unseenShows.size() != 0 && unseenMovies.get(0).getNoFavs() < unseenShows.get(0).getNoFavs()) {
              output = Constants.favoriteRecommendation + Constants.result + unseenShows.get(0).getTitle();
            } else {
              output = Constants.favoriteRecommendation + Constants.result + unseenMovies.get(0).getTitle();
            }
          } else {
            if (unseenShows.size() != 0) {
              output = Constants.favoriteRecommendation + Constants.result + unseenShows.get(0).getTitle();
            } else {
              output =  Constants.favoriteRecommendation + Constants.applied;
            }
          }
        }
        arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
      } else if (input.getCommands().get(i).getActionType() != null &&    // search recom
              input.getCommands().get(i).getType() != null &&
              input.getCommands().get(i).getActionType().equals("recommendation") &&
              input.getCommands().get(i).getType().equals("search")) {
        String genre = input.getCommands().get(i).getGenre();
        User user = data.getUsers().get(input.getCommands().get(i).getUsername());
        if (user == null || user.getCategory().equals("BASIC")) {
          output = Constants.searchRecommendation + Constants.applied;
        } else {
          ArrayList<Movie> unseenMovies = new ArrayList<>();
          ArrayList<Show> unseenShows = new ArrayList<>();
          ArrayList<Video> unseenVideos = new ArrayList<>();

          for (Movie m : data.getMovies().values()) {
            if (m.getGenres().contains(genre)) {
              if (!user.getViews().containsKey(m.getTitle())) {
                unseenMovies.add(m);
              }
            }
          }

          for (Show sh : data.getShows().values()) {
            if (sh.getGenres().contains(genre)) {
              if (!user.getViews().containsKey(sh.getTitle())) {
                unseenShows.add(sh);
              }
            }
          }

          for (Movie m : unseenMovies) {
            Video v = new Video(m.getTitle(), m.getYear(), m.getGenres(), m.getCast(), m.getDuration());
            v.setVideoRating(m.makeMovieRating());
            v.setCriteria("ratings");
            unseenVideos.add(v);
          }

          for (Show sh : unseenShows) {
            Video v = new Video(sh.getTitle(), sh.getYear(), sh.getGenres(), sh.getCast(), sh.getDuration());
            v.setVideoRating(sh.makeShowRating());
            v.setCriteria("ratings");
            unseenVideos.add(v);
          }

          Collections.sort(unseenVideos);
          result = new ArrayList<>();
          if (unseenVideos.size() != 0) {
            for (Video v : unseenVideos) {
              result.add(v.getTitle());
            }

            output = Constants.searchRecommendation + Constants.result + result;
          } else {
            output = Constants.searchRecommendation + Constants.applied;
          }
        }
        arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, output));
      }
    }
    fileWriter.closeJSON(arrayResult);
  }
}
