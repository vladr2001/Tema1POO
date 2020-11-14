package main;

import java.util.ArrayList;

public class User {
  public String category;
  public ArrayList<Video> favs;
  public ArrayList<Watched> views;

  public User(String category, ArrayList<Video> favs, ArrayList<Watched> views) {
    int sem = 0;
    this.category = category;
    this.favs = new ArrayList<>();
    this.views = new ArrayList<>();
    for (Watched i:views) {
      this.views.add(i);
    }

    for (Video i:favs) {
      sem = 0;
      for (Watched j:views) {
        if (i.title == j.video.title) {
          sem = 1;
        }
      }

      if (sem == 1) {
        this.favs.add(i);
      }
    }
  }
}
