package model;

import java.util.HashMap;

public class Network {

    private static final int MAX_USERS = 20;

    private int totalUsers;
    private int nextUserId;
    private HashMap<String, User> userNetwork;
    private User currentUser;

    public Network() {
        userNetwork = new HashMap<>(MAX_USERS);
        totalUsers = 0;
        nextUserId = 0;
        currentUser = null;
    }

    public Network(User currentUser) {
        this();
        addUser(currentUser);
        this.currentUser = currentUser;
    }

    public boolean userExists(String username) {
        if (!userNetwork.containsKey(username)) {
            System.out.println("This user does not exist.");
            return false;
        }

        return true;
    }

    public void clearCurrentUser() {
        currentUser = null;
    }

    public void addUser(User user) {
        if (userNetwork.size() >= MAX_USERS) {
            System.out.println("The network is full.");
            return;
        }

        if (userNetwork.containsKey(user.getUsername())) {
            System.out.println("This user already exists.");
            return;
        }

        userNetwork.put(user.getUsername(), user);
        totalUsers = userNetwork.size();
        nextUserId = Math.max(nextUserId, user.getUserId() + 1);

        if (currentUser == null && user.getUserId() == 0) {
            currentUser = user;
        }
    }

    public User createUser(String username, String password, String homeTown, String workPlace) {
        if (nextUserId >= MAX_USERS) {
            System.out.println("The network is full.");
            return null;
        }

        User user = new User(username, password, homeTown, workPlace, nextUserId);
        addUser(user);
        return user;
    }
}
