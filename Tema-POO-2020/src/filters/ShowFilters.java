package filters;

import entertainment.Show;

import java.util.ArrayList;

public class ShowFilters {
  private ArrayList<Show> videos;

  public ShowFilters() {
    this.videos = new ArrayList<>();
  }

  /**
   *
   * @param v un serial
   */
  public final void addVideo(final Show v) {
    this.videos.add(v);
  }

  public final void setVideos(final ArrayList<Show> videos) {
    this.videos = videos;
  }

  public final ArrayList<Show> getVideos() {
    return this.videos;
  }

  /**
   *
   * @param year anul dupa care e filtrata lista
   * @return lista noua, in care sunt doar seriale din acel an
   */
  public final ArrayList<Show> filterByYear(final int year) {
    ArrayList<Show> newVideos = new ArrayList<>();
    for (Show i : this.getVideos()) {
      if (i.getYear() == year) {
        newVideos.add(i);
      }
    }

    return newVideos;
  }

  /**
   *
   * @param genre genul dupa care trebuie filtrata
   * @return lista noua, in care sunt doar videoclipurile din acel gen
   */
  public final ArrayList<Show> filterByGenre(final String genre) {
    ArrayList<Show> newVideos = new ArrayList<>();
    for (Show i : this.getVideos()) {
      if (i.getGenres().contains(genre)) {
        newVideos.add(i);
      }
    }

    return newVideos;
  }
}
