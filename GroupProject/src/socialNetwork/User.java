package socialNetwork;

import java.util.HashSet;

public class User {

    private String username;
    private String password;
    private String homeTown;
    private String workPlace;
    private int userId;

    private HashSet<String> friends = new HashSet<String>();

    public User(String username, String password, String homeTown, String workPlace, int userId) {
        this.username = username;
        this.password = password;
        this.homeTown = homeTown;
        this.workPlace = workPlace;
        this.userId = userId;
    }

    // getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public HashSet<String> getFriends() {
        return friends;
    }
//
//    public void setFriends(HashSet<String> friends) {
//        this.friends = friends;
//    }

    // methods about one user's friends
    public void addFriend(String username) {
        friends.add(username);
    }

    public void removeFriend(String username) {
        friends.remove(username);
    }
}
