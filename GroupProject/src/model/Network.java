package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Network {

    private static final int MAX_USERS = 20;

    private int totalUsers;
    private int nextUserId;
    private HashMap<Integer, User> userNetwork;
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
        setCurrentUser(currentUser);
    }

    // 这是一个自带初始用户的构造方法
    public Network(String username, String password, String homeTown, String workPlace) {
        this();
        currentUser = createUser(username, password, homeTown, workPlace);
    }

    // ----------------------以下方法和基础检查有关------------------------

    // 检查网络中是否已经存在该 ID 对应的 user
    public boolean userIdExists(int userId) {
        if (!userNetwork.containsKey(userId)) {
            System.out.println("This user ID does not exist.");
            return false;
        }

        return true;
    }

    // 检查网络中是否已经存在该用户名对应的 user
    public boolean userExists(String username) {
        if (getUsersByUsername(username).isEmpty()) {
            System.out.println("This user does not exist.");
            return false;
        }

        return true;
    }

    // ----------------------以下方法和当前用户有关------------------------

    public void setCurrentUser(User user) {
        if (user == null || !userIdExists(user.getUserId())) {
            return;
        }

        currentUser = userNetwork.get(user.getUserId());
    }

    // 按照 ID 设置当前用户
    public void setCurrentUserById(int userId) {
        if (!userIdExists(userId)) {
            return;
        }

        currentUser = userNetwork.get(userId);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // 清空当前用户
    public void clearCurrentUser() {
        currentUser = null;
    }

    // ----------------------以下方法和创建用户有关------------------------

    public void addUser(User user) {
        if (user == null) {
            return;
        }

        if (userNetwork.size() >= MAX_USERS) {
            System.out.println("The network is full.");
            return;
        }

        // 检查社交网络中是否已经有重复的用户 ID
        if (userNetwork.containsKey(user.getUserId())) {
            System.out.println("This user ID already exists.");
            return;
        }

        // 把 User 对象放进哈希表中，userId 是 key，User 对象是 value
        userNetwork.put(user.getUserId(), user);

        // 更新总用户数和下一个用户 ID
        totalUsers = userNetwork.size();
        nextUserId = Math.max(nextUserId, user.getUserId() + 1);

        if (currentUser == null && user.getUserId() == 0) {
            currentUser = user;
        }
    }

    // 这个方法先创建一个用户，然后把用户添加到哈希表中
    public User createUser(String username, String password, String homeTown, String workPlace) {
        if (nextUserId >= MAX_USERS) {
            System.out.println("The network is full.");
            return null;
        }

        User user = new User(username, password, homeTown, workPlace, nextUserId);
        addUser(user);
        return user;
    }


    // ----------------------------------登录有关---------------------------------

    // 用户登录
    public boolean checkLogin(int userId, String password) {
        if (!checkPassword(userId, password)) {
            return false;
        }

        currentUser = userNetwork.get(userId);
        return true;
    }

    // 判断同一个用户 ID 下面的密码是否和输入的密码相同
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


    //----------------------以下方法和获取信息有关------------------------

    // 按照 ID 提取 user
    public User getUserById(int userId) {
        if (!userIdExists(userId)) {
            return null;
        }

        return userNetwork.get(userId);
    }

    // 按照名字提取 user，如果名字重复就需要改用 ID
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

    // 按照名字提取所有同名 user
    public HashSet<User> getUsersByUsername(String username) {
        HashSet<User> users = new HashSet<>();

        for (User user : userNetwork.values()) {
            if (user.getUsername().equals(username)) {
                users.add(user);
            }
        }

        return users;
    }

    // 按照名字提取所有同名 user 的 ID
    public HashSet<Integer> getUserIdsByUsername(String username) {
        HashSet<Integer> userIds = new HashSet<>();

        for (User user : getUsersByUsername(username)) {
            userIds.add(user.getUserId());
        }

        return userIds;
    }

    // 按照 ID 提取 username
    public String getUsernameById(int userId) {
        User user = getUserById(userId);
        if (user == null) {
            return null;
        }

        return user.getUsername();
    }

    // 获取网络中用户的总数
    public int getTotalUsers() {
        return totalUsers;
    }

    // 获取网络中所有用户
    public Collection<User> getAllUsers() {
        return new HashSet<>(userNetwork.values());
    }

    // 获取任意用户的好友列表
    public HashSet<Integer> getFriendsList(int userId) {
        if (!userIdExists(userId)) {
            return null;
        }

        return new HashSet<>(userNetwork.get(userId).getFriends());
    }

    // 获取任意用户的好友列表，如果用户名重复就需要改用 ID
    public HashSet<Integer> getFriendsList(String username) {
        User user = getUser(username);
        if (user == null) {
            return null;
        }

        return getFriendsList(user.getUserId());
    }

    // 获取当前用户的好友列表
    public HashSet<Integer> getCurrentUserFriends() {
        if (currentUser == null) {
            System.out.println("No current user.");
            return new HashSet<>();
        }

        return getFriendsList(currentUser.getUserId());
    }


    // -------------------------以下方法和建立好友关系删除好友关系有关-------------------------

    // 检查当前用户是否存在该好友
    public boolean isFriend(int userId) {
        if (!userIdExists(userId)) {
            return false;
        }

        if (currentUser == null) {
            System.out.println("No current user.");
            return false;
        }

        // 比较 currentUser 的朋友集合中是否包含这个 userId
        if (currentUser.getFriends().contains(userId)) {
            return true;
        }

        System.out.println("This user is not your friend.");
        return false;
    }

    // 检查当前用户是否存在该好友，如果用户名重复就需要改用 ID
    public boolean isFriend(String username) {
        User user = getUser(username);
        if (user == null) {
            return false;
        }

        return isFriend(user.getUserId());
    }

    // 查看好友，如果该好友存在就直接返回这个好友对象，如果不存在就返回空
    public User viewFriend(int userId) {
        if (isFriend(userId)) {
            return userNetwork.get(userId);
        }

        return null;
    }

    // 查看好友，如果用户名重复就需要改用 ID
    public User viewFriend(String username) {
        User user = getUser(username);
        if (user == null) {
            return null;
        }

        return viewFriend(user.getUserId());
    }

    // 添加双向好友关系，意思就是我这边添加了你，你那边也要添加上我
    public void addEachOther(int userId1, int userId2) {
        //判断这两个用户 ID 是否都存在
        if (!userIdExists(userId1) || !userIdExists(userId2)) {
            return;
        }
        // 确定这两个用户的 ID 不一样，如果两个用户 ID 一样则在控制台输出相应的信息
        if (userId1 == userId2) {
            System.out.println("You cannot add yourself as a friend.");
            return;
        }
        // 判断这两个用户是否已经成为了好友，如果已经成为好友那么久不能加好友，并且在控制台输出相应的信息
        // 这里我们需要通过 ID 从哈希表中提取 user 对象并比较二者是否已经加上好友
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

    // 添加双向好友关系，如果用户名重复就需要改用 ID
    public void addEachOther(String username1, String username2, String password1, String password2) {
        User user1 = getUser(username1);
        User user2 = getUser(username2);

        if (user1 == null || user2 == null) {
            return;
        }

        addEachOther(user1.getUserId(), user2.getUserId());
    }

    // 删除双向好友关系，意思就是我这边删除了你，你那边也要删除上我
    public void removeEachOther(int userId1, int userId2) {
        //判断这两个用户 ID 是否都存在
        if (!userIdExists(userId1) || !userIdExists(userId2)) {
            return;
        }

        //  确定这两个用户的 ID 不一样，如果两个用户 ID 一样则在控制台输出相应的信息
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

    // 删除双向好友关系，如果用户名重复就需要改用 ID
    public void removeEachOther(String username1, String username2) {
        User user1 = getUser(username1);
        User user2 = getUser(username2);

        if (user1 == null || user2 == null) {
            return;
        }

        removeEachOther(user1.getUserId(), user2.getUserId());
    }

    // 移除好友
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

    // 移除好友，如果用户名重复就需要改用 ID
    public void removeFriend(String username) {
        User user = getUser(username);
        if (user == null) {
            return;
        }

        removeFriend(user.getUserId());
    }

    // -------------------以下方法和筛选有关---------------------

    // 通过工作地点筛选用户
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

    // 通过家乡筛选好友
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

    // 筛选和当前用户工作地点相同的好友
    public HashSet<Integer> filterFriendsBySameWorkPlace() {
        if (currentUser == null) {
            System.out.println("No current user.");
            return new HashSet<>();
        }

        return filterFriendsByWorkPlace(currentUser.getWorkPlace());
    }

    // 筛选和当前用户家乡相同的好友
    public HashSet<Integer> filterFriendsBySameHomeTown() {
        if (currentUser == null) {
            System.out.println("No current user.");
            return new HashSet<>();
        }

        return filterFriendsByHomeTown(currentUser.getHomeTown());
    }

    // 筛选朋友的朋友圈中和指定用户工作地点相同的朋友
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

    // 筛选当前用户的朋友的和当前用户工作地点相同朋友
    public HashSet<Integer> filterFriendsOfFriendsBySameWorkPlace() {
        if (currentUser == null) {
            System.out.println("No current user.");
            return new HashSet<>();
        }

        return filterFriendsOfFriendsByWorkPlace(currentUser.getUserId(), currentUser.getWorkPlace());
    }

    // 筛选朋友的朋友圈中和指定用户家乡相同的朋友
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

    // 筛选当前用户的朋友的和当前用户家乡相同朋友
    public HashSet<Integer> filterFriendsOfFriendsBySameHomeTown() {
        if (currentUser == null) {
            System.out.println("No current user.");
            return new HashSet<>();
        }

        return filterFriendsOfFriendsByHomeTown(currentUser.getUserId(), currentUser.getHomeTown());
    }
}
