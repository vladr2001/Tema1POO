package actor;

import common.Constants;
import entertainment.Movie;
import entertainment.Show;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Clasa pentru actori
public class Actor implements  Comparable<Actor> {
  private final String name;
  private String careerDesc;
  private final ArrayList<String> videos;
  private final Hashtable<String, Integer> awards;
  private Double rating;
  private List<String> relevantAwards;
  private String queryCriteria;
  private int noAwards;

  public Actor(final String name, final String careerDesc, final ArrayList<String> videos,
               final Map<ActorsAwards, Integer> awards) {
    this.name = name;
    this.careerDesc = careerDesc;
    this.videos = videos;
    this.rating = 0.00;
    this.awards = new Hashtable<String, Integer>();
    this.noAwards = 0;
    for (ActorsAwards a : awards.keySet()) {
      String s = a.toString();
      int nr = awards.get(a);
      this.awards.put(s, nr);
      this.noAwards = this.noAwards + nr;
    }
  }

  public final String getName() {
    return name;
  }

  public final String getCareerDesc() {
    return careerDesc;
  }

  public final void setCareerDesc(final String careerDesc) {
    this.careerDesc = careerDesc;
  }

  public final ArrayList<String> getVideos() {
    return videos;
  }

  public final Map<String, Integer> getAwards() {
    return awards;
  }

  public final double getRating() {
    return this.rating;
  }

  public final void setRelevantAwards(final List<String> relAwards) {
    this.relevantAwards = relAwards;
  }

  public final List<String> getRelevantAwards() {
    return this.relevantAwards;
  }

  public final void setQueryCriteria(final String c) {
    this.queryCriteria = c;
  }

  public final String getQueryCriteria() {
    return this.queryCriteria;
  }

  public final void setNoAwards(final int noAwards) {
    this.noAwards = noAwards;
  }

  public final int getNoAwards() {
    return this.noAwards;
  }

  /**
   *
   * @param movies o structura de date cu toate filmele
   * @param shows o structura de date cu toate serialele
   */
  public final void makeRating(final LinkedHashMap<String, Movie> movies,
                         final LinkedHashMap<String, Show> shows) {
    // Face rating-ul pentru fiecare video si apoi rating-ul actorului
    double rate = -1;
    int noRates = 0;
    Movie movie;
    Show show;
    for (int i = 0; i < this.videos.size(); i++) {
      if (movies.get(this.videos.get(i)) != null) {
        movie = movies.get(this.videos.get(i));
        if (movie.getRatings().size() != 0) {
          noRates = noRates + 1;
          if (rate == -1) {
            rate = 0.00;
          }
          rate = rate + movie.makeMovieRating();
        }
      } else if (shows.get(this.videos.get(i)) != null) {
        show = shows.get(this.videos.get(i));
        if (show.makeShowRating() != -1) {
          noRates = noRates + 1;
          if (rate == -1) {
            rate = 0.00;
          }
          rate = rate + show.makeShowRating();
        }
      }
    }

    if (rate != -1) {
      rate = rate / noRates;
    }

    this.rating = rate;
  }

  /**
   *
   * @param o obiectul auxiliar
   * @return rezultatul comparatiei
   */
  @Override
  public int compareTo(final Actor o) {
    if (this.queryCriteria.equals(Constants.AVERAGE)) {
      if (Double.compare(this.rating, o.rating) != 0) {
        return Double.compare(this.rating, o.rating);
      } else {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getName(), o.getName());
      }
    } else if (this.queryCriteria.equals(Constants.AWARDS)) {
      if (this.noAwards != o.noAwards) {
        return Integer.compare(this.noAwards, o.noAwards);
      }
      return String.CASE_INSENSITIVE_ORDER.compare(this.getName(), o.getName());
    } else if (this.queryCriteria.equals(Constants.FILTER_DESCRIPTIONS)) {
      return String.CASE_INSENSITIVE_ORDER.compare(this.getName(), o.getName());
    }

    return 0;
  }
}
