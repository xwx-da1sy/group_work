package model;

import java.util.HashSet;

/**
 * Stores one user's profile information and friend ID set.
 */
public class User {

    /** The username shown in the social network. */
    private String username;

    /** The password used when this user logs in. */
    private String password;

    /** The user's hometown. */
    private String homeTown;

    /** The user's workplace. */
    private String workPlace;

    /** The unique user ID inside one network. */
    private int userId;

    /** The IDs of this user's friends. */
    private HashSet<Integer> friends = new HashSet<Integer>();

    /**
     * Creates a user with profile information and a unique user ID.
     *
     * @param username  the username
     * @param password  the password
     * @param homeTown  the hometown
     * @param workPlace the workplace
     * @param userId    the unique user ID
     */
    public User(String username, String password, String homeTown, String workPlace, int userId) {
        this.username = username;
        this.password = password;
        this.homeTown = homeTown;
        this.workPlace = workPlace;
        this.userId = userId;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the hometown.
     *
     * @return the hometown
     */
    public String getHomeTown() {
        return homeTown;
    }

    /**
     * Sets the hometown.
     *
     * @param homeTown the new hometown
     */
    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    /**
     * Gets the workplace.
     *
     * @return the workplace
     */
    public String getWorkPlace() {
        return workPlace;
    }

    /**
     * Sets the workplace.
     *
     * @param workPlace the new workplace
     */
    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the new user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets this user's friend ID set.
     *
     * @return the friend ID set
     */
    public HashSet<Integer> getFriends() {
        return friends;
    }

    /**
     * Adds a friend ID to this user.
     *
     * @param userId the friend ID to add
     */
    public void addFriend(int userId) {
        friends.add(userId);
    }

    /**
     * Removes a friend ID from this user.
     *
     * @param userId the friend ID to remove
     */
    public void removeFriend(int userId) {
        friends.remove(userId);
    }
}
