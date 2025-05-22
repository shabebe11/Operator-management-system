package nz.ac.auckland.se281;

public class OperatorManagementSystem {

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {}

  OperatorManager operatorManager = new OperatorManager();

  public void searchOperators(String keyword) {
    operatorManager.printOperators(keyword);
  }

  public void createOperator(String operatorName, String location) {
    operatorManager.addOperator(operatorName, location);
  }

  public void viewActivities(String operatorId) {
    operatorManager.viewActivities(operatorId);
  }

  public void createActivity(String activityName, String activityType, String operatorId) {
    operatorManager.createActivity(activityName, activityType, operatorId);
    // Check that atleast 3 character long for name
    // check that the type entered works for any case
    // check that random type will come back with OTHER
  }

  public void searchActivities(String keyword) {
    operatorManager.searchActivities(keyword.toLowerCase());
    // The activity name (case insensitive),
    // The activity type (case insensitive),
    // The operator location (case insensitive, te reo Māori, English, or abbreviation)
    // Check all case sensitivity
  }

  public void addPublicReview(String activityId, String[] options) {
    operatorManager.addReview(activityId, options, "public");
  }

  public void addPrivateReview(String activityId, String[] options) {
    operatorManager.addReview(activityId, options, "private");
  }

  public void addExpertReview(String activityId, String[] options) {
    operatorManager.addReview(activityId, options, "expert");
  }

  public void displayReviews(String activityId) {
    operatorManager.showReviews(activityId);
  }

  public void endorseReview(String reviewId) {
    operatorManager.endorseReview(reviewId);
    // make sure this only works for public reviews
  }

  public void resolveReview(String reviewId, String response) {
    operatorManager.resolveReview(reviewId, response);
    // make sure this only works for private reviews
  }

  public void uploadReviewImage(String reviewId, String imageName) {
    operatorManager.addImage(reviewId, imageName);
    // make sure this only works for expert reviews
  }

  public void displayTopActivities() {
    operatorManager.displayTopActivities();
  }
}
