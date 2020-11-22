package entertainment;

import java.util.ArrayList;
import java.util.List;

// Clasa mea pentru sezon
public class MySeason {
  private int noSeason;
  private ArrayList<Double> ratings;
  private int duration;
  private ArrayList<String> ratedBy;

  public MySeason(final int noSeason, final List<Double> ratings, final int duration) {
    this.noSeason = noSeason;
    this.ratings = new ArrayList<>();
    this.ratedBy = new ArrayList<>();
    this.duration = duration;

    this.ratings.addAll(ratings);
  }

  /**
   *
   * @param rating not care trebuie adaugata
   */
  public final void addRating(final double rating) {
    this.ratings.add(rating);
  }

  /**
   *
   * @param username numele utilizatorului
   */
  public final void addRatedBy(final String username) {
    this.ratedBy.add(username);
  }

  public final int getNoSeason() {
    return noSeason;
  }

  public final void setNoSeason(final int noSeason) {
    this.noSeason = noSeason;
  }

  public final ArrayList<Double> getRatings() {
    return ratings;
  }

  public final void setRatings(final ArrayList<Double> ratings) {
    this.ratings = ratings;
  }

  public final int getDuration() {
    return duration;
  }

  public final void setDuration(final int duration) {
    this.duration = duration;
  }

  public final ArrayList<String> getRatedBy() {
    return ratedBy;
  }

  public final void setRatedBy(final ArrayList<String> ratedBy) {
    this.ratedBy = ratedBy;
  }

  /**
   *
   * @return nota sezonului
   */
  public final double makeSeasonRating() {
    // Face nota sezonului facand media notelor pe care le are deja
    // Daca nu are intoarce -1
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
