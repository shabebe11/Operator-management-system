package nz.ac.auckland.se281;

public abstract class Review {

  protected String name;
  protected int rating;
  protected String comment;
  protected String code;

  public Review(String name, int rating, String comment, String code) {
    this.name = name;
    this.rating = rating;
    this.comment = comment;
    this.code = code;
  }

  public abstract void printReview();

  public abstract void endorseReview();

  public abstract String getCode();

  public abstract void resolveReview();

  public abstract void addImage(String image);

  public int getRating() {
    return this.rating;
  }
}
