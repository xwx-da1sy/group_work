package controller;

import model.Network;
import model.NetworkFileManager;
import model.User;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Controls the main social network window after a user has logged in.
 */
public class MainController {

    /** The social network currently shown in the main window. */
    private Network network;

    /** The file manager used to save the current network. */
    private NetworkFileManager fileManager;

    /**
     * Creates a main controller for an already loaded network.
     *
     * @param network the network to manage
     */
    public MainController(Network network) {
        this.network = network;
        fileManager = new NetworkFileManager();
    }

    /**
     * Gets the network managed by this controller.
     *
     * @return the current network
     */
    public Network getNetwork() {
        return network;
    }

    /**
     * Gets the current network ID.
     *
     * @return the network ID
     */
    public String getNetworkId() {
        return network.getNetworkId();
    }

    /**
     * Gets the current user's ID.
     *
     * @return the current user ID
     */
    public int getCurrentUserId() {
        return network.getCurrentUser().getUserId();
    }

    /**
     * Gets the current user's username.
     *
     * @return the current username
     */
    public String getCurrentUsername() {
        return network.getCurrentUser().getUsername();
    }

    /**
     * Gets the number of friends owned by the current user.
     *
     * @return the current user's friend count
     */
    public int getCurrentUserFriendsCount() {
        return network.getCurrentUserFriends().size();
    }

    /**
     * Gets all users in the network as a sorted list.
     *
     * @return all users sorted by user ID
     */
    public ArrayList<User> getAllUsersList() {
        ArrayList<User> users = new ArrayList<>(network.getAllUsers());

        sortUsersById(users);

        return users;
    }

    /**
     * Finds a user by user ID.
     *
     * @param userId the user ID to find
     * @return the matching user, or null if no user exists
     */
    public User getUserById(int userId) {
        return network.getUserById(userId);
    }

    /**
     * Gets one user's friends as a sorted list of User objects.
     *
     * @param userId the owner of the friend list
     * @return the user's friends sorted by user ID
     */
    public ArrayList<User> getUserFriendsList(int userId) {
        ArrayList<User> friendsList = new ArrayList<>();

        HashSet<Integer> friendIds = network.getFriendsList(userId);
        if (friendIds == null) {
            return friendsList;
        }

        for (int friendId : friendIds) {
            User friend = network.getUserById(friendId);

            if (friend != null) {
                friendsList.add(friend);
            }
        }

        sortUsersById(friendsList);

        return friendsList;
    }

    /**
     * Adds the target user as a friend of the current user.
     *
     * @param targetUserId the target user's ID
     * @return true if the friend relationship is created, otherwise false
     */
    public boolean addFriendToCurrentUser(int targetUserId) {
        int currentUserId = network.getCurrentUser().getUserId();

        if (currentUserId == targetUserId) {
            return false;
        }

        if (network.getUserById(targetUserId) == null) {
            return false;
        }

        if (network.getCurrentUserFriends().contains(targetUserId)) {
            return false;
        }

        network.addEachOther(currentUserId, targetUserId);
        return true;
    }

    /**
     * Removes the target user from the current user's friend list.
     *
     * @param targetUserId the target user's ID
     * @return true if the friend relationship is removed, otherwise false
     */
    public boolean removeFriendFromCurrentUser(int targetUserId) {
        int currentUserId = network.getCurrentUser().getUserId();

        if (currentUserId == targetUserId) {
            return false;
        }

        if (network.getUserById(targetUserId) == null) {
            return false;
        }

        if (!network.getCurrentUserFriends().contains(targetUserId)) {
            return false;
        }

        network.removeEachOther(currentUserId, targetUserId);
        return true;
    }

    /**
     * Creates a new user in the current network.
     *
     * @param username  the new username
     * @param password  the new password
     * @param homeTown  the new user's hometown
     * @param workPlace the new user's workplace
     * @return the created user, or null if the input is invalid
     */
    public User createNewUser(String username, String password, String homeTown, String workPlace) {
        if (username == null || password == null || homeTown == null || workPlace == null) {
            return null;
        }

        if (username.isEmpty() || password.isEmpty() || homeTown.isEmpty() || workPlace.isEmpty()) {
            return null;
        }

        return network.createUser(username, password, homeTown, workPlace);
    }

