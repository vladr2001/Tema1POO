package main;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import common.Constants;
import entertainment.Season;

public class Show extends Video{
  private ArrayList<mySeason> seasons;
  private double rating;
  private int length;

  public Show(String title, int year, ArrayList<String> genres, ArrayList<String> cast, ArrayList<Season> s) {
    super(title, year, genres, cast, 0);
    mySeason aux;
    this.seasons =  new ArrayList<>();
    for (int i = 0; i < s.size(); i++) {
      aux = new mySeason(i + 1, s.get(i).getRatings(), s.get(i).getDuration());
      this.seasons.add(aux);
    }
    this.rating = 0;
  }

  public ArrayList<mySeason> getSeasons() {
    return seasons;
  }

  public void setSeasons(ArrayList<mySeason> seasons) {
    this.seasons = seasons;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public void makeDuration() {
    int duration = 0;
    for (mySeason s : seasons) {
      duration = duration + s.getDuration();
    }
    this.setDuration(duration);
  }

  public double makeShowRating() {
    double rating = -1;
    int noRates = 0;

    for (mySeason i : this.seasons) {
      if (i.makeSeasonRating() != 0.00) {
        if (rating == -1) {
          rating = 0;
        }
        rating = rating + i.makeSeasonRating();
        noRates = noRates + 1;
      }
    }

    if (rating != -1) {
      rating = rating / this.seasons.size();
    }

    return rating;
  }

  public String addRating(double rating, int seasonNo, String username, LinkedHashMap<String, User> users) {
    User u = users.get(username);

    for (String i : this.seasons.get(seasonNo - 1).getRatedBy()) {
      if (i.equals(username)) {
        return Constants.err + this.getTitle() + Constants.alreadyVoted;
      }
    }

    if (!u.getViews().containsKey(this.getTitle())) {
      return Constants.err + this.getTitle() + Constants.notSeen;
    }

    this.seasons.get(seasonNo - 1).addRating(rating);
    this.seasons.get(seasonNo - 1).addRatedBy(username);
    users.get(username).setNoRatings(users.get(username).getNoRatings() + 1);
    return Constants.rateSuccess1 + this.getTitle() + Constants.rateSuccess2 + rating + Constants.rateSuccess3 + username;
  }
}
