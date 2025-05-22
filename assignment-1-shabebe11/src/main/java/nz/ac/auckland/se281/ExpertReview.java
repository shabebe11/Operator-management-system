package nz.ac.auckland.se281;

public class ExpertReview extends Review {

  protected String reccomend;
  protected String image;
  protected boolean isImage;

  public ExpertReview(String name, String rating, String comment, String reccomend, String code) {
    super(name, Integer.parseInt(rating), comment, code);
    this.reccomend = reccomend;
    this.image = null;
  }

  @Override
  public void printReview() {
    MessageCli.REVIEW_ENTRY_HEADER.printMessage(
        String.valueOf(this.rating), "5", "Expert", this.code, this.name);
    MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(this.comment);
    MessageCli.REVIEW_ENTRY_RECOMMENDED.printMessage();
    if (isImage) {
      MessageCli.REVIEW_ENTRY_IMAGES.printMessage(this.image);
    }
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
  public void addImage(String image) {
    if (this.image == null) {
      this.image = image;
    } else {
      this.image = this.image + "," + image;
    }
    this.isImage = true;
    MessageCli.REVIEW_IMAGE_ADDED.printMessage(image, this.code);
  }
}