    /**
     * Removes a user from the current network.
     *
     * @param targetUserId the user ID to remove
     * @return true if the user is removed, otherwise false
     */
    public boolean removeUserFromNetwork(int targetUserId) {
        int currentUserId = network.getCurrentUser().getUserId();

        if (currentUserId == targetUserId) {
            return false;
        }

        if (network.getUserById(targetUserId) == null) {
            return false;
        }

        return network.removeUser(targetUserId);
    }

    /**
     * Saves the current network to its data file.
     *
     * @return true if the network is saved successfully, otherwise false
     */
    public boolean saveCurrentNetwork() {
        return fileManager.saveNetwork(network);
    }

    /**
     * Filters all users by ID, name, workplace, or hometown.
     *
     * @param keyword    the search keyword
     * @param filterType the selected filter type
     * @return the filtered users sorted by user ID
     */
    public ArrayList<User> filterUsers(String keyword, String filterType) {
        ArrayList<User> filteredUsers = new ArrayList<>();

        if (keyword == null || filterType == null) {
            return getAllUsersList();
        }

        keyword = keyword.trim();
        if (keyword.isEmpty()) {
            return getAllUsersList();
        }

        if (filterType.equals("ID")) {
            try {
                int userId = Integer.parseInt(keyword);
                User user = network.getUserById(userId);

                if (user != null) {
                    filteredUsers.add(user);
                }
            } catch (NumberFormatException exception) {
                return filteredUsers;
            }
        }

        if (filterType.equals("Name")) {
            String keywordForCheck = keyword.toLowerCase();

            for (User user : getAllUsersList()) {
                String username = user.getUsername().toLowerCase();

                if (username.contains(keywordForCheck)) {
                    filteredUsers.add(user);
                }
            }
        }

        if (filterType.equals("Workplace")) {
            String keywordForCheck = keyword.toLowerCase();

            for (User user : getAllUsersList()) {
                String workPlace = user.getWorkPlace().toLowerCase();

                if (workPlace.contains(keywordForCheck)) {
                    filteredUsers.add(user);
                }
            }
        }

        if (filterType.equals("Hometown")) {
            String keywordForCheck = keyword.toLowerCase();

            for (User user : getAllUsersList()) {
                String homeTown = user.getHomeTown().toLowerCase();

                if (homeTown.contains(keywordForCheck)) {
                    filteredUsers.add(user);
                }
            }
        }

        sortUsersById(filteredUsers);

        return filteredUsers;
    }

    /**
     * Filters the current user's friends by ID, name, workplace, or hometown.
     *
     * @param keyword    the search keyword
     * @param filterType the selected filter type
     * @return the filtered friends sorted by user ID
     */
    public ArrayList<User> filterCurrentUserFriends(String keyword, String filterType) {
        ArrayList<User> filteredFriends = new ArrayList<>();

        if (keyword == null || filterType == null) {
            return getUserFriendsList(getCurrentUserId());
        }

        keyword = keyword.trim();
        if (keyword.isEmpty()) {
            return getUserFriendsList(getCurrentUserId());
        }

        ArrayList<User> friendsList = getUserFriendsList(getCurrentUserId());

        if (filterType.equals("ID")) {
            try {
                int userId = Integer.parseInt(keyword);

                for (User friend : friendsList) {
                    if (friend.getUserId() == userId) {
                        filteredFriends.add(friend);
                    }
                }
            } catch (NumberFormatException exception) {
                return filteredFriends;
            }
        }

        if (filterType.equals("Name")) {
            String keywordForCheck = keyword.toLowerCase();

            for (User friend : friendsList) {
                String username = friend.getUsername().toLowerCase();

                if (username.contains(keywordForCheck)) {
                    filteredFriends.add(friend);
                }
            }
        }

        if (filterType.equals("Workplace")) {
            String keywordForCheck = keyword.toLowerCase();

            for (User friend : friendsList) {
                String workPlace = friend.getWorkPlace().toLowerCase();

                if (workPlace.contains(keywordForCheck)) {
                    filteredFriends.add(friend);
                }
            }
        }

        if (filterType.equals("Hometown")) {
            String keywordForCheck = keyword.toLowerCase();

            for (User friend : friendsList) {
                String homeTown = friend.getHomeTown().toLowerCase();

                if (homeTown.contains(keywordForCheck)) {
                    filteredFriends.add(friend);
                }
            }
        }

        sortUsersById(filteredFriends);

        return filteredFriends;
    }

