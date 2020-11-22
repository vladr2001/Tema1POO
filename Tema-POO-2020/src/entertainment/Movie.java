package entertainment;

import common.Constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Movie extends Video {
  private ArrayList<Double> ratings;
  private ArrayList<String> ratedBy;

  public Movie(final String title, final int year, final ArrayList<String> genres,
               final ArrayList<String> cast, final int length) {
    super(title, year, genres, cast, length);
    this.ratings = new ArrayList<>();
    this.ratedBy = new ArrayList<>();
  }


  public final ArrayList<Double> getRatings() {
    return ratings;
  }

  public final void setRatings(final ArrayList<Double> ratings) {
    this.ratings = ratings;
  }

  public final ArrayList<String> getRatedBy() {
    return ratedBy;
  }

  public final void setRatedBy(final ArrayList<String> ratedBy) {
    this.ratedBy = ratedBy;
  }

  /**
   *
   * @return
   */
  public final double makeMovieRating() {
    // Face nota filmului facand media notelor pe care le are deja
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

  /**
   *
   * @param rating nota care trebuie adaugata
   * @param username identificatorul user-ului
   * @param users structura de date in care sunt toti utilizatorii
   * @return
   */
  public final String addRating(final double rating, final String username,
                          final LinkedHashMap<String, User> users) {
    User u = users.get(username);

    // Verifica daca utilizatorul a vizionat filmul si nu i-a dat deja nota
    // Daca ambele criterii sunt intalnite adauga un rating
    for (String i : ratedBy) {
      if (i.equals(username)) {
        return Constants.ERR + this.getTitle() + Constants.ALREADYVOTED;
      }
    }

    if (!u.getViews().containsKey(this.getTitle())) {
      return Constants.ERR + this.getTitle() + Constants.NOTSEEN;
    }

    this.ratings.add(rating);
    this.ratedBy.add(username);
    users.get(username).setNoRatings(users.get(username).getNoRatings() + 1);
    return Constants.SUCCESS + this.getTitle()
            + Constants.RATESUCCESS1 + rating + Constants.RATESUCCESS2
            + username;
  }


  /**
   *
   * @return titlul filmului
   */
  @Override
  public String toString() {
    return this.getTitle();
  }
}
