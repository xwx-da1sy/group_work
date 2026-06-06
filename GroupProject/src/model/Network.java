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
        setCurrentUser(currentUser);
    }

    // 这是一个自带初始用户的构造方法
    public Network(String username, String password, String homeTown, String workPlace) {
        this();
        currentUser = createUser(username, password, homeTown, workPlace);
    }

    // 检查网络中是否已经存在该用户名对应的 user
    public boolean userExists(String username) {
        if (!userNetwork.containsKey(username)) {
            System.out.println("This user does not exist.");
            return false;
        }

        return true;
    }

    public void setCurrentUser(User user) {
        if (user == null || !userExists(user.getUsername())) {
            return;
        }

        currentUser = userNetwork.get(user.getUsername());
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // 清空当前用户
    public void clearCurrentUser() {
        currentUser = null;
    }

    public void addUser(User user) {
        if (user == null) {
            return;
        }

        if (userNetwork.size() >= MAX_USERS) {
            System.out.println("The network is full.");
            return;
        }

        // 检查社交网络中是否已经有重名账号
        if (userNetwork.containsKey(user.getUsername())) {
            System.out.println("This user already exists.");
            return;
        }

        // 把 User 对象放进哈希表中，username 是 key，User 对象是 value
        userNetwork.put(user.getUsername(), user);

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

        if (userNetwork.containsKey(username)) {
            System.out.println("This user already exists.");
            return null;
        }

        User user = new User(username, password, homeTown, workPlace, nextUserId);
        addUser(user);
        return user;
    }

    // 按照名字提取 user
    public User getUser(String username) {
        if (!userExists(username)) {
            return null;
        }

        return userNetwork.get(username);
    }

    // 检查网络中是否已经存在该 ID 对应的 user
    public boolean userIdExists(int userId) {
        if (getUserById(userId) == null) {
            System.out.println("This user ID does not exist.");
            return false;
        }

        return true;
    }

    // 按照 ID 提取 user
    public User getUserById(int userId) {
        for (User user : userNetwork.values()) {
            if (user.getUserId() == userId) {
                return user;
            }
        }

        return null;
    }

    // 按照 ID 提取 username
    public String getUsernameById(int userId) {
        User user = getUserById(userId);
        if (user == null) {
            System.out.println("This user ID does not exist.");
            return null;
        }

        return user.getUsername();
    }

    // 用户登录
    public boolean checkLogin(String username, String password) {
        if (!checkPassword(username, password)) {
            return false;
        }

        currentUser = userNetwork.get(username);
        return true;
    }

    // 判断同一个用户名下面的密码是否和输入的密码相同
    public boolean checkPassword(String username, String password) {
        if (!userExists(username)) {
            return false;
        }

        if (userNetwork.get(username).getPassword().equals(password)) {
            return true;
        }

        System.out.println("Incorrect password.");
        return false;
    }

    // 检查当前用户是否存在该好友
    public boolean isFriend(String username) {
        if (!userExists(username)) {
            return false;
        }

        if (currentUser == null) {
            System.out.println("No current user.");
            return false;
        }

        // 比较 currentUser 的朋友集合中是否包含这个 username
        if (currentUser.getFriends().contains(username)) {
            return true;
        }

        System.out.println("This user is not your friend.");
        return false;
    }

    // 查看好友，如果该好友存在就直接返回这个好友对象，如果不存在就返回空
    public User viewFriend(String username) {
        if (isFriend(username)) {
            return userNetwork.get(username);
        }

        return null;
    }

    // 添加双向好友关系，意思就是我这边添加了你，你那边也要添加上我
    public void addEachOther(String username1, String username2, String password1, String password2) {
        //判断这两个用户名字是否都存在
        if (!userExists(username1) || !userExists(username2)) {
            return;
        }
        // 确定这两个用户的名字不一样，如果两个用户名字一样则在控制台输出相应的信息
        if (username1.equals(username2)) {
            System.out.println("You cannot add yourself as a friend.");
            return;
        }
        // 判断这两个用户是否已经成为了好友，如果已经成为好友那么久不能加好友，并且在控制台输出相应的信息
        // 这里我们需要通过名字从哈希表中提取user对象并调用isFriend（）方法比较二者是否已经加上好友
        User user1 = userNetwork.get(username1);
        User user2 = userNetwork.get(username2);

        boolean user1HasUser2 = user1.getFriends().contains(username2);
        boolean user2HasUser1 = user2.getFriends().contains(username1);

        if (user1HasUser2 && user2HasUser1) {
            System.out.println("You are already friends.");
            return;
        }

        if (!user1HasUser2) {
            user1.addFriend(username2);
        }

        if (!user2HasUser1) {
            user2.addFriend(username1);
        }
    }

    // 删除双向好友关系，意思就是我这边删除了你，你那边也要删除上我
    public void removeEachOther(String username1, String username2) {
        //判断这两个用户名字是否都存在
        if (!userExists(username1) || !userExists(username2)) {
            return;
        }

        //  确定这两个用户的名字不一样，如果两个用户名字一样则在控制台输出相应的信息
        if (username1.equals(username2)) {
            System.out.println("You cannot remove yourself as a friend.");
            return;
        }
        User user1 = userNetwork.get(username1);
        User user2 = userNetwork.get(username2);

        boolean user1HasUser2 = user1.getFriends().contains(username2);
        boolean user2HasUser1 = user2.getFriends().contains(username1);

        if (!user1HasUser2 && !user2HasUser1) {
            System.out.println("These users are not friends.");
            return;
        }

        if (user1HasUser2) {
            user1.removeFriend(username2);
        }

        if (user2HasUser1) {
            user2.removeFriend(username1);
        }
    }

    // 移除好友
    public void removeFriend(String username) {
        if (!userExists(username)) {
            return;
        }

        if (currentUser == null) {
            System.out.println("No current user.");
            return;
        }

        if (!currentUser.getFriends().contains(username)) {
            System.out.println("This user is not your friend.");
            return;
        }

        currentUser.removeFriend(username);
    }

    // 获取网络中用户的总数
    public int getTotalUsers() {
        return totalUsers;
    }
}
