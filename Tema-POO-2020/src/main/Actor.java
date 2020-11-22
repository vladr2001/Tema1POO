package main;

import actor.ActorsAwards;

import java.util.*;

public class Actor implements  Comparable<Actor>{
  private String name;
  private String careerDesc;
  private ArrayList<String> videos;
  private Hashtable<String, Integer> awards;
  private Double rating;
  private List<String> relevantAwards;
  private String queryCriteria;
  private int noAwards;

  public Actor(String name, String careerDesc, ArrayList<String> videos, Map<ActorsAwards, Integer> awards) {
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCareerDesc() {
    return careerDesc;
  }

  public void setCareerDesc(String careerDesc) {
    this.careerDesc = careerDesc;
  }

  public ArrayList<String> getVideos() {
    return videos;
  }

  public void setVideos(ArrayList<String> videos) {
    this.videos = videos;
  }

  public Map<String, Integer> getAwards() {
    return awards;
  }

  public void setAwards(Hashtable<String, Integer> awards) {
    this.awards = awards;
  }

  public double getRating() {
    return this.rating;
  }

  public void setRelevantAwards(List<String> relAwards) {
    this.relevantAwards = relAwards;
  }

  public List<String> getRelevantAwards() {
    return this.relevantAwards;
  }

  public void setQueryCriteria(String c) {
    this.queryCriteria = c;
  }

  public String getQueryCriteria() {
    return this.queryCriteria;
  }

  public void setNoAwards(int noAwards) {
    this.noAwards = noAwards;
  }

  public int getNoAwards() {
    return this.noAwards;
  }

  public void makeRating(LinkedHashMap<String, Movie> movies, LinkedHashMap<String, Show> shows) {
    double rating = -1;
    int noRates = 0;
    Movie movie;
    Show show;
    // System.out.println("Videourile sunt " + this.videos);
    for (int i = 0; i < this.videos.size(); i++) {
      if (movies.get(this.videos.get(i)) != null) {
        // System.out.println("S a gasit ca film");
        movie = movies.get(this.videos.get(i));
        if (movie.getRatings().size() != 0) {
          // System.out.println("un film cu rating");
          noRates = noRates + 1;
          if (rating == -1) {
            rating = 0.00;
          }
          rating = rating + movie.makeMovieRating();
        }
      } else if (shows.get(this.videos.get(i)) != null) {
       // System.out.println("S a gasit ca serial");
        show = shows.get(this.videos.get(i));
        if (show.makeShowRating() != -1) {
         // System.out.println("un serial cu rating");
          noRates = noRates + 1;
          if (rating == -1) {
            rating = 0.00;
          }
          rating = rating + show.makeShowRating();
        }
      }
    }

    if (rating != -1) {
      rating = rating / noRates;
    }

    this.rating = rating;
  }

  @Override
  public int compareTo(Actor o) {
    if (this.queryCriteria.equals("average")) {
      if (Double.compare(this.rating, o.rating) != 0) {
        return Double.compare(this.rating, o.rating);
      } else {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getName(), o.getName());
      }
    } else if (this.queryCriteria.equals("awards")) {
      if (this.noAwards != o.noAwards) {
        return Integer.compare(this.noAwards, o.noAwards);
      }
      return String.CASE_INSENSITIVE_ORDER.compare(this.getName(), o.getName());
    } else if (this.queryCriteria.equals("filter_description")) {
      return String.CASE_INSENSITIVE_ORDER.compare(this.getName(), o.getName());
    }

    return 0;
  }
}
