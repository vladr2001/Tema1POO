package entertainment;

import common.Constants;

import java.util.ArrayList;
import java.util.Map;

public class User implements Comparable<User> {
  private String username;
  private String category;
  private ArrayList<String> favs;
  private Map<String, Integer> views;
  private Integer noRatings;

  public User(final String username, final String category,
              final ArrayList<String> favs, final Map<String, Integer> views) {
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

  public final String getUsername() {
    return username;
  }

  public final void setUsername(final String username) {
    this.username = username;
  }

  public final String getCategory() {
    return category;
  }

  public final void setCategory(final String category) {
    this.category = category;
  }

  public final ArrayList<String> getFavs() {
    return favs;
  }

  public final void setFavs(final ArrayList<String> favs) {
    this.favs = favs;
  }

  public final Map<String, Integer> getViews() {
    return views;
  }

  public final void setViews(final Map<String, Integer> views) {
    this.views = views;
  }

  public final Integer getNoRatings() {
    return this.noRatings;
  }

  public final void setNoRatings(final int noRatings) {
    this.noRatings = noRatings;
  }

  /**
   *
   * @param fav numele videoclipului
   * @return output-ul
   */
  public final String addFavourite(final String fav) {
    // Daca l-a vizionat si nu il are deja la favorite
    // videoclipul este adaugat in lista de favorite
    if (fav == null) {
      return null;
    }
    if (this.views.get(fav) == null) {
      return Constants.FAVERRINVALID1 + fav + Constants.FAVERRINVALID2;
    }

    for (String i : this.favs) {
      if (i.equals(fav)) {
        return Constants.FAVERRDUPLICATE1 + fav + Constants.FAVERRDUPLICATE2;
      }
    }

    this.favs.add(fav);
    return Constants.SUCCESS + fav + Constants.FAVSUCCESS2;
  }

  /**
   *
   * @param video numele videoclipului
   * @return string-ul de output
   */
  public final String addView(final String video) {
    // Adauga un view la acest video
    if (video == null) {
      return null;
    }

    if (this.views.containsKey(video)) {
      this.views.replace(video, this.views.get(video) + 1);
      return Constants.SUCCESS + video + Constants.VIEWSUCCESS + this.views.get(video);
    } else {
      this.views.put(video, Constants.VIEWCONST);
      return Constants.SUCCESS + video + Constants.VIEWSUCCESS + Constants.VIEWCONST;
    }
  }

  /**
   *
   * @param o obiectul auxiliar
   * @return rezultatul comparatiei
   */
  @Override
  public int compareTo(final User o) {
    if (!this.getNoRatings().equals(o.getNoRatings())) {
      return Integer.compare(this.getNoRatings(), o.getNoRatings());
    } else {
      return String.CASE_INSENSITIVE_ORDER.compare(this.getUsername(), o.getUsername());
    }
  }
}
