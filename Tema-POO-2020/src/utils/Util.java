package utils;

import actor.Actor;
import common.Constants;
import entertainment.Genres;
import entertainment.Movie;
import entertainment.Show;
import entertainment.User;
import entertainment.Video;
import fileio.Input;
import database.Database;
import filters.MovieFilters;
import filters.ShowFilters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Util {
  public Util() {

  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @return string-ul de output
   */
  public final ArrayList<String> averageActors(final Input input,
                                               final Database data, final int i) {
    // Cauta actorii dupa rating-ul lor si ii ordoneaza
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> average = new ArrayList<>();

    ArrayList<Actor> aux = new ArrayList<>();
    aux.addAll(data.getActors());

    for (Actor a : aux) {
      a.setQueryCriteria(Constants.AVERAGE);
    }

    // Face rating-ul pentru fiecare actor si ii scoate pe cei fara niciun rating
    for (int j = 0; j < aux.size(); j++) {
      aux.get(j).makeRating(data.getMovies(), data.getShows());

      if (aux.get(j).getRating() == -1) {
        aux.remove(j);
        j--;
      }
    }

    // Cauta ordinea ceruta si ordoneaza lista
    if (input.getCommands().get(i).getSortType().equals(Constants.ASC)) {
      Collections.sort(aux);
    } else if (input.getCommands().get(i).getSortType().equals(Constants.DESC)) {
      Collections.sort(aux, Collections.reverseOrder());
    }

    // Face lista cu primii n actori
    for (int nr = 0; nr < n && nr < aux.size(); nr++) {
      average.add(aux.get(nr).getName());
    }
    return average;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @return string-ul de output
   */
  public final ArrayList<String> awardActors(final Input input, final Database data, final int i) {
    // Cauta actorii dupa premii
    int flag;
    ArrayList<Actor> aux = new ArrayList<>();
    List<String> awards = new ArrayList<>();
    ArrayList<String> awardedActors = new ArrayList<>();
    List<List<String>> filters = input.getCommands().get(i).getFilters();
    awards = filters.get(Constants.MAGICNUMBER);
    // Cauta actorii cu premiile relevante
    for (Actor a : data.getActors()) {
      flag = 1;
      a.setRelevantAwards(awards);
      a.setQueryCriteria(Constants.AWARDS);
      for (String s : awards) {
        if (!a.getAwards().containsKey(s)) {
          flag = 0;
        }
      }

      if (flag == 1) {

        aux.add(a);
      }
    }
    // Verifica cum trebuie ordonata lista si o face
    if (input.getCommands().get(i).getSortType().equals(Constants.ASC)) {
      Collections.sort(aux);
    } else if (input.getCommands().get(i).getSortType().equals(Constants.DESC)) {
      Collections.sort(aux, Collections.reverseOrder());
    }
    // Face lista de nume
    for (Actor a : aux) {
      awardedActors.add(a.getName());
    }
    return awardedActors;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @return string-ul de output
   */
  public final ArrayList<String> filteredActors(final Input input, final Database data,
                                                final int i) {
    // Cauta actori dupa keywords si ii afiseaza pe cei care au toate
    // aceste cuvinte in descriere
    List<List<String>> filters = input.getCommands().get(i).getFilters();
    List<String> words = filters.get(2);
    ArrayList<Actor> aux = new ArrayList<>();
    int flag;
    ArrayList<String> filteredActors = new ArrayList<>();

    // Cauta cuvintele si verifica sa nu fie in mijlocul altui cuvant
    // substring-ul cautat
    for (Actor a : data.getActors()) {
      a.setCareerDesc(a.getCareerDesc().toLowerCase());
      flag = 1;
      for (String s : words) {
        if (!a.getCareerDesc().contains(s)) {
          flag = 0;
        } else {
          int index = a.getCareerDesc().indexOf(s);
          while (index != -1 && index < a.getCareerDesc().length()) {
            if (index > 0) {
              if (!(Constants.ALPHABET.indexOf(a.getCareerDesc().charAt(index - 1)) != -1)
              && !(index + s.length() < a.getCareerDesc().length()
                      && Constants.ALPHABET.indexOf(a.getCareerDesc()
                      .charAt(index + s.length())) != -1)) {
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
        a.setQueryCriteria(Constants.FILTER_DESCRIPTIONS);
        aux.add(a);
      }
    }

    // Verifica cum trebuie ordonate si o face, apoi face o lista cu numele lor
    if (input.getCommands().get(i).getSortType().equals(Constants.ASC)) {
      Collections.sort(aux);
    } else if (input.getCommands().get(i).getSortType().equals(Constants.DESC)) {
      Collections.sort(aux, Collections.reverseOrder());
    }

    for (Actor a : aux) {
      filteredActors.add(a.getName());
    }

    return filteredActors;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru data
   * @param i pentru a i-a iteratie
   * @return string-ul de output
   */
  public final ArrayList<String> videoRating(final Input input, final Database data, final int i) {
    // Cauta videoclipuri dupa rating si le ordoneaza
    ArrayList<Movie> movieList = new ArrayList<Movie>(data.getMovies().values());
    ArrayList<Show> showList = new ArrayList<Show>(data.getShows().values());

    MovieFilters mfilter = new MovieFilters();
    ShowFilters sfilter = new ShowFilters();
    ArrayList<String> result = new ArrayList<>();

    int n = input.getCommands().get(i).getNumber();
    // Pune filmele/serialele in cate un filter si le selecteaza dupa an si gen
    if (input.getCommands().get(i).getObjectType().equals(Constants.MOVIES)) {
      mfilter.setVideos(movieList);
      if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
        mfilter.setVideos(mfilter.filterByYear(Integer.parseInt(input.
                getCommands().get(i).getFilters().get(0).get(0))));
      }
      if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
        mfilter.setVideos(mfilter.filterByGenre(input.
                getCommands().get(i).getFilters().get(1).get(0)));
      }
    } else {
      sfilter.setVideos(showList);
      if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
        sfilter.setVideos(sfilter.filterByYear(Integer.parseInt(input.
                getCommands().get(i).getFilters().get(0).get(0))));
      }
      if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
        sfilter.setVideos(sfilter.filterByGenre(input.
                getCommands().get(i).getFilters().get(1).get(0)));
      }
    }

    ArrayList<Movie> auxMovies = new ArrayList<>();
    ArrayList<Show> auxShows = new ArrayList<>();
    // Face rating-urile pentru filme/seriale si le scoate pe cele fara
    // Apoi verifica in ce ordine trebuie ordonate, o face si
    // Le selecteaza pe primele n
    if (input.getCommands().get(i).getObjectType().equals(Constants.MOVIES)) {
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

      if (input.getCommands().get(i).getSortType().equals(Constants.ASC)) {
        Collections.sort(auxMovies);
      } else if (input.getCommands().get(i).getSortType().equals(Constants.DESC)) {
        Collections.sort(auxMovies, Collections.reverseOrder());
      }

      result = new ArrayList<>();
      for (int j = 0; j < n && j < auxMovies.size(); j++) {
        result.add(auxMovies.get(j).getTitle());
      }
    } else if (input.getCommands().get(i).getObjectType().equals(Constants.SHOWS)) {
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

      if (input.getCommands().get(i).getSortType().equals(Constants.ASC)) {
        Collections.sort(auxShows);
      } else if (input.getCommands().get(i).getSortType().equals(Constants.DESC)) {
        Collections.sort(auxShows, Collections.reverseOrder());
      }
      result = new ArrayList<>();
      for (int j = 0; j < n && j < auxShows.size(); j++) {
        result.add(auxShows.get(j).getTitle());
      }
    }
    return result;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @return string-ul de output
   */
  public final ArrayList<String> favMovies(final Input input, final Database data, final int i) {
    // Cauta si ordoneaza filmele dupa numarul de liste de favorite in care apar
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Movie> movieList = new ArrayList<Movie>(data.getMovies().values());
    MovieFilters filter = new MovieFilters();
    filter.setVideos(movieList);
    // Le adauga intr-un filter si le selecteaza dupa an si gen
    if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
      filter.setVideos(filter.filterByYear(Integer.parseInt(input.
              getCommands().get(i).getFilters().get(0).get(0))));
    }
    if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
      filter.setVideos(filter.filterByGenre(input.getCommands().get(i).getFilters().get(1).get(0)));
    }
    ArrayList<User> userList = new ArrayList<User>(data.getUsers().values());


    for (User u : userList) {
      for (String s : u.getFavs()) {
        for (Movie m : filter.getVideos()) {
          m.setCriteria(Constants.FAVORITE);
          if (m.getTitle().equals(s)) {
            m.addFav();
          }
        }
      }
    }

    movieList = filter.getVideos();

    // Scoate din lista filmele care nu apar in nicio lista de favorite
    for (int j = 0; j < movieList.size(); j++) {
      if (movieList.get(j).getNoFavs() == 0) {
        movieList.remove(j);
        j--;
      }
    }
    // Verifica care este ordinea si le sorteaza
    if (input.getCommands().get(i).getSortType().equals(Constants.ASC)) {
      Collections.sort(movieList);
    } else if (input.getCommands().get(i).getSortType().equals(Constants.DESC)) {
      Collections.sort(movieList, Collections.reverseOrder());
    }
    result = new ArrayList<>();
    // Formeaza o lista cu titlurile de la primele n din lista sortata
    for (int j = 0; j < n && j < movieList.size(); j++) {
      result.add(movieList.get(j).getTitle());
    }
    return result;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratia
   * @return string-ul de output
   */
  public final ArrayList<String> favShows(final Input input, final Database data, final int i) {
    // Cauta serialele cu cele mai multe favorite
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Show> showList = new ArrayList<Show>(data.getShows().values());
    ShowFilters filter = new ShowFilters();
    filter.setVideos(showList);

    // Selecteaza serialele dupa an si gen
    if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
      filter.setVideos(filter.filterByYear(Integer.parseInt(input.
              getCommands().get(i).getFilters().get(0).get(0))));
    }
    if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
      filter.setVideos(filter.filterByGenre(input.getCommands().get(i).getFilters().get(1).get(0)));
    }
    ArrayList<User> userList = new ArrayList<User>(data.getUsers().values());
    // Calculeaza cate favorite are fiecare video
    for (User u : userList) {
      for (String s : u.getFavs()) {
        for (Show sh : filter.getVideos()) {
          sh.setCriteria(Constants.FAVORITE);
          if (sh.getTitle().equals(s)) {
            sh.addFav();
          }
        }
      }
    }

    showList = filter.getVideos();

    // Le scot pe cele care nu apar in nicio lista de favorite
    for (int j = 0; j < showList.size(); j++) {
      if (showList.get(j).getNoFavs() == 0) {
        showList.remove(j);
        j--;
      }
    }

    // Verifica cum trebuie sortate si o face
    if (input.getCommands().get(i).getSortType().equals(Constants.ASC)) {
      Collections.sort(showList);
    } else if (input.getCommands().get(i).getSortType().equals(Constants.DESC)) {
      Collections.sort(showList, Collections.reverseOrder());
    }

    result = new ArrayList<>();
    // Adauga primele n titluri din lista sortata
    for (int j = 0; j < n && j < showList.size(); j++) {
      result.add(showList.get(j).getTitle());
    }
    return result;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratia
   * @return string-ul de output
   */
  public final ArrayList<String> longestMovie(final Input input, final Database data, final int i) {
    // Cauta si ordoneaza filmele dupa durata
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Movie> movieList = new ArrayList<Movie>(data.getMovies().values());
    MovieFilters filter = new MovieFilters();
    filter.setVideos(movieList);

    // Adauga toate filmele intr-un filter si le selecteaza dupa an si gen
    if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
      filter.setVideos(filter.filterByYear(Integer.parseInt(input.
              getCommands().get(i).getFilters().get(0).get(0))));
    }
    if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
      filter.setVideos(filter.filterByGenre(input.getCommands().get(i).getFilters().get(1).get(0)));
    }

    movieList = filter.getVideos();

    for (Movie m : movieList) {
      m.setCriteria(Constants.LONGEST);
    }

    // Verifica ordinea si le ordoneaza
    if (input.getCommands().get(i).getSortType().equals(Constants.ASC)) {
      Collections.sort(movieList);
    } else if (input.getCommands().get(i).getSortType().equals(Constants.DESC)) {
      Collections.sort(movieList, Collections.reverseOrder());
    }

    result = new ArrayList<>();

    // Face o lista cu titlurile primelor n filme din lista
    for (int j = 0; j < n && j < movieList.size(); j++) {
      result.add(movieList.get(j).getTitle());
    }
    return result;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @return string-ul de output
   */
  public final ArrayList<String> longestShow(final Input input, final Database data, final int i) {
    // Ordoneaza si afiseaza seriale dupa durata
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Show> showList = new ArrayList<Show>(data.getShows().values());
    ShowFilters filter = new ShowFilters();
    filter.setVideos(showList);

    // Adauga serialele intr-un filter si le selecteaza dupa an si gen
    if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
      filter.setVideos(filter.filterByYear(Integer.parseInt(input.
              getCommands().get(i).getFilters().get(0).get(0))));
    }
    if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
      filter.setVideos(filter.filterByGenre(input.getCommands().get(i).
              getFilters().get(1).get(0)));
    }

    showList = filter.getVideos();

    for (Show sh : showList) {
      sh.setCriteria(Constants.LONGEST);
    }

    if (input.getCommands().get(i).getSortType().equals(Constants.ASC)) {
      Collections.sort(showList);
    } else if (input.getCommands().get(i).getSortType().equals(Constants.DESC)) {
      Collections.sort(showList, Collections.reverseOrder());
    }

    result = new ArrayList<>();
    // Formeaza o lista cu titlurile de la primele n seriale
    for (int j = 0; j < n && j < showList.size(); j++) {
      result.add(showList.get(j).getTitle());
    }
    return result;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @return string-ul e output
   */
  public final ArrayList<String> mostViewedMovies(final Input input, final Database data,
                                                  final int i) {
    // Cauta cele mai vizionate filme
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Movie> movieList = new ArrayList<Movie>(data.getMovies().values());
    MovieFilters filter = new MovieFilters();
    // Adauga filmele in filter si le selecteaza dupa an si gen
    filter.setVideos(movieList);
    if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
      filter.setVideos(filter.filterByYear(Integer.parseInt(input.
              getCommands().get(i).getFilters().get(0).get(0))));
    }
    if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
      filter.setVideos(filter.filterByGenre(input.getCommands().get(i).getFilters().get(1).get(0)));
    }

    movieList = filter.getVideos();

    for (Movie m : movieList) {
      m.setCriteria(Constants.MOSTVIEWED);
    }

    ArrayList<User> userList = new ArrayList<User>(data.getUsers().values());

    // Verifica cate vizionari are fiecare
    for (User u : userList) {
      for (Movie m : movieList) {
        if (u.getViews().get(m.getTitle()) != null) {
          m.addViews(u.getViews().get(m.getTitle()));
        }
      }
    }

    // Le scoate din lista pe cele fara vizionari
    for (int j = 0; j < movieList.size(); j++) {
      if (movieList.get(j).getNoViews() == 0) {
        movieList.remove(j);
        j--;
      }
    }

    // Verifica cum trebuie ordonata lista si o face
    if (input.getCommands().get(i).getSortType().equals(Constants.ASC)) {
      Collections.sort(movieList);
    } else if (input.getCommands().get(i).getSortType().equals(Constants.DESC)) {
      Collections.sort(movieList, Collections.reverseOrder());
    }

    result = new ArrayList<>();

    // Le selecteaza pe primele n si le trimite titlurile inapoi
    for (int j = 0; j < movieList.size() && j < n; j++) {
      result.add(movieList.get(j).getTitle());
    }

    return result;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @return string-ul de output
   */
  public final ArrayList<String> mostViewedShows(final Input input, final Database data,
                                                 final int i) {
    // Cauta cele mai vizionate seriale
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Show> showList = new ArrayList<>(data.getShows().values());
    ShowFilters filter = new ShowFilters();
    filter.setVideos(showList);
    // Adauga toate serialele intr-un filter si apoi le selecteaza dupa an si gen
    if (input.getCommands().get(i).getFilters().get(0).get(0) != null) {
      filter.setVideos(filter.filterByYear(Integer.parseInt(input.getCommands()
              .get(i).getFilters().get(0).get(0))));
    }

    if (input.getCommands().get(i).getFilters().get(1).get(0) != null) {
      filter.setVideos(filter.filterByGenre(input.getCommands().get(i).
              getFilters().get(1).get(0)));
    }

    showList = filter.getVideos();

    for (Show sh : showList) {
      sh.setNoViews(0);
      sh.setCriteria(Constants.MOSTVIEWED);
    }

    ArrayList<User> userList = new ArrayList<User>(data.getUsers().values());

    // Numara cate vizionari are fiecare
    for (User u : userList) {
      for (Show sh : showList) {
        if (u.getViews().get(sh.getTitle()) != null) {
          sh.addViews(u.getViews().get(sh.getTitle()));
        }
      }
    }

    // Le scoate pe cele fara vizionari
    for (int j = 0; j < showList.size(); j++) {
      if (showList.get(j).getNoViews() == 0) {
        showList.remove(j);
        j--;
      }
    }

    // Verifica cum trebuie ordonate si o face
    if (input.getCommands().get(i).getSortType().equals(Constants.ASC)) {
      Collections.sort(showList);
    } else if (input.getCommands().get(i).getSortType().equals(Constants.DESC)) {
      Collections.sort(showList, Collections.reverseOrder());
    }

    result = new ArrayList<>();

    // Formeaza string-ul de output
    for (int j = 0; j < showList.size() && j < n; j++) {
      result.add(showList.get(j).getTitle());
    }
    return result;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @return string-ul de output
   */
  public final ArrayList<String> userRatings(final Input input, final Database data, final int i) {
    int n = input.getCommands().get(i).getNumber();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<User> userList = new ArrayList<>(data.getUsers().values());
    // Verifica ce utilizator a dat cele mai multe rating-uri
    for (int j = 0; j < userList.size(); j++) {
      // Ii scoate pe cei care n-au dat niciun rating din lista
      if (userList.get(j).getNoRatings() == 0) {
        userList.remove(j);
        j--;
      }
    }

    // Verifica daca trebuie sortat ascendent sau descendent si o face
    if (input.getCommands().get(i).getSortType().equals(Constants.ASC)) {
      Collections.sort(userList);
    } else if (input.getCommands().get(i).getSortType().equals(Constants.DESC)) {
      Collections.sort(userList, Collections.reverseOrder());
    }

    result = new ArrayList<>();
    // Formeaza lista de username-uri (primii n) cu cele mai multe(sau putine) rating-uri
    for (int j = 0; j < userList.size() && j < n; j++) {
      result.add(userList.get(j).getUsername());
    }
    return result;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @return string-ul de output
   */
  public final String standardRecommendation(final Input input, final Database data, final int i) {
    String output = null;
    int flag = 0;
    String recommended = null;
    // Cauta primul videoclip nevizionat de utilizator in ordinea din baza de date
    // (intai in filme, apoi in seriale)

    if (data.getUsers().get(input.getCommands().get(i).getUsername()) == null) {
      output = Constants.STANDARDRECOMMENDATION + Constants.APPLIED;
      flag = 2;
    }
    if (flag == 0) {
      for (Movie m : data.getMovies().values()) {
        if (data.getUsers().get(input.getCommands().get(i).getUsername())
                .getViews().get(m.getTitle()) == null) {
          flag = 1;
          recommended = m.getTitle();
          break;
        }
      }
    }

    if (flag == 0) {
      for (Show sh : data.getShows().values()) {
        if (data.getUsers().get(input.getCommands().get(i)
                .getUsername()).getViews().get(sh.getTitle()) == null) {
          flag = 1;
          recommended = sh.getTitle();
          break;
        }
      }
    }

    if (flag == 1) {
      output = Constants.STANDARDRECOMMENDATION + Constants.RESULT + recommended;
    }
    if (output == null) {
      output = Constants.STANDARDRECOMMENDATION + Constants.APPLIED;
    }
    return output;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @return string-ul de output
   */
  public final String bestUnseenRecommendation(final Input input,
                                               final Database data, final int i) {
    ArrayList<Movie> unseeenMovies = new ArrayList<>();
    ArrayList<Show> unseenShows = new ArrayList<>();
    String output;
    // Cauta cel mai bun film/serial pe care nu l-a vazut utilizatorul
    // Face o lista cu filmele pe care nu le-a vazut utilizatorul, si una cu serialele
    for (Movie m : data.getMovies().values()) {
      if (data.getUsers().get(input.getCommands().get(i)
              .getUsername()).getViews().get(m.getTitle()) == null) {
        m.setCriteria(Constants.RATINGSRECOM);
        unseeenMovies.add(m);
        double rating = unseeenMovies.get(unseeenMovies.size() - 1).makeMovieRating();
        unseeenMovies.get(unseeenMovies.size() - 1).setVideoRating(rating);
      }
    }

    for (Show sh : data.getShows().values()) {
      if (data.getUsers().get(input.getCommands().get(i)
              .getUsername()).getViews().get(sh.getTitle()) == null) {
        sh.setCriteria(Constants.RATINGSRECOM);
        unseenShows.add(sh);
        double rating = unseenShows.get(unseenShows.size() - 1).makeShowRating();
        unseenShows.get(unseenShows.size() - 1).setVideoRating(rating);
      }
    }

    // Le ordoneaza pe ambele dupa rating
    Collections.sort(unseeenMovies, Collections.reverseOrder());
    Collections.sort(unseenShows, Collections.reverseOrder());
    // Apoi il cauta pe primul dupa criteriile stabilite
    if (unseeenMovies.size() != 0 && unseenShows.size() != 0) {
      if (unseenShows.get(0).getVideoRating() > unseeenMovies.get(0).getVideoRating()) {
        output = Constants.BESTRATEDRECOMMENDATION + Constants.RESULT
                + unseenShows.get(0).getTitle();
      } else {
        output = Constants.BESTRATEDRECOMMENDATION + Constants.RESULT
                + unseeenMovies.get(0).getTitle();
      }
    } else if (unseeenMovies.size() != 0) {
      output = Constants.BESTRATEDRECOMMENDATION
              + Constants.RESULT + unseeenMovies.get(0).getTitle();
    } else if (unseenShows.size() != 0) {
      output = Constants.BESTRATEDRECOMMENDATION + Constants.RESULT
              + unseenShows.get(0).getTitle();
    } else {
      output = Constants.BESTRATEDRECOMMENDATION + Constants.APPLIED;
    }
    return output;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratia
   * @return string-ul de output
   */
  public final String popularRecommendation(final Input input, final Database data, final int i) {
    // Functia care cauta recomandarea dupa videoclipurile populare
    int flag = 0;
    String output = null;
    if (data.getUsers().get(input.getCommands().get(i).getUsername()) == null
            || data.getUsers().get(input.getCommands().get(i).
            getUsername()).getCategory().equals(Constants.BASIC)) {
      // Verifica daca user-ul nu exista sau e BASIC
      output = Constants.POPULARRECOMMENDATION + Constants.APPLIED;
    } else {
      ArrayList<Movie> unseenMovies = new ArrayList<>();
      ArrayList<Show> unseenShows = new ArrayList<>();
      HashMap<String, Genres> popularGenres = new HashMap<String, Genres>();
      // Parcurge toate filmele si serialele vazute si calculeaza cate vizionari are fiecare gen
      for (Movie m : data.getMovies().values()) {
        if (data.getUsers().get(input.getCommands().get(i).
                getUsername()).getViews().get(m.getTitle()) == null) {
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
        if (data.getUsers().get(input.getCommands().get(i).getUsername())
                .getViews().get(sh.getTitle()) == null) {
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
      // Creeaza o lista cu genuri si o ordoneaza dupa criteriile specificate
      ArrayList<Genres> genreOrder = new ArrayList<Genres>(popularGenres.values());
      Collections.sort(genreOrder, Collections.reverseOrder());
      flag = 0;

      // Cauta primul film si apoi serial nevazut de utilizator
      // de diverse genuri in ordinea popularitatii
      for (Genres g : genreOrder) {
        for (Movie m : unseenMovies) {
          if (m.getGenres().contains(g.getName())) {
            flag = 1;
            output = Constants.POPULARRECOMMENDATION + Constants.RESULT + m.getTitle();
            break;
          }
        }
        if (flag == 1) {
          break;
        }
        for (Show sh : unseenShows) {
          if (sh.getGenres().contains(g.getName())) {
            flag = 1;
            output = Constants.POPULARRECOMMENDATION + Constants.RESULT + sh.getTitle();
            break;
          }
          if (flag == 1) {
            break;
          }
        }
      }

      if (flag == 0) {
        output = Constants.POPULARRECOMMENDATION + Constants.APPLIED;
      }
    }
    return output;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @return string-ul de output
   */
  public final String favoriteRecommendation(final Input input, final Database data, final int i) {
    // Functia care cauta recomandarea de favorite
    User user = data.getUsers().get(input.getCommands().get(i).getUsername());
    ArrayList<Movie> unseenMovies = new ArrayList<>();
    ArrayList<Show> unseenShows = new ArrayList<>();
    String output = null;
    if (user == null || user.getCategory().equals(Constants.BASIC)) {
      // Verifica daca utilizatorul nu exista sau e BASIC
      output = Constants.FAVORITERECOMMENDATION + Constants.APPLIED;
    } else {
      // Face o lista cu filme si seriale pe care nu le-a vazut utilizatorul
      for (Movie m : data.getMovies().values()) {
        if (user.getViews().get(m.getTitle()) == null) {
          m.setNoFavs(0);
          unseenMovies.add(m);
        }
      }

      for (Show sh : data.getShows().values()) {
        if (user.getViews().get(sh.getTitle()) == null) {
          sh.setNoFavs(0);
          unseenShows.add(sh);
        }
      }

      // Numara in cate liste de favorite este fiecare film/serial
      for (User u : data.getUsers().values()) {
        for (String s : u.getFavs()) {
          for (Movie m : unseenMovies) {
            m.setCriteria(Constants.FAVRECOM);
            if (m.getTitle().equals(s)) {
              m.addFav();
            }
          }
        }
      }

      for (User u : data.getUsers().values()) {
        for (String s : u.getFavs()) {
          for (Show sh : unseenShows) {
            sh.setCriteria(Constants.FAVRECOM);
            if (sh.getTitle().equals(s)) {
              sh.addFav();
            }
          }
        }
      }

      // Le scoate pe cele care nu sunt in nicio lista de favorite
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
      // Sorteaza listele
      Collections.sort(unseenMovies, Collections.reverseOrder());
      Collections.sort(unseenShows, Collections.reverseOrder());

      // Verifica care este primul dupa criteriile stabilite si formeaza
      // string-ul de output
      if (unseenMovies.size() != 0) {
        if (unseenShows.size() != 0 && unseenMovies.get(0).getNoFavs()
                < unseenShows.get(0).getNoFavs()) {
          output = Constants.FAVORITERECOMMENDATION + Constants.RESULT
                  + unseenShows.get(0).getTitle();
        } else {
          output = Constants.FAVORITERECOMMENDATION + Constants.RESULT
                  + unseenMovies.get(0).getTitle();
        }
      } else {
        if (unseenShows.size() != 0) {
          output = Constants.FAVORITERECOMMENDATION
                  + Constants.RESULT + unseenShows.get(0).getTitle();
        } else {
          output =  Constants.FAVORITERECOMMENDATION + Constants.APPLIED;
        }
      }
    }
    return output;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @return  string-ul de output
   */
  public final String searchRecommendation(final Input input, final Database data, final int i) {
    // Functia care cauta recomandarea de search
    String genre = input.getCommands().get(i).getGenre();
    User user = data.getUsers().get(input.getCommands().get(i).getUsername());
    String output = null;
    ArrayList<String> result;
    if (user == null || user.getCategory().equals(Constants.BASIC)) {
      // Verifica daca nu exista userul sau daca este BASIC
      output = Constants.SEARCHRECOMMENDATION + Constants.APPLIED;
    } else {
      ArrayList<Movie> unseenMovies = new ArrayList<>();
      ArrayList<Show> unseenShows = new ArrayList<>();
      ArrayList<Video> unseenVideos = new ArrayList<>();
      // Creeaza o lista de filme si seriale pe care nu le-a vazut utilizatorul
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

      // Face rating-ul pentru fiecare film si serial
      // si le adauga intr-o lista comuna de videoclipuri
      for (Movie m : unseenMovies) {
        Video v = new Video(m.getTitle(), m.getYear(), m.getGenres(), m.getCast(), m.getDuration());
        v.setVideoRating(m.makeMovieRating());
        v.setCriteria(Constants.RATINGS);
        unseenVideos.add(v);
      }

      for (Show sh : unseenShows) {
        Video v = new Video(sh.getTitle(), sh.getYear(),
                sh.getGenres(), sh.getCast(), sh.getDuration());
        v.setVideoRating(sh.makeShowRating());
        v.setCriteria(Constants.RATINGS);
        unseenVideos.add(v);
      }

      // Sorteaza videoclipurile si formeaza lista de titluri
      Collections.sort(unseenVideos);
      result = new ArrayList<>();
      if (unseenVideos.size() != 0) {
        for (Video v : unseenVideos) {
          result.add(v.getTitle());
        }

        output = Constants.SEARCHRECOMMENDATION + Constants.RESULT + result;
      } else {
        output = Constants.SEARCHRECOMMENDATION + Constants.APPLIED;
      }
    }
    return output;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratia
   * @return string-ul de output
   */
  public final String command(final Input input, final Database data, final int i) {
    String output = null;
    Movie movie;
    Show serial;
    // Verifica ce comanda este si apeleaza functia aferenta
    if (input.getCommands().get(i).getType() != null
            && input.getCommands().get(i).getType().equals(Constants.FAVORITE)
            && input.getCommands().get(i).getActionType().equals(Constants.COMMAND)) {
      output = data.getUsers().get(input.getCommands().get(i).getUsername()).
              addFavourite(input.getCommands().get(i).getTitle());
      if (output != null) {
        return output;

      }
    } else if (input.getCommands().get(i).getType() != null // VIEW
            && input.getCommands().get(i).getType().equals(Constants.VIEW)
            && input.getCommands().get(i).getActionType().equals(Constants.COMMAND)) {
      output = data.getUsers().get(input.getCommands().get(i).getUsername()).
              addView(input.getCommands().get(i).getTitle());
      if (output != null) {
        return output;
      }
    } else if (input.getCommands().get(i).getType() != null && // RATING
            input.getCommands().get(i).getType().equals(Constants.RATING)
            && input.getCommands().get(i).getActionType().equals(Constants.COMMAND)) {

      if (data.getMovies().get(input.getCommands().get(i).getTitle()) != null) { // PT FILME
        movie = data.getMovies().get(input.getCommands().get(i).getTitle());
        output = movie.addRating(input.getCommands().get(i).getGrade(),
                input.getCommands().get(i).getUsername(), data.getUsers());

      } else if (data.getShows().get(input.getCommands().get(i).getTitle()) != null) { // PT SERIALE
        serial = data.getShows().get(input.getCommands().get(i).getTitle());
        output = serial.addRating(input.getCommands().get(i).getGrade(),
                input.getCommands().get(i).getSeasonNumber(),
                input.getCommands().get(i).getUsername(), data.getUsers());
      }
      if (output != null) {
        return output;
      }
    }
    return output;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @param util pentru a putea apela functiile
   * @return string-ul de output
   */
  public final String query(final Input input, final Database data, final int i, final Util util) {
    String output = null;
    ArrayList<String> result = new ArrayList<>();
    // Verifica ce query doreste si apeleaza functia aferenta
    if (input.getCommands().get(i).getActionType() != null // AVERAGE ACTORI
            && input.getCommands().get(i).getCriteria() != null
            && input.getCommands().get(i).getCriteria().equals(Constants.AVERAGE)
            && input.getCommands().get(i).getActionType().equals(Constants.QUERY)) {
      output = Constants.QUERYRESULT + util.averageActors(input, data, i);
      if (output != null) {
        return output;
      }
    } else if (input.getCommands().get(i).getActionType() != null // AWARDS ACTORI
            && input.getCommands().get(i).getCriteria() != null
            && input.getCommands().get(i).getCriteria().equals(Constants.AWARDS)
            && input.getCommands().get(i).getActionType().equals(Constants.QUERY)) {
      output = Constants.QUERYRESULT + util.awardActors(input, data, i);

      if (output != null) {
        return output;
      }
    } else if (input.getCommands().get(i).getActionType() != null // FILTER ACTORI
            && input.getCommands().get(i).getCriteria() != null
            && input.getCommands().get(i).getCriteria().equals(Constants.FILTER_DESCRIPTIONS)
            && input.getCommands().get(i).getActionType().equals(Constants.QUERY)) {
      output = Constants.QUERYRESULT + util.filteredActors(input, data, i);
      if (output != null) {
        return output;
      }
    } else if (input.getCommands().get(i).getActionType() != null // RATING videos
            && input.getCommands().get(i).getCriteria() != null
            && input.getCommands().get(i).getActionType().equals(Constants.QUERY)
            && input.getCommands().get(i).getCriteria().equals(Constants.RATINGS)) {
      output = Constants.QUERYRESULT + util.videoRating(input, data, i);
      return output;
    } else if (input.getCommands().get(i).getActionType() != null // fav videos
            && input.getCommands().get(i).getCriteria() != null
            && input.getCommands().get(i).getActionType().equals(Constants.QUERY)
            && input.getCommands().get(i).getCriteria().equals(Constants.FAVORITE)) {
      if (input.getCommands().get(i).getObjectType().equals(Constants.MOVIES)) {
        result = util.favMovies(input, data, i);
      } else if (input.getCommands().get(i).getObjectType().equals(Constants.SHOWS)) {
        result = util.favShows(input, data, i);
      }
      if (result != null) {
        output = Constants.QUERYRESULT + result;
      }
      return output;
    } else if (input.getCommands().get(i).getActionType() != null // longest videos
            && input.getCommands().get(i).getCriteria() != null
            && input.getCommands().get(i).getActionType().equals(Constants.QUERY)
            && input.getCommands().get(i).getCriteria().equals(Constants.LONGEST)) {
      if (input.getCommands().get(i).getObjectType().equals(Constants.MOVIES)) {
        output = Constants.QUERYRESULT + util.longestMovie(input, data, i);
      } else if (input.getCommands().get(i).getObjectType().equals(Constants.SHOWS)) {
        output = Constants.QUERYRESULT + util.longestShow(input, data, i);
      }
      return output;
    } else if (input.getCommands().get(i).getActionType() != null // most viewed videos
            && input.getCommands().get(i).getCriteria() != null
            && input.getCommands().get(i).getActionType().equals(Constants.QUERY)
            && input.getCommands().get(i).getCriteria().equals(Constants.MOSTVIEWED)) {
      if (input.getCommands().get(i).getObjectType().equals(Constants.MOVIES)) {
        result = util.mostViewedMovies(input, data, i);
      } else if (input.getCommands().get(i).getObjectType().equals(Constants.SHOWS)) {
        result = util.mostViewedShows(input, data, i);
      }
      output = Constants.QUERYRESULT + result;
      return output;
    } else if (input.getCommands().get(i).getActionType() != null // user ratings
            && input.getCommands().get(i).getCriteria() != null
            && input.getCommands().get(i).getActionType().equals(Constants.QUERY)
            && input.getCommands().get(i).getCriteria().equals(Constants.NUM_RATINGS)) {
      output = Constants.QUERYRESULT + util.userRatings(input, data, i);
      return output;
    }
    return output;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @param util pentru functii
   * @return string-ul de output
   */
  public final String recommendation(final Input input, final Database data,
                               final int i, final Util util) {
    String output = null;
    // Verifica ce fel de recomandare este si apeleaza functia aferenta
    if (input.getCommands().get(i).getActionType() != null    // recommended standard
            && input.getCommands().get(i).getType() != null
            && input.getCommands().get(i).getActionType().equals(Constants.RECOMMENDATION)
            && input.getCommands().get(i).getType().equals(Constants.STANDARD)) {
      output = util.standardRecommendation(input, data, i);
      return output;
    } else if (input.getCommands().get(i).getActionType() != null    // recommended best
            && input.getCommands().get(i).getType() != null
            && input.getCommands().get(i).getActionType().equals(Constants.RECOMMENDATION)
            && input.getCommands().get(i).getType().equals(Constants.BESTUNSEEN)) {
      output = util.bestUnseenRecommendation(input, data, i);
      return output;
    } else if (input.getCommands().get(i).getActionType() != null    // popular recommendation
            && input.getCommands().get(i).getType() != null
            && input.getCommands().get(i).getActionType().equals(Constants.RECOMMENDATION)
            && input.getCommands().get(i).getType().equals(Constants.POPULAR)) {
      output = util.popularRecommendation(input, data, i);
      return output;
    } else if (input.getCommands().get(i).getActionType() != null    // fav recommendation
            && input.getCommands().get(i).getType() != null
            && input.getCommands().get(i).getActionType().equals(Constants.RECOMMENDATION)
            && input.getCommands().get(i).getType().equals(Constants.FAVORITE)) {
      output = util.favoriteRecommendation(input, data, i);
      return output;
    } else if (input.getCommands().get(i).getActionType() != null    // search recommendation
            && input.getCommands().get(i).getType() != null
            && input.getCommands().get(i).getActionType().equals(Constants.RECOMMENDATION)
            && input.getCommands().get(i).getType().equals(Constants.SEARCH)) {
      output = util.searchRecommendation(input, data, i);
      return output;
    }
    return output;
  }

  /**
   *
   * @param input pentru input
   * @param data pentru baza de date
   * @param i pentru a i-a iteratie
   * @param util pentru a accesa functiile
   * @return string-ul pentru output
   */
  public final String menu(final Input input, final Database data, final int i, final Util util) {
    String output = null;
    ArrayList<String> result = new ArrayList<>();
    Movie movie;
    Show serial;
    // Verifica daca e command, query sau recommendation si apeleaza mai departe
    // functia aferenta
    if (input.getCommands().get(i).getActionType() != null
    && input.getCommands().get(i).getActionType().equals(Constants.COMMAND)) {
      output = util.command(input, data, i);
      return output;
    } else if (input.getCommands().get(i).getActionType() != null
    && input.getCommands().get(i).getActionType().equals(Constants.QUERY)) {
      output = util.query(input, data, i, util);
      return output;
    } else if (input.getCommands().get(i).getActionType() != null
    && input.getCommands().get(i).getActionType().equals(Constants.RECOMMENDATION)) {
      output = util.recommendation(input, data, i, util);
      return output;
    }

    return output;
  }

}
