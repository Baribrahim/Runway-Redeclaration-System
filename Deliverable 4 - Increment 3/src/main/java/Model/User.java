package Model;

public class User {

  private String userID;
  private String password;
  private String permission;

  public User(String userID, String password, String permission) {
    this.userID = userID;
    this.password = password;
    this.permission = permission;
  }

  public User(String userID, String permission) {
    this.userID = userID;
    this.permission = permission;
  }

  public String getUserID() {
    return this.userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String getPassword() {
    return this.userID;
  }

  public void setPassword(String password) {
    this.permission = password;
  }

  public String getPermission() {
    return this.permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

}
