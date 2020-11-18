package main;

import common.Constants;

import java.util.ArrayList;
import java.util.Hashtable;

public class Movie extends Video{
  private int length;
  private ArrayList<Double> ratings;
  private ArrayList<String> ratedBy;

  public Movie(String title, int year, ArrayList<String> genres, ArrayList<String> cast, int length) {
    super(title, year, genres, cast);
    this.length = length;
    this.ratings = new ArrayList<>();
    this.ratedBy = new ArrayList<>();
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public ArrayList<Double> getRatings() {
    return ratings;
  }

  public void setRatings(ArrayList<Double> ratings) {
    this.ratings = ratings;
  }

  public ArrayList<String> getRatedBy() {
    return ratedBy;
  }

  public void setRatedBy(ArrayList<String> ratedBy) {
    this.ratedBy = ratedBy;
  }

  public double makeMovieRating() {
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

  public String addRating(double rating, String username, Hashtable<String, User> users) {
    for (String i : ratedBy) {
      if (i.equals(username)) {
        return Constants.err + this.getTitle() + Constants.alreadyVoted;
      }
    }

    this.ratings.add(rating);
    this.ratedBy.add(username);
    users.get(username).setNoRatings(users.get(username).getNoRatings() + 1);
    return Constants.rateSuccess1 + this.getTitle() + Constants.rateSuccess2 + rating + Constants.rateSuccess3
            + username;
  }
}
