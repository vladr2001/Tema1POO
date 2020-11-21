package main;

import fileio.Input;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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

  public void initUsers(Input input) {
    User user;
    for (int i = 0; i < input.getUsers().size(); i++) {
      user = new User(input.getUsers().get(i).getUsername(),
              input.getUsers().get(i).getSubscriptionType(),
              input.getUsers().get(i).getFavoriteMovies(),
              input.getUsers().get(i).getHistory());
      this.users.put(user.getUsername(), user);
    }
  }

  public void initMovies(Input input) {
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

  public void initShows(Input input) {
    Show serial;
    for (int i = 0; i < input.getSerials().size(); i++) {
      // System.out.println("formeaza ht de seriale");
      serial = new Show(input.getSerials().get(i).getTitle(),
              input.getSerials().get(i).getYear(),
              input.getSerials().get(i).getGenres(),
              input.getSerials().get(i).getCast(),
              input.getSerials().get(i).getSeasons());
      serial.makeDuration();
      this.shows.put(serial.getTitle(), serial);
    }
  }

  public void initActors(Input input) {
    Actor actor;
    for (int i = 0; i < input.getActors().size(); i++) {
      actor = new Actor(input.getActors().get(i).getName(),
              input.getActors().get(i).getCareerDescription(),
              input.getActors().get(i).getFilmography(),
              input.getActors().get(i).getAwards());
      this.actors.add(actor);
    }
  }

  public LinkedHashMap<String, User> getUsers() {
    return users;
  }

  public void setUsers(LinkedHashMap<String, User> users) {
    this.users = users;
  }

  public LinkedHashMap<String, Movie> getMovies() {
    return movies;
  }

  public void setMovies(LinkedHashMap<String, Movie> movies) {
    this.movies = movies;
  }

  public LinkedHashMap<String, Show> getShows() {
    return shows;
  }

  public void setShows(LinkedHashMap<String, Show> shows) {
    this.shows = shows;
  }

  public ArrayList<Actor> getActors() {
    return actors;
  }

  public void setActors(ArrayList<Actor> actors) {
    this.actors = actors;
  }
}
