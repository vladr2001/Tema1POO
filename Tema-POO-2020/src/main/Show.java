package main;

public class Show extends Video{
  public Season[] seasons;

  public Show(String title, int year, String[] genres, Season[] s) {
    super(title, year, genres);
    this.seasons =  new Season[s.length];
    for (int i = 0; i < s.length; i++) {
      this.seasons[i] = s[i];
    }
  }
}
