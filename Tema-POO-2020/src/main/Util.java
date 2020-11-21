package main;

import common.Constants;
import fileio.Input;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {
  public Util() {

  }

  public ArrayList<String> averageActors(Input input, Database data, int i) {
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> average = new ArrayList<>();

    ArrayList<Actor> aux = new ArrayList<>();
    aux.addAll(data.getActors());

    for (Actor a : aux) {
      a.setQueryCriteria("average");
    }

    for (int j = 0; j < aux.size(); j++) {
      aux.get(j).makeRating(data.getMovies(), data.getShows());

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
    return average;
  }

  public ArrayList<String> awardActors(Input input, Database data, int i) {
    int flag;
    ArrayList<Actor> aux = new ArrayList<>();
    List<String> awards = new ArrayList<>();
    ArrayList<String> awardedActors = new ArrayList<>();
    List<List<String>> filters = input.getCommands().get(i).getFilters();
    awards = filters.get(3);

    // System.out.println("Premiile cautate sunt " + awards);

    for (Actor a : data.getActors()) {
      flag = 1;
      a.setRelevantAwards(awards);
      a.setQueryCriteria("awards");
      // System.out.println("Premiile actorului " + a.getName() + " sunt " + a.getAwards());
      for (String s : awards) {
        if (!a.getAwards().containsKey(s)) {
         //  System.out.println("Actorul " + a.getName() + " nu are premiul " + s);
          flag = 0;
        } else {
          // System.out.println("Actorul " + a.getName() + " are premiul " + s);
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

    for (Actor a : aux) {
      awardedActors.add(a.getName());
    }
    return awardedActors;
  }

  public ArrayList<String> filteredActors(Input input, Database data, int i) {
    List<List<String>> filters = input.getCommands().get(i).getFilters();
    List<String> words = filters.get(2);
    System.out.println("the words are " + words);
    ArrayList<Actor> aux = new ArrayList<>();
    int flag;
    ArrayList<String> filteredActors = new ArrayList<>();

    for (Actor a : data.getActors()) {
      a.setCareerDesc(a.getCareerDesc().toLowerCase());
      flag = 1;
      for (String s : words) {
        if (!a.getCareerDesc().contains(s)) {
          System.out.println("Actorul " + a.getName() + " nu are cuvantul " + s);
          flag = 0;
        } else {
          System.out.println("Actorul " + a.getName() + " are cuvantul " + s);
          int index = a.getCareerDesc().indexOf(s);
          while (index != -1 && index < a.getCareerDesc().length()) {
            if (index > 0) {
              if (Constants.alphabet.indexOf(a.getCareerDesc().charAt(index - 1)) != -1) {
                System.out.println("Actorul " + a.getName() + " nu are cuvantul " + s + " deoarece e in mijl altui cuvant");
                System.out.println(a.getCareerDesc().charAt(index - 1));
              } else if (index + s.length() < a.getCareerDesc().length() && Constants.alphabet.indexOf(a.getCareerDesc().charAt(index + s.length())) != -1) {
                System.out.println("Actorul " + a.getName() + " nu are cuvantul " + s + " deoarece e in mijl altui cuvant partea a 2a");
                System.out.println(a.getCareerDesc().charAt(index + s.length()));
              } else {
                System.out.println("Actorul " + a.getName() + " are de fapt cuvantul " + s);
                break;
              }
            }
            index = a.getCareerDesc().indexOf(s, index + 1);
            if (index == -1) {
              flag = 0;
            }
         }
        }
      }

      if (flag == 1) {
        a.setQueryCriteria("filter_description");
        aux.add(a);
      }
    }

    if (input.getCommands().get(i).getSortType().equals("asc")) {
      Collections.sort(aux);
    } else if (input.getCommands().get(i).getSortType().equals("desc")) {
      Collections.sort(aux, Collections.reverseOrder());
    }

    for (Actor a : aux) {
      filteredActors.add(a.getName());
    }

    return filteredActors;
  }

  public ArrayList<String> videoRating(Input input, Database data, int i) {
    ArrayList<Movie> movieList = new ArrayList<Movie>(data.getMovies().values());
    ArrayList<Show> showList = new ArrayList<Show>(data.getShows().values());

    MovieFilters mfilter = new MovieFilters();
    ShowFilters sfilter = new ShowFilters();
    ArrayList<String> result = new ArrayList<>();

    int n = input.getCommands().get(i).getNumber();

    if (input.getCommands().get(i).getObjectType().equals("movie")) {
      mfilter.setVideos(movieList);
      if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
        mfilter.setVideos(mfilter.filterByYear(Integer.parseInt(input.getCommands().get(i).getFilters().get(0).get(0))));
      }
      if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
        mfilter.setVideos(mfilter.filterByGenre(input.getCommands().get(i).getFilters().get(1).get(0)));
      }
    } else {
      sfilter.setVideos(showList);
      if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
        sfilter.setVideos(sfilter.filterByYear(Integer.parseInt(input.getCommands().get(i).getFilters().get(0).get(0))));
      }
      if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
        sfilter.setVideos(sfilter.filterByGenre(input.getCommands().get(i).getFilters().get(1).get(0)));
      }
    }

    ArrayList<Movie> auxMovies = new ArrayList<>();
    ArrayList<Show> auxShows = new ArrayList<>();

    if (input.getCommands().get(i).getObjectType().equals("movie")) {
      auxMovies.addAll(mfilter.getVideos());

      for (Movie m : auxMovies) {
        m.setVideoRating(m.makeMovieRating());
      }

      for (int j = 0; j < auxMovies.size(); j++) {
        if (auxMovies.get(j).getVideoRating() == -1) {
          auxMovies.remove(j);
          j--;
        }
      }

      if (input.getCommands().get(i).getSortType().equals("asc")) {
        Collections.sort(auxMovies);
      } else if (input.getCommands().get(i).getSortType().equals("desc")) {
        Collections.sort(auxMovies, Collections.reverseOrder());
      }

      result = new ArrayList<>();
      for (int j = 0; j < n && j < auxMovies.size(); j++) {
        result.add(auxMovies.get(j).getTitle());
      }
    } else if (input.getCommands().get(i).getObjectType().equals("show")) {
      auxShows.addAll(sfilter.getVideos());

      for (Show sh : auxShows) {
        sh.setVideoRating(sh.makeShowRating());
      }

      for (int j = 0; j < auxShows.size(); j++) {
        if (auxShows.get(j).getVideoRating() == -1) {
          auxShows.remove(j);
          j--;
        }
      }

      if (input.getCommands().get(i).getSortType().equals("asc")) {
        Collections.sort(auxShows);
      } else if (input.getCommands().get(i).getSortType().equals("desc")) {
        Collections.sort(auxShows, Collections.reverseOrder());
      }
      result = new ArrayList<>();
      for (int j = 0; j < n && j < auxShows.size(); j++) {
        result.add(auxShows.get(j).getTitle());
      }
    }
    return result;
  }

  public ArrayList<String> favMovies(Input input, Database data, int i) {
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Movie> movieList = new ArrayList<Movie>(data.getMovies().values());
    MovieFilters filter = new MovieFilters();
    filter.setVideos(movieList);
    if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
      filter.setVideos(filter.filterByYear(Integer.parseInt(input.getCommands().get(i).getFilters().get(0).get(0))));
    }
    if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
      filter.setVideos(filter.filterByGenre(input.getCommands().get(i).getFilters().get(1).get(0)));
    }
    ArrayList<User> userList = new ArrayList<User>(data.getUsers().values());

    for (User u : userList) {
      for (String s : u.getFavs()) {
        for (Movie m : filter.getVideos()) {
          m.setCriteria("favorite");
          if (m.getTitle().equals(s)) {
            m.addFav();
          }
        }
      }
    }

    movieList = filter.getVideos();

    for (int j = 0; j < movieList.size(); j++) {
      if (movieList.get(j).getNoFavs() == 0) {
        movieList.remove(j);
        j--;
      }
    }

    if (input.getCommands().get(i).getSortType().equals("asc")) {
      Collections.sort(movieList);
    } else if (input.getCommands().get(i).getSortType().equals("desc")) {
      Collections.sort(movieList, Collections.reverseOrder());
    }
    result = new ArrayList<>();
    for (int j = 0; j < n && j < movieList.size(); j++) {
      result.add(movieList.get(j).getTitle());
    }
    return result;
  }

  public ArrayList<String> favShows(Input input, Database data, int i) {
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Show> showList = new ArrayList<Show>(data.getShows().values());
    ShowFilters filter = new ShowFilters();
    filter.setVideos(showList);

    if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
      filter.setVideos(filter.filterByYear(Integer.parseInt(input.getCommands().get(i).getFilters().get(0).get(0))));
    }
    if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
      filter.setVideos(filter.filterByGenre(input.getCommands().get(i).getFilters().get(1).get(0)));
    }
    ArrayList<User> userList = new ArrayList<User>(data.getUsers().values());

    for (User u : userList) {
      for (String s : u.getFavs()) {
        for (Show sh : filter.getVideos()) {
          sh.setCriteria("favorite");
          if (sh.getTitle().equals(s)) {
            sh.addFav();
          }
        }
      }
    }

    showList = filter.getVideos();

    for (int j = 0; j < showList.size(); j++) {
      if (showList.get(j).getNoFavs() == 0) {
        showList.remove(j);
        j--;
      }
    }

    if (input.getCommands().get(i).getSortType().equals("asc")) {
      Collections.sort(showList);
    } else if (input.getCommands().get(i).getSortType().equals("desc")) {
      Collections.sort(showList, Collections.reverseOrder());
    }

    result = new ArrayList<>();
    for (int j = 0; j < n && j < showList.size(); j++) {
      result.add(showList.get(j).getTitle());
    }
    return result;
  }

  public ArrayList<String> longestMovie(Input input, Database data, int i) {
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Movie> movieList = new ArrayList<Movie>(data.getMovies().values());
    MovieFilters filter = new MovieFilters();
    filter.setVideos(movieList);

    if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
      filter.setVideos(filter.filterByYear(Integer.parseInt(input.getCommands().get(i).getFilters().get(0).get(0))));
    }
    if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
      filter.setVideos(filter.filterByGenre(input.getCommands().get(i).getFilters().get(1).get(0)));
    }

    movieList = filter.getVideos();

    for (Movie m : movieList) {
      m.setCriteria("longest");
    }

    if (input.getCommands().get(i).getSortType().equals("asc")) {
      Collections.sort(movieList);
    } else if (input.getCommands().get(i).getSortType().equals("desc")) {
      Collections.sort(movieList, Collections.reverseOrder());
    }

    result = new ArrayList<>();

    for (int j = 0; j < n && j < movieList.size(); j++) {
      result.add(movieList.get(j).getTitle());
    }
    return result;
  }

  public ArrayList<String> longestShow(Input input, Database data, int i) {
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Show> showList = new ArrayList<Show>(data.getShows().values());
    ShowFilters filter = new ShowFilters();
    filter.setVideos(showList);

    if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
      filter.setVideos(filter.filterByYear(Integer.parseInt(input.getCommands().get(i).getFilters().get(0).get(0))));
    }
    if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
      filter.setVideos(filter.filterByGenre(input.getCommands().get(i).getFilters().get(1).get(0)));
    }

    showList = filter.getVideos();

    for (Show sh : showList) {
      sh.setCriteria("longest");
    }

    if (input.getCommands().get(i).getSortType().equals("asc")) {
      Collections.sort(showList);
    } else if (input.getCommands().get(i).getSortType().equals("desc")) {
      Collections.sort(showList, Collections.reverseOrder());
    }

    result = new ArrayList<>();

    for (int j = 0; j < n && j < showList.size(); j++) {
      result.add(showList.get(j).getTitle());
    }
    return result;
  }

  public ArrayList<String> mostViewedMovies(Input input, Database data, int i) {
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Movie> movieList = new ArrayList<Movie>(data.getMovies().values());
    MovieFilters filter = new MovieFilters();
    filter.setVideos(movieList);

    if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
      filter.setVideos(filter.filterByYear(Integer.parseInt(input.getCommands().get(i).getFilters().get(0).get(0))));
    }
    if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
      filter.setVideos(filter.filterByGenre(input.getCommands().get(i).getFilters().get(1).get(0)));
    }

    movieList = filter.getVideos();

    for (Movie m : movieList) {
      m.setCriteria("most_viewed");
    }

    ArrayList<User> userList = new ArrayList<User>(data.getUsers().values());

    for (User u : userList) {
      for (Movie m : movieList) {
        if (u.getViews().get(m.getTitle()) != null) {
          m.addViews(u.getViews().get(m.getTitle()));
        }
      }
    }

    for (int j = 0; j < movieList.size(); j++) {
      if (movieList.get(j).getNoViews() == 0) {
        movieList.remove(j);
        j--;
      }
    }

    if (input.getCommands().get(i).getSortType().equals("asc")) {
      Collections.sort(movieList);
    } else if (input.getCommands().get(i).getSortType().equals("desc")) {
      Collections.sort(movieList, Collections.reverseOrder());
    }

    result = new ArrayList<>();

    for (int j = 0; j < movieList.size() && j < n; j++) {
      result.add(movieList.get(j).getTitle());
    }

    return result;
  }

  public ArrayList<String> mostViewedShows(Input input, Database data, int i) {
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Show> showList = new ArrayList<>(data.getShows().values());
    ShowFilters filter = new ShowFilters();
    filter.setVideos(showList);

    if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
      filter.setVideos(filter.filterByYear(Integer.parseInt(input.getCommands().get(i).getFilters().get(0).get(0))));
    }
    if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
      filter.setVideos(filter.filterByGenre(input.getCommands().get(i).getFilters().get(1).get(0)));
    }

    showList = filter.getVideos();

    for (Show sh : showList) {
      sh.setCriteria("most_viewed");
    }

    ArrayList<User> userList = new ArrayList<User>(data.getUsers().values());

    for (User u : userList) {
      for (Show sh : showList) {
        if (u.getViews().get(sh.getTitle()) != null) {
          sh.addViews(u.getViews().get(sh.getTitle()));
        }
      }
    }

    for (int j = 0 ; j < showList.size(); j++) {
      if (showList.get(j).getNoViews() == 0) {
        showList.remove(j);
        j--;
      }
    }

    if (input.getCommands().get(i).getSortType().equals("asc")) {
      Collections.sort(showList);
    } else if (input.getCommands().get(i).getSortType().equals("desc")) {
      Collections.sort(showList, Collections.reverseOrder());
    }

    result = new ArrayList<>();

    for (int j = 0; j < showList.size() && j < n; j++) {
      result.add(showList.get(j).getTitle());
    }
    return result;
  }

  public ArrayList<String> userRatings(Input input, Database data, int i) {
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<User> userList = new ArrayList<>(data.getUsers().values());

    for (int j = 0; j < userList.size(); j++) {
      if (userList.get(j).getNoRatings() == 0) {
        userList.remove(j);
        j--;
      }
    }

    if (input.getCommands().get(i).getSortType().equals("asc")) {
      Collections.sort(userList);
    } else if (input.getCommands().get(i).getSortType().equals("desc")) {
      Collections.sort(userList, Collections.reverseOrder());
    }

    result = new ArrayList<>();

    for (int j = 0; j < userList.size() && j < n; j++) {
      result.add(userList.get(j).getUsername());
    }
    return result;
  }

  public void menu(Input input, Database data) {

  }
}
