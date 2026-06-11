package model;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Stores all users, the current user, and friendship relationships in one social network.
 */
public class Network {

    /** The maximum number of users allowed in one network. */
    private static final int MAX_USERS = 20;

    /** The unique ID of this social network. */
    private String networkId;

    /** The current number of users in the network. */
    private int totalUsers;

    /** The next available user ID candidate. */
    private int nextUserId;

    /** The user table where user ID is the key and User is the value. */
    private HashMap<Integer, User> userNetwork;

    /** The user currently using the program. */
    private User currentUser;

    /**
     * Creates an empty network with an automatically generated network ID.
     */
    public Network() {
        networkId = generateNetworkId();
        userNetwork = new HashMap<>(MAX_USERS);
        totalUsers = 0;
        nextUserId = 0;
        currentUser = null;
    }

    /**
     * Creates an empty network with a specified network ID.
     *
     * @param networkId the network ID to use
     */
    public Network(String networkId) {
        this();
        setNetworkId(networkId);
    }

    /**
     * Creates a network and sets the given user as the current user.
     *
     * @param currentUser the first current user
     */
    public Network(User currentUser) {
        this();
        addUser(currentUser);
        setCurrentUser(currentUser);
    }

    /**
     * Creates a network with one initial user.
     *
     * @param username  the initial user's username
     * @param password  the initial user's password
     * @param homeTown  the initial user's hometown
     * @param workPlace the initial user's workplace
     */
    public Network(String username, String password, String homeTown, String workPlace) {
        this();
        currentUser = createUser(username, password, homeTown, workPlace);
    }


    /**
     * Generates a network ID using the current date and the first available daily index.
     *
     * @return a new network ID
     */
    private static String generateNetworkId() {
        String dateText = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        int index = 0;

        while (true) {
            String possibleNetworkId = dateText + "-" + index;
            String filePath = NetworkFileManager.buildNetworkFilePath(possibleNetworkId);
            File networkFile = new File(filePath);

            if (!networkFile.exists()) {
                return possibleNetworkId;
            }

            index++;
        }
    }

    /**
     * Gets this network's ID.
     *
     * @return the network ID
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * Sets this network's ID.
     *
     * @param networkId the network ID to use
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    /**
     * Checks whether a user ID exists in the network.
     *
     * @param userId the user ID to check
     * @return true if the user ID exists, otherwise false
     */
    public boolean userIdExists(int userId) {
        if (!userNetwork.containsKey(userId)) {
            System.out.println("This user ID does not exist.");
            return false;
        }

        return true;
    }

    /**
     * Checks whether at least one user has the given username.
     *
     * @param username the username to check
     * @return true if at least one user exists, otherwise false
     */
    public boolean userExists(String username) {
        if (getUsersByUsername(username).isEmpty()) {
            System.out.println("This user does not exist.");
            return false;
        }

        return true;
    }


    /**
     * Sets the current user by User object.
     *
     * @param user the user to set as current user
     */
    public void setCurrentUser(User user) {
        if (user == null || !userIdExists(user.getUserId())) {
            return;
        }

        currentUser = userNetwork.get(user.getUserId());
    }

    /**
     * Sets the current user by user ID.
     *
     * @param userId the user ID to set as current user
     */
    public void setCurrentUserById(int userId) {
        if (!userIdExists(userId)) {
            return;
        }

        currentUser = userNetwork.get(userId);
    }

    /**
     * Gets the current user.
     *
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Clears the current user.
     */
    public void clearCurrentUser() {
        currentUser = null;
    }


    /**
     * Adds an existing user object to the network.
     *
     * @param user the user to add
     */
    public void addUser(User user) {
        if (user == null) {
            return;
        }

        if (userNetwork.size() >= MAX_USERS) {
            System.out.println("The network is full.");
            return;
        }

        if (user.getUserId() < 0 || user.getUserId() >= MAX_USERS) {
            System.out.println("Invalid user ID.");
            return;
        }

        if (userNetwork.containsKey(user.getUserId())) {
            System.out.println("This user ID already exists.");
            return;
        }

        userNetwork.put(user.getUserId(), user);

        totalUsers = userNetwork.size();
        nextUserId = findAvailableUserId();

        if (currentUser == null && user.getUserId() == 0) {
            currentUser = user;
        }
    }

