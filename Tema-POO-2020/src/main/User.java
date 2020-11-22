package main;

import common.Constants;

import java.util.ArrayList;
import java.util.Map;

public class User implements Comparable<User> {
  private String username;
  private String category;
  private ArrayList<String> favs;
  private Map<String, Integer> views;
  private Integer noRatings;

  public User(String username, String category, ArrayList<String> favs, Map<String, Integer> views) {
    this.username = username;
    this.category = category;
    this.views = views;
    this.favs = new ArrayList<>();
    this.noRatings = 0;

    for (String fav : favs) {
      if (views.get(fav) != null) {
        this.favs.add(fav);
      }
    }
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public ArrayList<String> getFavs() {
    return favs;
  }

  public void setFavs(ArrayList<String> favs) {
    this.favs = favs;
  }

  public Map<String, Integer> getViews() {
    return views;
  }

  public void setViews(Map<String, Integer> views) {
    this.views = views;
  }

  public Integer getNoRatings() {
    return this.noRatings;
  }

  public void setNoRatings(int noRatings) {
    this.noRatings = noRatings;
  }

  public String addFavourite(String fav) {
    // System.out.println("intra in fav");
    if (fav == null) {
      return null;
    }
    if (this.views.get(fav) == null) {
      return Constants.favErrInvalid1 + fav + Constants.favErrInvalid2;
    }

    for (String i : this.favs) {
      if (i.equals(fav)) {
        return Constants.favErrDuplicate1 + fav + Constants.getFavErrDuplicate2;
      }
    }

    this.favs.add(fav);
    return Constants.favSuccess1 + fav + Constants.getFavSuccess2;
  }

  public String addView(String video) {
    if (video == null) {
      return null;
    }

    if (this.views.containsKey(video)) {
      this.views.replace(video, this.views.get(video) + 1);
      return Constants.viewSuccess1 + video + Constants.viewSuccess2 + this.views.get(video);
    } else {
      this.views.put(video, Constants.viewConst);
      return Constants.viewSuccess1 + video + Constants.viewSuccess2 + Constants.viewConst;
    }
  }

  @Override
  public int compareTo(User o) {
    if (!this.getNoRatings().equals(o.getNoRatings())) {
      return Integer.compare(this.getNoRatings(), o.getNoRatings());
    } else {
      return String.CASE_INSENSITIVE_ORDER.compare(this.getUsername(), o.getUsername());
    }
  }
}
