package gymmembershipsystem.singleton;

public class UserSession {

  public static int userId;
  public static int role;

  public static void setUserId(int id) {
    userId = id;
  }

  public static void setRole(int role) {
    UserSession.role = role;
  }

  public static int getRole() {
    return role;
  }

  public static int getUserId() {
    return userId;
  }

  public static void destroy() {
    userId = 0;
    role = 0;
  }

}
