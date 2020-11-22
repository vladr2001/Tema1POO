package entertainment;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import common.Constants;

public class Show extends Video {
  private ArrayList<MySeason> seasons;
  private double rating;
  private int length;

  public Show(final String title, final int year, final ArrayList<String> genres,
              final ArrayList<String> cast, final ArrayList<Season> s) {
    super(title, year, genres, cast, 0);
    MySeason aux;
    this.seasons =  new ArrayList<>();
    for (int i = 0; i < s.size(); i++) {
      aux = new MySeason(i + 1, s.get(i).getRatings(), s.get(i).getDuration());
      this.seasons.add(aux);
    }
    this.rating = 0;
  }

  public final ArrayList<MySeason> getSeasons() {
    return seasons;
  }

  public final void setSeasons(final ArrayList<MySeason> seasons) {
    this.seasons = seasons;
  }

  public final double getRating() {
    return rating;
  }

  public final void setRating(final float rating) {
    this.rating = rating;
  }

  /**
   *   Adauga duratele sezoanelor pentru a forma durata serialului
   */
  public final void makeDuration() {
    int duration = 0;
    for (MySeason s : seasons) {
      duration = duration + s.getDuration();
    }
    this.setDuration(duration);
  }

  /**
   *
   * @return nota serialului
   */
  public final double makeShowRating() {
    // Face media sezoanelor pentru a face media serialului
    // Daca nu au note intoarce -1
    double rate = -1;

    for (MySeason i : this.seasons) {
      if (i.makeSeasonRating() != 0.00) {
        if (rate == -1) {
          rate = 0;
        }
        rate = rate + i.makeSeasonRating();
      }
    }

    if (rate != -1) {
      rate = rate / this.seasons.size();
    }

    return rate;
  }

  /**
   *
   * @param rate nota noua
   * @param seasonNo sezonul la care trebuie adaugata nota
   * @param username numele de utilizator
   * @param users structura de date cu toti utilizatorii
   * @return string-ul de output
   */
  public final String addRating(final double rate, final int seasonNo,
                                final String username, final LinkedHashMap<String, User> users) {
    // Daca nu a dat deja nota la sezonul acela sau nu l-a vizionat
    // ii adauga nota
    User u = users.get(username);

    for (String i : this.seasons.get(seasonNo - 1).getRatedBy()) {
      if (i.equals(username)) {
        return Constants.ERR + this.getTitle() + Constants.ALREADYVOTED;
      }
    }

    if (!u.getViews().containsKey(this.getTitle())) {
      return Constants.ERR + this.getTitle() + Constants.NOTSEEN;
    }

    this.seasons.get(seasonNo - 1).addRating(rate);
    this.seasons.get(seasonNo - 1).addRatedBy(username);
    users.get(username).setNoRatings(users.get(username).getNoRatings() + 1);
    return Constants.SUCCESS + this.getTitle() + Constants.RATESUCCESS1
            + rate + Constants.RATESUCCESS2 + username;
  }

  /**
   *
   * @return titlul serialului
   */
  @Override
  public String toString() {
    return this.getTitle();
  }
}
