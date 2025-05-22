package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Activity {
  private String name;
  private String type;
  private String code;
  private ArrayList<Review> reviews;

  public Activity(String name, String type) {
    this.name = name;
    this.type = type;
    this.reviews = new ArrayList<Review>();
  }

  public String getName() {
    return this.name;
  }

  public String getType() {
    return this.type;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }

  public void addPublicReview(String keyword, String[] options) {
    int count = reviews.size() + 1;
    String code = this.code + "-R" + count;
    Review newReview = new PublicReview(options[0], options[1], options[2], options[3], code);
    MessageCli.REVIEW_ADDED.printMessage("Public", code, this.name);
    reviews.add(newReview);
  }

  public void addPrivateReview(String keyword, String[] options) {
    int count = reviews.size() + 1;
    String code = this.code + "-R" + count;
    Review newReview =
        new PrivateReview(options[0], options[1], options[2], options[3], options[4], code);
    MessageCli.REVIEW_ADDED.printMessage("Private", code, this.name);
    reviews.add(newReview);
  }

  public void addExpertReview(String keyword, String[] options) {
    int count = reviews.size() + 1;
    String code = this.code + "-R" + count;
    Review newReview = new ExpertReview(options[0], options[1], options[2], options[3], code);
    MessageCli.REVIEW_ADDED.printMessage("Expert", code, this.name);
    reviews.add(newReview);
  }

  public void displayReviews() {
    if (reviews.size() == 0) {
      MessageCli.REVIEWS_FOUND.printMessage("are", "no", "s", this.name, ".");
      return;
    } else if (reviews.size() == 1) {
      MessageCli.REVIEWS_FOUND.printMessage("is", "1", "", this.name);
      for (Review review : reviews) {
        review.printReview();
      }
      return;
    } else if (reviews.size() == 2) {
      MessageCli.REVIEWS_FOUND.printMessage("are", String.valueOf(reviews.size()), "s", this.name);
      for (Review review : reviews) {
        review.printReview();
      }
      return;
    }
  }

  public void endorseReview(String keyword) {
    for (Review review : reviews) {
      if (review.getCode().equals(keyword)) {
        review.endorseReview();
      }
    }
  }

  public void resolveReview(String keyword, String message) {
    for (Review review : reviews) {
      if (review.getCode().equals(keyword)) {
        review.resolveReview();
        return;
      }
    }
    MessageCli.REVIEW_NOT_FOUND.printMessage(keyword);
  }

  public void addImage(String keyword, String image) {
    for (Review review : reviews) {
      if (review.getCode().equals(keyword)) {
        review.addImage(image);
        return;
      }
    }
    MessageCli.REVIEW_NOT_FOUND.printMessage(keyword);
  }

  public int getRating() {
    int sum = 0;
    for (Review review : reviews) {
      sum += review.getRating();
    }
    return sum / reviews.size();
  }
}
