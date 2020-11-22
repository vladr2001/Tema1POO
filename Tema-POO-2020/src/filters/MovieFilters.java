package filters;

import entertainment.Movie;

import java.util.ArrayList;

public class MovieFilters {
  private ArrayList<Movie> videos;

  public MovieFilters() {
    this.videos = new ArrayList<>();
  }

  /**
   *
   * @param v un film
   */
  public final void addVideo(final Movie v) {
    this.videos.add(v);
  }

  public final void setVideos(final ArrayList<Movie> videos) {
    this.videos = videos;
  }

  public final ArrayList<Movie> getVideos() {
    return this.videos;
  }

  /**
   *
   * @param year anul dupa care trebuie filtrata lista
   * @return lista noua(doar cu videoclipurile din acel an)
   */
  public final ArrayList<Movie> filterByYear(final int year) {
    ArrayList<Movie> newVideos = new ArrayList<>();
    for (Movie i : this.getVideos()) {
      if (i.getYear() == year) {
        newVideos.add(i);
      }
    }

    return newVideos;
  }

  /**
   *
   * @param genre genul dupa care trebuie filtrata lista
   * @return lista noua(doar cu videoclipurile cu acel gen)
   */
  public final ArrayList<Movie> filterByGenre(final String genre) {
    ArrayList<Movie> newVideos = new ArrayList<>();
    for (Movie i : this.getVideos()) {
      if (i.getGenres().contains(genre)) {
        newVideos.add(i);
      }
    }

    return newVideos;
  }
}