    /**
     * Creates a new user and adds it to the network.
     *
     * @param username  the username
     * @param password  the password
     * @param homeTown  the hometown
     * @param workPlace the workplace
     * @return the created user, or null if the network is full
     */
    public User createUser(String username, String password, String homeTown, String workPlace) {
        int userId = findAvailableUserId();
        if (userId == -1) {
            System.out.println("The network is full.");
            return null;
        }

        User user = new User(username, password, homeTown, workPlace, userId);
        addUser(user);
        return user;
    }

    /**
     * Finds the smallest available user ID.
     *
     * @return the smallest available user ID, or -1 if the network is full
     */
    private int findAvailableUserId() {
        for (int id = 0; id < MAX_USERS; id++) {
            if (!userNetwork.containsKey(id)) {
                return id;
            }
        }
        return -1;
    }

    /**
     * Removes a user from the network and clears that user from all friend lists.
     *
     * @param userId the user ID to remove
     * @return true if the user is removed, otherwise false
     */
    public boolean removeUser(int userId) {
        if (!userIdExists(userId)) {
            return false;
        }

        if (currentUser != null && currentUser.getUserId() == userId) {
            System.out.println("You cannot remove current user.");
            return false;
        }

        for (User user : userNetwork.values()) {
            user.removeFriend(userId);
        }

        userNetwork.remove(userId);

        totalUsers = userNetwork.size();

        return true;
    }



    /**
     * Checks login information and sets the current user if it is correct.
     *
     * @param userId   the login user ID
     * @param password the login password
     * @return true if login succeeds, otherwise false
     */
    public boolean checkLogin(int userId, String password) {
        if (!checkPassword(userId, password)) {
            return false;
        }

        currentUser = userNetwork.get(userId);
        return true;
    }

    /**
     * Checks whether a password matches a user ID.
     *
     * @param userId   the user ID to check
     * @param password the password to check
     * @return true if the password is correct, otherwise false
     */
    public boolean checkPassword(int userId, String password) {
        if (!userIdExists(userId)) {
            return false;
        }

        if (userNetwork.get(userId).getPassword().equals(password)) {
            return true;
        }

        System.out.println("Incorrect password.");
        return false;
    }



    /**
     * Gets a user by user ID.
     *
     * @param userId the user ID to find
     * @return the matching user, or null if no user exists
     */
    public User getUserById(int userId) {
        if (!userIdExists(userId)) {
            return null;
        }

        return userNetwork.get(userId);
    }

    /**
     * Gets a user by username when the username is unique.
     *
     * @param username the username to find
     * @return the matching user, or null if no unique match exists
     */
    public User getUser(String username) {
        HashSet<User> users = getUsersByUsername(username);

        if (users.isEmpty()) {
            System.out.println("This user does not exist.");
            return null;
        }

        if (users.size() > 1) {
            System.out.println("More than one user has this name. Please use user ID.");
            return null;
        }

        return users.iterator().next();
    }

    /**
     * Gets all users with the same username.
     *
     * @param username the username to find
     * @return users with the given username
     */
    public HashSet<User> getUsersByUsername(String username) {
        HashSet<User> users = new HashSet<>();

        for (User user : userNetwork.values()) {
            if (user.getUsername().equals(username)) {
                users.add(user);
            }
        }

        return users;
    }

    /**
     * Gets all user IDs with the same username.
     *
     * @param username the username to find
     * @return user IDs with the given username
     */
    public HashSet<Integer> getUserIdsByUsername(String username) {
        HashSet<Integer> userIds = new HashSet<>();

        for (User user : getUsersByUsername(username)) {
            userIds.add(user.getUserId());
        }

        return userIds;
    }

