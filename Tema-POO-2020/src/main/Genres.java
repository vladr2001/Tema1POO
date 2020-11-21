package main;

public class Genres implements Comparable<Genres> {
  private String name;
  private int apparitions;

  public Genres(String name, int apparitions) {
    this.name = name;
    this.apparitions = apparitions;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getApparitions() {
    return apparitions;
  }

  public void setApparitions(int apparitions) {
    this.apparitions = apparitions;
  }

  @Override
  public int compareTo(Genres o) {
    if (Integer.compare(this.getApparitions(), o.getApparitions()) != 0) {
      return Integer.compare(this.getApparitions(), o.getApparitions());
    } else {
      return String.CASE_INSENSITIVE_ORDER.compare(this.getName(), o.getName());
    }
  }
}
