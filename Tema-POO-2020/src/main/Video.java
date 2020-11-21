package main;

import java.util.ArrayList;

public class Video implements  Comparable<Video>{
  private String title;
  private int year;
  private ArrayList<String> cast;
  private ArrayList<String> genres;
  private double videoRating;
  private String criteria;
  private int noFavs;
  private int duration;
  private int noViews;


  public Video(String title, int year, ArrayList<String> genres, ArrayList<String> cast, int length) {
    this.title = title;
    this.year = year;
    this.genres = new ArrayList<>();
    this.cast = new ArrayList<>();
    this.criteria = null;
    this.noFavs = 0;
    this.duration = length;
    this.noViews = 0;

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

  public void setVideoRating(double rating) {
    this.videoRating = rating;
  }

  public double getVideoRating() {
    return this.videoRating;
  }

  public void setCriteria(String criteria) {
    this.criteria = criteria;
  }

  public String getCriteria() {
    return this.criteria;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getDuration() {
    return this.duration;
  }

  public int getNoFavs() {
    return this.noFavs;
  }

  public void addFav() {
    this.noFavs = this.noFavs + 1;
  }

  public void addViews(int noViews) {
    this.noViews = this.noViews + noViews;
  }

  public int getNoViews() {
    return this.noViews;
  }

  @Override
  public int compareTo(Video o) {
    if (this.getCriteria().equals("ratings")) {
      if (Double.compare(this.videoRating, o.videoRating) != 0) {
        return Double.compare(this.videoRating, o.videoRating);
      } else {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getTitle(), o.getTitle());
      }
    }
    if (this.getCriteria().equals("favorite")) {
      if (Integer.compare(this.getNoFavs(), o.getNoFavs()) != 0) {
        return Integer.compare(this.getNoFavs(), o.getNoFavs());
      } else {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getTitle(), o.getTitle());
      }
    }
    if (this.getCriteria().equals("longest")) {
      if (Integer.compare(this.duration, o.duration) != 0) {
        return Integer.compare(this.duration, o.duration);
      } else {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getTitle(), o.getTitle());
      }
    }
    if (this.getCriteria().equals("most_viewed")) {
      if (Integer.compare(this.getNoViews(), o.getNoViews()) != 0) {
        return Integer.compare(this.getNoViews(), o.getNoViews());
      } else {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getTitle(), o.getTitle());
      }
    }
    return 0;
  }
}
