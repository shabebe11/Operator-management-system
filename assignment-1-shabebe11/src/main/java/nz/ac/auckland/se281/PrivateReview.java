package nz.ac.auckland.se281;

public class PrivateReview extends Review {

  protected String email;
  protected boolean follow;

  public PrivateReview(
      String name, String email, String rating, String comment, String follow, String code) {
    super(name, Integer.parseInt(rating), comment, code);
    this.email = email;

    if (follow.equals("n")) {
      this.follow = false;
    } else {
      this.follow = true;
    }
  }

  @Override
  public void printReview() {
    MessageCli.REVIEW_ENTRY_HEADER.printMessage(
        String.valueOf(this.rating), "5", "Private", this.code, this.name);

    if (this.follow == false) {
      MessageCli.REVIEW_ENTRY_RESOLVED.printMessage("-");
    } else {
      MessageCli.REVIEW_ENTRY_FOLLOW_UP.printMessage(this.email);
    }
    MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(this.comment);
  }

  @Override
  public void endorseReview() {
    MessageCli.REVIEW_NOT_ENDORSED.printMessage(this.code);
  }

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public void resolveReview() {
    this.follow = false;
    MessageCli.REVIEW_RESOLVED.printMessage(this.code);
  }

  @Override
  public void addImage(String image) {
    MessageCli.REVIEW_IMAGE_NOT_ADDED_NOT_EXPERT.printMessage(this.code);
  }
}
