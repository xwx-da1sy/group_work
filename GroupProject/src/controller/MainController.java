package controller;

import model.Network;
import model.NetworkFileManager;
import model.User;

import java.util.ArrayList;
import java.util.HashSet;

public class MainController {

    private Network network;
    private NetworkFileManager fileManager;

    public MainController(Network network) {
        this.network = network;
        fileManager = new NetworkFileManager();
    }

    // 获取当前主控制器正在管理的社交网络
    public Network getNetwork() {
        return network;
    }

    // 获取当前社交网络的ID
    public String getNetworkId() {
        return network.getNetworkId();
    }

    // 获取当前用户的ID
    public int getCurrentUserId() {
        return network.getCurrentUser().getUserId();
    }

    // 获取当前用户的用户名
    public String getCurrentUsername() {
        return network.getCurrentUser().getUsername();
    }

    // 获取当前用户的好友数量
    public int getCurrentUserFriendsCount() {
        return network.getCurrentUserFriends().size();
    }

    // 获取社交网络中的所有用户对象
    public ArrayList<User> getAllUsersList() {
        ArrayList<User> users = new ArrayList<>(network.getAllUsers());

        // 按照用户ID从小到大排序，这样界面显示比较稳定
        sortUsersById(users);

        return users;
    }

    // 按照用户ID获取用户对象
    public User getUserById(int userId) {
        return network.getUserById(userId);
    }

    // 按照用户ID获取这个用户的所有好友对象
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

        // 按照用户ID从小到大排序，这样界面显示比较稳定
        sortUsersById(friendsList);

        return friendsList;
    }

    // 把目标用户添加为当前用户的好友
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

    // 把目标用户从当前用户的好友列表中删除
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

    // 在当前社交网络中创建一个新的用户
    public User createNewUser(String username, String password, String homeTown, String workPlace) {
        if (username == null || password == null || homeTown == null || workPlace == null) {
            return null;
        }

        if (username.isEmpty() || password.isEmpty() || homeTown.isEmpty() || workPlace.isEmpty()) {
            return null;
        }

        return network.createUser(username, password, homeTown, workPlace);
    }

    // 从当前社交网络中删除一个用户
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

    // 保存当前社交网络
    public boolean saveCurrentNetwork() {
        return fileManager.saveNetwork(network);
    }

    // 按照指定的信息类型筛选用户
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

    // 按照指定的信息类型筛选当前用户的好友
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

    // 获取目标用户和当前用户的共同好友
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

        // 按照用户ID从小到大排序，这样界面显示比较稳定
        sortUsersById(commonFriends);

        return commonFriends;
    }

    // 获取当前用户的好友推荐列表
    public ArrayList<User> getFriendRecommendationsForCurrentUser() {
        ArrayList<User> recommendedFriends = new ArrayList<>();
        HashSet<Integer> recommendedFriendIds = new HashSet<>();

        HashSet<Integer> sameWorkPlaceFriends = network.filterFriendsOfFriendsBySameWorkPlace();
        HashSet<Integer> sameHomeTownFriends = network.filterFriendsOfFriendsBySameHomeTown();

        // 把同工作地点的朋友的朋友加入推荐集合
        for (int userId : sameWorkPlaceFriends) {
            recommendedFriendIds.add(userId);
        }

        // 把同家乡的朋友的朋友加入推荐集合
        for (int userId : sameHomeTownFriends) {
            recommendedFriendIds.add(userId);
        }

        // 按照ID从哈希表中提取用户对象，方便UI展示
        for (int userId : recommendedFriendIds) {
            User recommendedFriend = network.getUserById(userId);

            if (recommendedFriend != null) {
                recommendedFriends.add(recommendedFriend);
            }
        }

        // 按照用户ID从小到大排序，这样界面显示比较稳定
        sortUsersById(recommendedFriends);

        return recommendedFriends;
    }

    // 获取社交网络中所有用户的展示信息
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

    // 按照用户ID从小到大排序
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