    /**
     * Gets a username by user ID.
     *
     * @param userId the user ID to find
     * @return the username, or null if no user exists
     */
    public String getUsernameById(int userId) {
        User user = getUserById(userId);
        if (user == null) {
            return null;
        }

        return user.getUsername();
    }

    /**
     * Gets the total number of users.
     *
     * @return the total number of users
     */
    public int getTotalUsers() {
        return totalUsers;
    }

    /**
     * Gets all users in this network.
     *
     * @return all users
     */
    public Collection<User> getAllUsers() {
        return new HashSet<>(userNetwork.values());
    }

    /**
     * Gets the friend ID set for a user.
     *
     * @param userId the user ID whose friends should be returned
     * @return a copy of the user's friend ID set, or null if the user does not exist
     */
    public HashSet<Integer> getFriendsList(int userId) {
        if (!userIdExists(userId)) {
            return null;
        }

        return new HashSet<>(userNetwork.get(userId).getFriends());
    }

    /**
     * Gets a user's friend ID set by username when the username is unique.
     *
     * @param username the username to find
     * @return a copy of the user's friend ID set, or null if no unique match exists
     */
    public HashSet<Integer> getFriendsList(String username) {
        User user = getUser(username);
        if (user == null) {
            return null;
        }

        return getFriendsList(user.getUserId());
    }

    /**
     * Gets the current user's friend ID set.
     *
     * @return the current user's friend ID set
     */
    public HashSet<Integer> getCurrentUserFriends() {
        if (currentUser == null) {
            System.out.println("No current user.");
            return new HashSet<>();
        }

        return getFriendsList(currentUser.getUserId());
    }



    /**
     * Checks whether a user is a friend of the current user.
     *
     * @param userId the user ID to check
     * @return true if the user is a friend, otherwise false
     */
    public boolean isFriend(int userId) {
        if (!userIdExists(userId)) {
            return false;
        }

        if (currentUser == null) {
            System.out.println("No current user.");
            return false;
        }

        if (currentUser.getFriends().contains(userId)) {
            return true;
        }

        System.out.println("This user is not your friend.");
        return false;
    }

    /**
     * Checks whether a username belongs to a friend of the current user.
     *
     * @param username the username to check
     * @return true if the unique matching user is a friend, otherwise false
     */
    public boolean isFriend(String username) {
        User user = getUser(username);
        if (user == null) {
            return false;
        }

        return isFriend(user.getUserId());
    }

    /**
     * Gets a friend object by user ID.
     *
     * @param userId the friend ID to view
     * @return the friend object, or null if the user is not a friend
     */
    public User viewFriend(int userId) {
        if (isFriend(userId)) {
            return userNetwork.get(userId);
        }

        return null;
    }

    /**
     * Gets a friend object by username when the username is unique.
     *
     * @param username the friend's username
     * @return the friend object, or null if no unique friend exists
     */
    public User viewFriend(String username) {
        User user = getUser(username);
        if (user == null) {
            return null;
        }

        return viewFriend(user.getUserId());
    }

    /**
     * Adds a two-way friendship between two users.
     *
     * @param userId1 the first user ID
     * @param userId2 the second user ID
     */
    public void addEachOther(int userId1, int userId2) {
        if (!userIdExists(userId1) || !userIdExists(userId2)) {
            return;
        }
        if (userId1 == userId2) {
            System.out.println("You cannot add yourself as a friend.");
            return;
        }
        User user1 = userNetwork.get(userId1);
        User user2 = userNetwork.get(userId2);

        boolean user1HasUser2 = user1.getFriends().contains(userId2);
        boolean user2HasUser1 = user2.getFriends().contains(userId1);

        if (user1HasUser2 && user2HasUser1) {
            System.out.println("You are already friends.");
            return;
        }

        if (!user1HasUser2) {
            user1.addFriend(userId2);
        }

        if (!user2HasUser1) {
            user2.addFriend(userId1);
        }
    }

