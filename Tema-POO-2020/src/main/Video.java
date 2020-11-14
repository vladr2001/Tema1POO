package main;

public class Video {
  public String title;
  public int year;
  public String[] genres;


  public Video(String title, int year, String[] genres) {
    this.title = title;
    this.year = year;
    this.genres = new String[genres.length];
    for (int i = 0; i < genres.length; i++) {
      this.genres[i] = genres[i];
    }
  }
}
