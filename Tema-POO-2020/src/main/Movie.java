package main;

public class Movie extends Video{
  public int length;
  public float rating;

  public Movie(String title, int year, String[] genres, int length, float rating) {
    super(title, year, genres);
    this.length = length;
    this.rating = rating;
  }
}
