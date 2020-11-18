package main;

import java.util.ArrayList;

public class Video {
  private String title;
  private int year;
  private ArrayList<String> cast;
  private ArrayList<String> genres;


  public Video(String title, int year, ArrayList<String> genres, ArrayList<String> cast) {
    this.title = title;
    this.year = year;
    this.genres = new ArrayList<>();
    this.cast = new ArrayList<>();

    for (String i: genres) {
      this.genres.add(i);
    }

    for (String i : cast) {
      this.cast.add(i);
    }
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public ArrayList<String> getCast() {
    return cast;
  }

  public void setCast(ArrayList<String> cast) {
    this.cast = cast;
  }

  public ArrayList<String> getGenres() {
    return genres;
  }

  public void setGenres(ArrayList<String> genres) {
    this.genres = genres;
  }
}
