package main;

import java.util.Comparator;

public class CustomComparatorAwards implements Comparator<Actor> {

  @Override
  public int compare(Actor a1, Actor a2) {
      for (String i : a1.getRelevantAwards()) {
        if (a1.getAwards().get(i) != a2.getAwards().get(i)) {
          return a1.getAwards().get(i) - a2.getAwards().get(i);
        }
      }
      return 0;
  }
}
