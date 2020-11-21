package main;

import java.util.ArrayList;

public class VideoFilters {
  private ArrayList<Video> videos;

  public VideoFilters() {
    this.videos = new ArrayList<>();
  }

  public void addVideo(Video v) {
    this.videos.add(v);
  }

  public void setVideos(ArrayList<Video> videos) {
    this.videos = videos;
  }

  public ArrayList<Video> getVideos() {
    return this.videos;
  }

  public ArrayList<Video> filterByYear(int year) {
    ArrayList<Video> newVideos = new ArrayList<>();
    for (Video i : this.getVideos()) {
      if (i.getYear() == year) {
        newVideos.add(i);
      }
    }

    return newVideos;
  }

  public ArrayList<Video> filterByGenre(String genre) {
    ArrayList<Video> newVideos = new ArrayList<>();
    for (Video i : this.getVideos()) {
      if (i.getGenres().contains(genre)) {
        newVideos.add(i);
      }
    }

    return newVideos;
  }
}