    /**
     * Adds a two-way friendship by username when both usernames are unique.
     *
     * @param username1 the first username
     * @param username2 the second username
     * @param password1 kept for earlier method compatibility
     * @param password2 kept for earlier method compatibility
     */
    public void addEachOther(String username1, String username2, String password1, String password2) {
        User user1 = getUser(username1);
        User user2 = getUser(username2);

        if (user1 == null || user2 == null) {
            return;
        }

        addEachOther(user1.getUserId(), user2.getUserId());
    }

    /**
     * Removes a two-way friendship between two users.
     *
     * @param userId1 the first user ID
     * @param userId2 the second user ID
     */
    public void removeEachOther(int userId1, int userId2) {
        if (!userIdExists(userId1) || !userIdExists(userId2)) {
            return;
        }

        if (userId1 == userId2) {
            System.out.println("You cannot remove yourself as a friend.");
            return;
        }
        User user1 = userNetwork.get(userId1);
        User user2 = userNetwork.get(userId2);

        boolean user1HasUser2 = user1.getFriends().contains(userId2);
        boolean user2HasUser1 = user2.getFriends().contains(userId1);

        if (!user1HasUser2 && !user2HasUser1) {
            System.out.println("These users are not friends.");
            return;
        }

        if (user1HasUser2) {
            user1.removeFriend(userId2);
        }

        if (user2HasUser1) {
            user2.removeFriend(userId1);
        }
    }

    /**
     * Removes a two-way friendship by username when both usernames are unique.
     *
     * @param username1 the first username
     * @param username2 the second username
     */
    public void removeEachOther(String username1, String username2) {
        User user1 = getUser(username1);
        User user2 = getUser(username2);

        if (user1 == null || user2 == null) {
            return;
        }

        removeEachOther(user1.getUserId(), user2.getUserId());
    }

    /**
     * Removes a user from the current user's friend list only.
     *
     * @param userId the friend ID to remove
     */
    public void removeFriend(int userId) {
        if (!userIdExists(userId)) {
            return;
        }

        if (currentUser == null) {
            System.out.println("No current user.");
            return;
        }

        if (!currentUser.getFriends().contains(userId)) {
            System.out.println("This user is not your friend.");
            return;
        }

        currentUser.removeFriend(userId);
    }

    /**
     * Removes a user from the current user's friend list by username.
     *
     * @param username the friend's username
     */
    public void removeFriend(String username) {
        User user = getUser(username);
        if (user == null) {
            return;
        }

        removeFriend(user.getUserId());
    }


    /**
     * Filters the current user's friends by workplace.
     *
     * @param workPlace the workplace to match
     * @return matching friend IDs
     */
    public HashSet<Integer> filterFriendsByWorkPlace(String workPlace) {
        HashSet<Integer> filteredFriends = new HashSet<>();

        if (currentUser == null) {
            System.out.println("No current user.");
            return filteredFriends;
        }

        for (int friendId : currentUser.getFriends()) {
            User friend = userNetwork.get(friendId);
            if (friend != null && friend.getWorkPlace().equals(workPlace)) {
                filteredFriends.add(friendId);
            }
        }
        return filteredFriends;
    }

    /**
     * Filters the current user's friends by hometown.
     *
     * @param homeTown the hometown to match
     * @return matching friend IDs
     */
    public HashSet<Integer> filterFriendsByHomeTown(String homeTown) {
        HashSet<Integer> filteredFriends = new HashSet<>();

        if (currentUser == null) {
            System.out.println("No current user.");
            return filteredFriends;
        }

        for (int friendId : currentUser.getFriends()) {
            User friend = userNetwork.get(friendId);
            if (friend != null && friend.getHomeTown().equals(homeTown)) {
                filteredFriends.add(friendId);
            }
        }
        return filteredFriends;
    }

