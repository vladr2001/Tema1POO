package entertainment;

import common.Constants;

import java.util.ArrayList;

public class Video implements  Comparable<Video> {
  private String title;
  private int year;
  private ArrayList<String> cast;
  private ArrayList<String> genres;
  private double videoRating;
  private String criteria;
  private int noFavs;
  private int duration;
  private int noViews;


  public Video(final String title, final int year, final ArrayList<String> genres,
               final ArrayList<String> cast, final int length) {
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

  public final String getTitle() {
    return title;
  }

  public final void setTitle(final String title) {
    this.title = title;
  }

  public final int getYear() {
    return year;
  }

  public final void setYear(final int year) {
    this.year = year;
  }

  public final ArrayList<String> getCast() {
    return cast;
  }

  public final void setCast(final ArrayList<String> cast) {
    this.cast = cast;
  }

  public final ArrayList<String> getGenres() {
    return genres;
  }

  public final void setGenres(final ArrayList<String> genres) {
    this.genres = genres;
  }

  public final void setVideoRating(final double rating) {
    this.videoRating = rating;
  }

  public final double getVideoRating() {
    return this.videoRating;
  }

  public final void setCriteria(final String crit) {
    this.criteria = crit;
  }

  public final String getCriteria() {
    return this.criteria;
  }

  public final void setDuration(final int duration) {
    this.duration = duration;
  }

  public final int getDuration() {
    return this.duration;
  }

  public final int getNoFavs() {
    return this.noFavs;
  }

  public final void setNoFavs(final int favs) {
    this.noFavs = favs;
  }

  public final void setNoViews(final int views) {
    this.noViews = views;
  }

  /**
   *
   */
  public final void addFav() {
    this.noFavs = this.noFavs + 1;
  }

  /**
   *
   * @param views numaru de views care trebuie adaugat
   */
  public final void addViews(final int views) {
    this.noViews = this.noViews + views;
  }

  public final int getNoViews() {
    return this.noViews;
  }

  /**
   *
   * @param o obiectul auxiliar
   * @return rezultatul comparatiei
   */
  @Override
  public int compareTo(final Video o) {
    if (this.getCriteria().equals(Constants.RATINGS)) {
      if (Double.compare(this.videoRating, o.videoRating) != 0) {
        return Double.compare(this.videoRating, o.videoRating);
      } else {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getTitle(), o.getTitle());
      }
    }
    if (this.getCriteria().equals(Constants.FAVORITE)) {
      if (Integer.compare(this.getNoFavs(), o.getNoFavs()) != 0) {
        return Integer.compare(this.getNoFavs(), o.getNoFavs());
      } else {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getTitle(), o.getTitle());
      }
    }
    if (this.getCriteria().equals(Constants.LONGEST)) {
      if (Integer.compare(this.duration, o.duration) != 0) {
        return Integer.compare(this.duration, o.duration);
      } else {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getTitle(), o.getTitle());
      }
    }
    if (this.getCriteria().equals(Constants.MOSTVIEWED)) {
      if (Integer.compare(this.getNoViews(), o.getNoViews()) != 0) {
        return Integer.compare(this.getNoViews(), o.getNoViews());
      } else {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getTitle(), o.getTitle());
      }
    }

    if (this.getCriteria().equals(Constants.RATINGSRECOM)) {
      return Double.compare(this.videoRating, o.videoRating);
    }

    if (this.getCriteria().equals(Constants.FAVRECOM)) {
      return Integer.compare(this.getNoFavs(), o.getNoFavs());
    }
    return 0;
  }
}
