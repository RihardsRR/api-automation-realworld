package com.testing.serenitySteps;

import cucumber.api.DataTable;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class RealworldSteps extends BaseSteps {
  private final static String _API_USERS_ = "/api/users/";
  private final static String _API_ARTICLES_ = "/api/articles/";
  // Find endpoint for API login
  private final static String _API_USERS_LOGIN_ = null;

  @Steps
  RealworldSteps realworldSteps;

  @Step
  public static void createAnAccount(DataTable dataTable) throws IOException {
    Map<String, Object> map = new HashMap<>(dataTable.asMap(String.class, String.class));

    if(map.get("user --> email").toString().equals(RANDOM_EMAIL)){
      String randomEmail = "test_" + new Random().nextInt(999999) + "@testdevlab.com";
      map.replace("user --> email", randomEmail);
      setSessionVariable(RANDOM_EMAIL).to(randomEmail);
    }

    if(map.get("user --> username").toString().equals(RANDOM_USERNAME)){
      String randomUsername = "test_" + new Random().nextInt(999999);
      map.replace("user --> username", randomUsername);
      setSessionVariable(RANDOM_USERNAME).to(randomUsername);
    }

    sendRequestWithBodyJson(POST, _API_USERS_, createBody(map));
    if (((Response) sessionVariableCalled(RESPONSE)).statusCode() == 200) {
      saveValueInPathToSessionVariable("user --> token", "token");
    }
  }

  @Step
  public static void logIntoAccount(DataTable dataTable) throws IOException {
    Map<String, Object> map = new HashMap<>(dataTable.asMap(String.class, String.class));
    setSessionVariable("token").to(null);

    if(map.get("user --> email").toString().equals(RANDOM_EMAIL)){
      map.replace("user --> email", sessionVariableCalled(RANDOM_EMAIL));
    }

    sendRequestWithBodyJson(POST, _API_USERS_LOGIN_, createBody(map));
    if (((Response) sessionVariableCalled(RESPONSE)).statusCode() == 200) {
      saveValueInPathToSessionVariable("user --> token", "token");
    }
  }


  // Private

  private static String addCommentEndpoint(String slug){
    return _API_ARTICLES_ + slug + "/comments";
  }

  public static void updateProfileSettings(DataTable dataTable) {
  }

  public static void createNewPost(DataTable dataTable) {
  }

  public static void deletePost() {
  }

  public static void modifyPost(DataTable dataTable) {
  }

  public static void addComment(DataTable dataTable) {
  }

  public static void deleteComment() {
  }
}