    /**
     * Filters current user's friends with the same workplace as the current user.
     *
     * @return matching friend IDs
     */
    public HashSet<Integer> filterFriendsBySameWorkPlace() {
        if (currentUser == null) {
            System.out.println("No current user.");
            return new HashSet<>();
        }

        return filterFriendsByWorkPlace(currentUser.getWorkPlace());
    }

    /**
     * Filters current user's friends with the same hometown as the current user.
     *
     * @return matching friend IDs
     */
    public HashSet<Integer> filterFriendsBySameHomeTown() {
        if (currentUser == null) {
            System.out.println("No current user.");
            return new HashSet<>();
        }

        return filterFriendsByHomeTown(currentUser.getHomeTown());
    }

    /**
     * Finds friends of friends whose workplace matches the given workplace.
     *
     * @param userId    the user ID whose friend network is searched
     * @param workPlace the workplace to match
     * @return matching user IDs from friends of friends
     */
    public HashSet<Integer> filterFriendsOfFriendsByWorkPlace(int userId, String workPlace) {
        HashSet<Integer> filteredFriendsOfFriends = new HashSet<>();

        if (!userIdExists(userId)) {
            return filteredFriendsOfFriends;
        }

        HashSet<Integer> directFriends = getFriendsList(userId);

        for (int friendId : directFriends) {
            HashSet<Integer> friendsOfFriend = getFriendsList(friendId);

            for (int candidateId : friendsOfFriend) {
                User candidate = getUserById(candidateId);

                if (candidateId != userId
                        && !directFriends.contains(candidateId)
                        && candidate != null
                        && candidate.getWorkPlace().equals(workPlace)) {
                    filteredFriendsOfFriends.add(candidateId);
                }
            }
        }

        return filteredFriendsOfFriends;
    }

    /**
     * Finds friends of friends who share the current user's workplace.
     *
     * @return matching user IDs from friends of friends
     */
    public HashSet<Integer> filterFriendsOfFriendsBySameWorkPlace() {
        if (currentUser == null) {
            System.out.println("No current user.");
            return new HashSet<>();
        }

        return filterFriendsOfFriendsByWorkPlace(currentUser.getUserId(), currentUser.getWorkPlace());
    }

    /**
     * Finds friends of friends whose hometown matches the given hometown.
     *
     * @param userId   the user ID whose friend network is searched
     * @param homeTown the hometown to match
     * @return matching user IDs from friends of friends
     */
    public HashSet<Integer> filterFriendsOfFriendsByHomeTown(int userId, String homeTown) {
        HashSet<Integer> filteredFriendsOfFriends = new HashSet<>();

        if (!userIdExists(userId)) {
            return filteredFriendsOfFriends;
        }

        HashSet<Integer> directFriends = getFriendsList(userId);

        for (int friendId : directFriends) {
            HashSet<Integer> friendsOfFriend = getFriendsList(friendId);

            for (int candidateId : friendsOfFriend) {
                User candidate = getUserById(candidateId);

                if (candidateId != userId
                        && !directFriends.contains(candidateId)
                        && candidate != null
                        && candidate.getHomeTown().equals(homeTown)) {
                    filteredFriendsOfFriends.add(candidateId);
                }
            }
        }

        return filteredFriendsOfFriends;
    }

    /**
     * Finds friends of friends who share the current user's hometown.
     *
     * @return matching user IDs from friends of friends
     */
    public HashSet<Integer> filterFriendsOfFriendsBySameHomeTown() {
        if (currentUser == null) {
            System.out.println("No current user.");
            return new HashSet<>();
        }

        return filterFriendsOfFriendsByHomeTown(currentUser.getUserId(), currentUser.getHomeTown());
    }

    /**
     * Checks whether a user ID exists and prints an error message when it does not.
     *
     * @param userId the user ID to check
     * @return true if the user ID exists, otherwise false
     */
    public boolean checkUserIdExists(int userId) {
        if (!userIdExists(userId)) {
            System.out.println("This user ID does not exist.");
            return false;
        }

        return true;
    }
}
