package controller;

import model.Network;
import model.User;

import java.util.ArrayList;

public class MainController {

    private Network network;

    public MainController(Network network) {
        this.network = network;
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
