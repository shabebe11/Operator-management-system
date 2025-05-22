package nz.ac.auckland.se281;

public class PublicReview extends Review {

  protected String annonymous;
  protected boolean endoresed;

  public PublicReview(String name, String annonymous, String rating, String comment, String code) {
    super(name, Integer.parseInt(rating), comment, code);
    this.annonymous = annonymous;
    this.endoresed = false;
  }

  @Override
  public void printReview() {
    if (this.annonymous.equals("n")) {
      MessageCli.REVIEW_ENTRY_HEADER.printMessage(
          String.valueOf(this.rating), "5", "Public", this.code, this.name);
    } else {
      MessageCli.REVIEW_ENTRY_HEADER.printMessage(
          String.valueOf(this.rating), "5", "Public", this.code, "Anonymous");
    }
    MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(this.comment);
    if (this.endoresed) {
      MessageCli.REVIEW_ENTRY_ENDORSED.printMessage();
    }
  }

  @Override
  public void endorseReview() {
    MessageCli.REVIEW_ENDORSED.printMessage(this.code);
    this.endoresed = true;
  }

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public void addImage(String image) {
    MessageCli.REVIEW_IMAGE_NOT_ADDED_NOT_EXPERT.printMessage(this.code);
  }
}
