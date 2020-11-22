package entertainment;

// Clasa pentru genre
public class Genres implements Comparable<Genres> {
  private String name;
  private int apparitions;

  public Genres(final String name, final int apparitions) {
    this.name = name;
    this.apparitions = apparitions;
  }

  public final String getName() {
    return name;
  }

  public final void setName(final String name) {
    this.name = name;
  }

  public final int getApparitions() {
    return apparitions;
  }

  public final void setApparitions(final int apparitions) {
    this.apparitions = apparitions;
  }

  /**
   *
   * @param o obiect auxiliar
   * @return rezultatul comparatiei
   */
  @Override
  public int compareTo(final Genres o) {
    if (Integer.compare(this.getApparitions(), o.getApparitions()) != 0) {
      return Integer.compare(this.getApparitions(), o.getApparitions());
    } else {
      return String.CASE_INSENSITIVE_ORDER.compare(this.getName(), o.getName());
    }
  }
}
