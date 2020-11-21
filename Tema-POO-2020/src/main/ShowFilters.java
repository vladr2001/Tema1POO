package main;

import java.util.ArrayList;

public class ShowFilters {
  private ArrayList<Show> videos;

  public ShowFilters() {
    this.videos = new ArrayList<>();
  }

  public void addVideo(Show v) {
    this.videos.add(v);
  }

  public void setVideos(ArrayList<Show> videos) {
    this.videos = videos;
  }

  public ArrayList<Show> getVideos() {
    return this.videos;
  }

  public ArrayList<Show> filterByYear(int year) {
    ArrayList<Show> newVideos = new ArrayList<>();
    for (Show i : this.getVideos()) {
      if (i.getYear() == year) {
        newVideos.add(i);
      }
    }

    return newVideos;
  }

  public ArrayList<Show> filterByGenre(String genre) {
    ArrayList<Show> newVideos = new ArrayList<>();
    for (Show i : this.getVideos()) {
      if (i.getGenres().contains(genre)) {
        newVideos.add(i);
      }
    }

    return newVideos;
  }
}
