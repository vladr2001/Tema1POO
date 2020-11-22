package database;

import actor.Actor;
import entertainment.Movie;
import entertainment.Show;
import entertainment.User;
import fileio.Input;

import java.util.ArrayList;
import java.util.LinkedHashMap;

// Clasa in care se afla baza de date
public class Database {
  private LinkedHashMap<String, User> users;
  private LinkedHashMap<String, Movie> movies;
  private LinkedHashMap<String, Show> shows;
  private ArrayList<Actor> actors;
  public Database() {
    users = new LinkedHashMap<String, User>();
    movies = new LinkedHashMap<String, Movie>();
    shows = new LinkedHashMap<String, Show>();
    actors = new ArrayList<>();
  }

  /**
   *
   * @param input pentru input
   */
  public final void initUsers(final Input input) {
    // Initializarea structurii de utilizatori
    User user;
    for (int i = 0; i < input.getUsers().size(); i++) {
      user = new User(input.getUsers().get(i).getUsername(),
              input.getUsers().get(i).getSubscriptionType(),
              input.getUsers().get(i).getFavoriteMovies(),
              input.getUsers().get(i).getHistory());
      this.users.put(user.getUsername(), user);
    }
  }

  /**
   *
   * @param input pentru input
   */
  public final void initMovies(final Input input) {
    // Initializare structurii de filme
    Movie movie;
    for (int i = 0; i < input.getMovies().size(); i++) {
      movie = new Movie(input.getMovies().get(i).getTitle(),
              input.getMovies().get(i).getYear(),
              input.getMovies().get(i).getGenres(),
              input.getMovies().get(i).getCast(),
              input.getMovies().get(i).getDuration());
      this.movies.put(movie.getTitle(), movie);
    }
  }

  /**
   *
   * @param input pentru input
   */
  public final void initShows(final Input input) {
    // Initializarea structurii de seriale
    Show serial;
    for (int i = 0; i < input.getSerials().size(); i++) {
      serial = new Show(input.getSerials().get(i).getTitle(),
              input.getSerials().get(i).getYear(),
              input.getSerials().get(i).getGenres(),
              input.getSerials().get(i).getCast(),
              input.getSerials().get(i).getSeasons());
      serial.makeDuration();
      this.shows.put(serial.getTitle(), serial);
    }
  }

  /**
   *
   * @param input pentru input
   */
  public final void initActors(final Input input) {
    // Initializarea structurii de actori
    Actor actor;
    for (int i = 0; i < input.getActors().size(); i++) {
      actor = new Actor(input.getActors().get(i).getName(),
              input.getActors().get(i).getCareerDescription(),
              input.getActors().get(i).getFilmography(),
              input.getActors().get(i).getAwards());
      this.actors.add(actor);
    }
  }

  public final LinkedHashMap<String, User> getUsers() {
    return users;
  }

  public final void setUsers(final LinkedHashMap<String, User> users) {
    this.users = users;
  }

  public final LinkedHashMap<String, Movie> getMovies() {
    return movies;
  }

  public final void setMovies(final LinkedHashMap<String, Movie> movies) {
    this.movies = movies;
  }

  public final LinkedHashMap<String, Show> getShows() {
    return shows;
  }

  public final void setShows(final LinkedHashMap<String, Show> shows) {
    this.shows = shows;
  }

  public final ArrayList<Actor> getActors() {
    return actors;
  }

  public final void setActors(final ArrayList<Actor> actors) {
    this.actors = actors;
  }
}
