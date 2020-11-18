package main;

import java.util.ArrayList;
import java.util.List;

public class mySeason {
  private int noSeason;
  private ArrayList<Double> ratings;
  private int duration;
  private ArrayList<String> ratedBy;

  public mySeason(int noSeason, List<Double> ratings, int duration) {
    this.noSeason = noSeason;
    this.ratings = new ArrayList<>();
    this.ratedBy = new ArrayList<>();
    this.duration = duration;

    this.ratings.addAll(ratings);
  }

  public void addRating(double rating) {
    this.ratings.add(rating);
  }

  public void addRatedBy(String username) {
    this.ratedBy.add(username);
  }

  public int getNoSeason() {
    return noSeason;
  }

  public void setNoSeason(int noSeason) {
    this.noSeason = noSeason;
  }

  public ArrayList<Double> getRatings() {
    return ratings;
  }

  public void setRatings(ArrayList<Double> ratings) {
    this.ratings = ratings;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public ArrayList<String> getRatedBy() {
    return ratedBy;
  }

  public void setRatedBy(ArrayList<String> ratedBy) {
    this.ratedBy = ratedBy;
  }

  public double makeSeasonRating() {
    double rating = -1;

    if (this.ratings.size() == 0) {
      return rating;
    }

    for (double i : this.ratings) {
      if (rating == -1) {
        rating = 0;
      }
      rating = rating + i;
    }

    rating = rating / this.ratings.size();

    return rating;
  }
}
