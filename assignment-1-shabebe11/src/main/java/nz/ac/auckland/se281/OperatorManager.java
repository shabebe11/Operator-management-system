package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Types.ActivityType;
import nz.ac.auckland.se281.Types.Location;

public class OperatorManager {

  List<Operator> operators = new ArrayList<>();

  public OperatorManager() {
    operators = new ArrayList<>();
  }

  public List<Operator> getOperators() {
    return operators;
  }

  public void addOperator(String name, String location) {

    name = name.trim();
    location = location.trim();

    if (name.length() < 3) {
      MessageCli.OPERATOR_NOT_CREATED_INVALID_OPERATOR_NAME.printMessage(name);
      return;
    }

    if (Types.Location.fromString(location) == null) {
      MessageCli.OPERATOR_NOT_CREATED_INVALID_LOCATION.printMessage(location);
      return;
    }

    int count = 1;

    for (Operator operator : operators) {
      if (operator.getName().toLowerCase().equals(name.toLowerCase())
          && operator
              .getCity()
              .equals(Types.Location.fromString(location).getLocationAbbreviation())) {
        MessageCli.OPERATOR_NOT_CREATED_ALREADY_EXISTS_SAME_LOCATION.printMessage(
            name, Types.Location.fromString(location).getFullName());
        return;
      }
      if (operator
          .getCity()
          .equals(Types.Location.fromString(location).getLocationAbbreviation())) {
        count++;
      }
    }

    String fullName = Types.Location.fromString(location).getFullName();
    String locationAbbreviation = Types.Location.fromString(location).getLocationAbbreviation();
    String nameAbbreviation = getNameLetters(name);
    String code =
        nameAbbreviation + "-" + locationAbbreviation + "-" + String.format("%03d", count);
    String searchString =
        name
            + Types.Location.fromString(location).getNameEnglish()
            + Types.Location.fromString(location).getNameTeReo()
            + locationAbbreviation
            + nameAbbreviation;
    Operator newOperator =
        new Operator(name, locationAbbreviation, code, searchString.toLowerCase());
    operators.add(newOperator);

    MessageCli.OPERATOR_CREATED.printMessage(name, code, fullName);
  }

  public void printOperators(String keyword) {
    int searchResults = 0;
    keyword = keyword.trim().toLowerCase();

    if (keyword.equals("*")) {
      searchResults = operators.size();
    } else {
      for (Operator operator : operators) {
        if (operator.getSearch().contains(keyword)) {
          searchResults++;
        }
      }
    }

    if (searchResults == 0) {
      MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");

    } else if (searchResults == 1) {
      MessageCli.OPERATORS_FOUND.printMessage("is", String.valueOf(searchResults), "", ":");

      if (keyword.equals("*")) {
        for (Operator operator : operators) {
          MessageCli.OPERATOR_ENTRY.printMessage(
              operator.getName(),
              operator.getCode(),
              Types.Location.fromString(operator.getCity()).getFullName());
        }
      } else {
        for (Operator operator : operators) {
          if (operator.getSearch().contains(keyword))
            MessageCli.OPERATOR_ENTRY.printMessage(
                operator.getName(),
                operator.getCode(),
                Types.Location.fromString(operator.getCity()).getFullName());
        }
      }
    } else if (searchResults >= 2) {
      MessageCli.OPERATORS_FOUND.printMessage("are", String.valueOf(searchResults), "s", ":");

      if (keyword.equals("*")) {
        for (Operator operator : operators) {
          MessageCli.OPERATOR_ENTRY.printMessage(
              operator.getName(),
              operator.getCode(),
              Types.Location.fromString(operator.getCity()).getFullName());
        }
      } else {
        for (Operator operator : operators) {
          if (operator.getSearch().contains(keyword))
            MessageCli.OPERATOR_ENTRY.printMessage(
                operator.getName(),
                operator.getCode(),
                Types.Location.fromString(operator.getCity()).getFullName());
        }
      }
    }
  }

  public String getNameLetters(String name) {
    String[] individual = name.split(" ");
    String firstLetters = "";

    for (String word : individual) {
      if (!word.isEmpty()) {
        firstLetters += (word.charAt(0));
      }
    }
    return firstLetters.toUpperCase();
  }

