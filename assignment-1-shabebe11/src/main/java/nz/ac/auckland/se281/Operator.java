package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Operator {
  private String name;
  private String city;
  private String code;
  private String searchString;
  private ArrayList<Activity> activities;
  private int highestRating;
  private Activity highestRated;

  private int count;

  public Operator(String name, String city, String code, String searchString) {
    this.name = name;
    this.city = city;
    this.code = code;
    this.searchString = searchString;
    this.activities = new ArrayList<>();
    this.count = 0;
    this.highestRated = null;
    this.highestRating = -1;
  }

  public String getName() {
    return name;
  }

  public String getCity() {
    return city;
  }

  public String getCode() {
    return code;
  }

  public String getSearch() {
    return searchString;
  }

  public String getLocationSearch() {
    return Types.Location.fromString(this.city).getNameEnglish().toLowerCase()
        + Types.Location.fromString(this.city).getNameTeReo().toLowerCase()
        + Types.Location.fromString(this.city).getLocationAbbreviation().toLowerCase();
  }

  public void addActivity(Activity newActivity) {
    activities.add(newActivity);
    this.count++;
  }

  public int countActivities() {
    return activities.size();
  }

  public String getActivityCode(String name) {
    int counter = 0;
    for (int i = 0; i < activities.size(); i++) {
      if (activities.get(i).getName().equals(name)) {
        counter = i + 1;
      }
    }

    return code + "-" + String.format("%03d", counter);
  }

  public int activityCount(String keyword) {
    int count = 0;
    if (keyword.equals("*")) {
      count = activities.size();
      return count;
    }
    for (Activity activity : activities) {
      if (activity.getName().toLowerCase().contains(keyword)
          || activity.getType().toLowerCase().contains(keyword)) {
        count++;
      }
    }
    return count;
  }

  public void searchActivities(String keyword) {
    for (Activity activity : activities) {
      if (keyword.equals("*")) {
        MessageCli.ACTIVITY_ENTRY.printMessage(
            activity.getName(),
            this.getActivityCode(activity.getName()),
            activity.getType(),
            this.name);
      } else if (activity.getName().toLowerCase().contains(keyword)
          || activity.getType().toLowerCase().contains(keyword)) {
        MessageCli.ACTIVITY_ENTRY.printMessage(
            activity.getName(),
            this.getActivityCode(activity.getName()),
            activity.getType(),
            this.name);
      }
    }
  }

  public void showReviews(String keyword) {
    for (Activity activity : activities) {
      if (activity.getCode().equals(keyword)) {
        activity.displayReviews();
      }
    }
  }

  public void addReview(String keyword, String[] options, String type) {
    for (Activity activity : activities) {
      if (activity.getCode().equals(keyword)) {
        if (type.equals("public")) {
          activity.addPublicReview(keyword, options);
          return;
        }
        if (type.equals("private")) {
          activity.addPrivateReview(keyword, options);
          return;
        }
        if (type.equals("expert")) {
          activity.addExpertReview(keyword, options);
          return;
        }
      }
    }
    MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(keyword);
  }

  public void endorseReview(String keyword) {
    int index = keyword.lastIndexOf("-");
    String key = keyword.substring(0, index);
    for (Activity activity : activities) {
      if (activity.getCode().equals(key)) {
        activity.endorseReview(keyword);
      }
    }
  }

  public void resolveReview(String keyword, String message) {
    int index = keyword.lastIndexOf("-");
    String key = keyword.substring(0, index);
    for (Activity activity : activities) {
      if (activity.getCode().equals(key)) {
        activity.resolveReview(keyword, message);
      }
    }
  }

  public void addImage(String keyword, String image) {
    int index = keyword.lastIndexOf("-");
    String key = keyword.substring(0, index);
    for (Activity activity : activities) {
      if (activity.getCode().equals(key)) {
        activity.addImage(keyword, image);
      }
    }
  }

  public Activity highestRated() {
    for (Activity activity : activities) {
      if (this.highestRated == null || activity.getRating() > this.highestRating) {
        this.highestRated = activity;
        this.highestRating = activity.getRating();
      }
    }
    return this.highestRated;
  }
}