    /**
     * Gets common friends between the current user and a target user.
     *
     * @param targetUserId the target user's ID
     * @return common friends sorted by user ID
     */
    public ArrayList<User> getCommonFriendsWithCurrentUser(int targetUserId) {
        ArrayList<User> commonFriends = new ArrayList<>();

        int currentUserId = getCurrentUserId();

        if (currentUserId == targetUserId) {
            return commonFriends;
        }

        if (network.getUserById(targetUserId) == null) {
            return commonFriends;
        }

        HashSet<Integer> currentUserFriends = network.getFriendsList(currentUserId);
        HashSet<Integer> targetUserFriends = network.getFriendsList(targetUserId);

        if (currentUserFriends == null || targetUserFriends == null) {
            return commonFriends;
        }

        for (int friendId : currentUserFriends) {
            if (targetUserFriends.contains(friendId)) {
                User commonFriend = network.getUserById(friendId);

                if (commonFriend != null) {
                    commonFriends.add(commonFriend);
                }
            }
        }

        sortUsersById(commonFriends);

        return commonFriends;
    }

    /**
     * Gets recommended friends for the current user from friends of friends.
     *
     * @return recommended friends sorted by user ID
     */
    public ArrayList<User> getFriendRecommendationsForCurrentUser() {
        ArrayList<User> recommendedFriends = new ArrayList<>();
        HashSet<Integer> recommendedFriendIds = new HashSet<>();

        HashSet<Integer> sameWorkPlaceFriends = network.filterFriendsOfFriendsBySameWorkPlace();
        HashSet<Integer> sameHomeTownFriends = network.filterFriendsOfFriendsBySameHomeTown();

        for (int userId : sameWorkPlaceFriends) {
            recommendedFriendIds.add(userId);
        }

        for (int userId : sameHomeTownFriends) {
            recommendedFriendIds.add(userId);
        }

        for (int userId : recommendedFriendIds) {
            User recommendedFriend = network.getUserById(userId);

            if (recommendedFriend != null) {
                recommendedFriends.add(recommendedFriend);
            }
        }

        sortUsersById(recommendedFriends);

        return recommendedFriends;
    }

    /**
     * Gets display text for all users in the network.
     *
     * @return user information strings
     */
    public ArrayList<String> getAllUserInformationList() {
        ArrayList<User> users = getAllUsersList();
        ArrayList<String> userInformationList = new ArrayList<>();

        for (User user : users) {
            int userId = user.getUserId();
            String username = user.getUsername();
            String homeTown = user.getHomeTown();
            String workPlace = user.getWorkPlace();

            String userInformation = "ID: " + userId;
            userInformation = userInformation + "    Name: " + username;
            userInformation = userInformation + "    Home: " + homeTown;
            userInformation = userInformation + "    Work: " + workPlace;

            userInformationList.add(userInformation);
        }

        return userInformationList;
    }

    /**
     * Sorts users by user ID in ascending order.
     *
     * @param users the users to sort
     */
    private void sortUsersById(ArrayList<User> users) {
        for (int i = 0; i < users.size(); i++) {
            for (int j = i + 1; j < users.size(); j++) {
                User user1 = users.get(i);
                User user2 = users.get(j);

                if (user1.getUserId() > user2.getUserId()) {
                    users.set(i, user2);
                    users.set(j, user1);
                }
            }
        }
    }
}