  public void viewActivities(String operatorId) {
    for (Operator operator : operators) {
      if (operator.getCode().equals(operatorId)) {
        if (operator.countActivities() == 0) {
          MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
          return;
        }

        if (operator.countActivities() == 1) {
          MessageCli.ACTIVITIES_FOUND.printMessage("is", "1", "y", ":");
          operator.searchActivities("*");
          ;
          return;
        }

        if (operator.countActivities() >= 2) {
          int number = operator.countActivities();
          MessageCli.ACTIVITIES_FOUND.printMessage("are", String.valueOf(number), "ies", ":");
          operator.searchActivities("*");
          return;
        }
      }
    }
    MessageCli.OPERATOR_NOT_FOUND.printMessage(operatorId);
  }

  public void createActivity(String Name, String type, String code) {
    if (Name.trim().length() < 3) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_ACTIVITY_NAME.printMessage(Name);
      return;
    }

    for (Operator operator : operators) {
      if (operator.getCode().equals(code)) {
        ActivityType actualType = Types.ActivityType.fromString(type);
        Activity newActivity = new Activity(Name, actualType.toString());
        operator.addActivity(newActivity);
        MessageCli.ACTIVITY_CREATED.printMessage(
            Name, operator.getActivityCode(Name), actualType.toString(), operator.getName());
        newActivity.setCode(operator.getActivityCode(Name));
        return;
      }
    }
    MessageCli.ACTIVITY_NOT_CREATED_INVALID_OPERATOR_ID.printMessage(code);
  }

  public void searchActivities(String keyword) {
    int count = 0;
    for (Operator operator : operators) {
      count += operator.activityCount(keyword);
      if (operator.getLocationSearch().contains(keyword.toLowerCase())) {
        count += operator.activityCount("*");
      }
    }

    if (count == 0) {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
    } else if (count == 1) {
      MessageCli.ACTIVITIES_FOUND.printMessage("is", "1", "y", ":");
    } else {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", String.valueOf(count), "ies", ":");
    }

    for (Operator operator : operators) {
      operator.searchActivities(keyword);
      if (operator.getLocationSearch().contains(keyword)) {
        operator.searchActivities("*");
      }
    }
  }

  public void showReviews(String keyword) {
    int index = keyword.lastIndexOf("-");
    String key = keyword.substring(0, index);
    for (Operator operator : operators) {
      if (operator.getCode().equals(key)) {
        operator.showReviews(keyword);
      }
    }
  }

  public void addReview(String keyword, String[] options, String type) {
    int index = keyword.lastIndexOf("-");
    String key = keyword.substring(0, index);
    for (Operator operator : operators) {
      if (operator.getCode().equals(key)) {
        operator.addReview(keyword, options, type);
        return;
      }
    }
    MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(keyword);
  }

  public void endorseReview(String keyword) {
    int index0 = keyword.lastIndexOf("-");
    int index = keyword.lastIndexOf("-", index0 - 1);
    String key = keyword.substring(0, index);
    for (Operator operator : operators) {
      if (operator.getCode().equals(key)) {
        operator.endorseReview(keyword);
        return;
      }
    }
  }

  public void resolveReview(String keyword, String message) {
    int index0 = keyword.lastIndexOf("-");
    int index = keyword.lastIndexOf("-", index0 - 1);
    String key = keyword.substring(0, index);
    for (Operator operator : operators) {
      if (operator.getCode().equals(key)) {
        operator.resolveReview(keyword, message);
        return;
      }
    }
  }

  public void addImage(String keyword, String image) {
    int index0 = keyword.lastIndexOf("-");
    int index = keyword.lastIndexOf("-", index0 - 1);
    String key = keyword.substring(0, index);
    for (Operator operator : operators) {
      if (operator.getCode().equals(key)) {
        operator.addImage(keyword, image);
        return;
      }
    }
  }

  public void displayTopActivities() {
    for (Location location : Location.values()) {
      Activity topActivity = null;
      int highestRating = -1;
      for (Operator operator : operators) {
        if (operator.getCity().equalsIgnoreCase(location.toString())) {
          Activity highestRated = operator.highestRated();
          if (topActivity == null || highestRated.getRating() > highestRating) {
            topActivity = highestRated;
            highestRating = topActivity.getRating();
          }
        }
      }
      if (topActivity != null) {
        MessageCli.TOP_ACTIVITY.printMessage(
            location.toString(), topActivity.getName(), String.valueOf(topActivity.getRating()));

      } else {
        MessageCli.NO_REVIEWED_ACTIVITIES.printMessage(location.toString());
      }
    }
  }
}
