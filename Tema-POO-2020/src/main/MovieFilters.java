package main;

import java.util.ArrayList;

public class MovieFilters {
  private ArrayList<Movie> videos;

  public MovieFilters() {
    this.videos = new ArrayList<>();
  }

  public void addVideo(Movie v) {
    this.videos.add(v);
  }

  public void setVideos(ArrayList<Movie> videos) {
    this.videos = videos;
  }

  public ArrayList<Movie> getVideos() {
    return this.videos;
  }

  public ArrayList<Movie> filterByYear(int year) {
    ArrayList<Movie> newVideos = new ArrayList<>();
    for (Movie i : this.getVideos()) {
      if (i.getYear() == year) {
        newVideos.add(i);
      }
    }

    return newVideos;
  }

  public ArrayList<Movie> filterByGenre(String genre) {
    ArrayList<Movie> newVideos = new ArrayList<>();
    for (Movie i : this.getVideos()) {
      if (i.getGenres().contains(genre)) {
        newVideos.add(i);
      }
    }

    return newVideos;
  }
}
