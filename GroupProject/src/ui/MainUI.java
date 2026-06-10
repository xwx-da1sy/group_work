package ui;

import controller.MainController;
import model.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainUI extends JFrame {

    private MainController mainController;
    private JPanel userButtonPanel;
    private JPanel currentFriendPanel;

    // 创建主窗口
    public MainUI(MainController mainController) {
        this.mainController = mainController;

        this.setTitle("Social Network");
        this.setSize(960, 720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建主容器，最外层使用BorderLayout布局管理器
        JPanel mainPanel = new JPanel(new BorderLayout(0, 12));
        // 设置边框
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // -----------------------------顶部区域组件设置--------------------------

        // 创建顶部区域，用来放社交网络ID和当前用户信息
        JPanel topPanel = new JPanel(new GridLayout(2, 1));

        // 创建主标题标签
        JLabel titleLabel = new JLabel("Social Network");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        // 把主标题标签放进顶部区域
        topPanel.add(titleLabel);

        // 创建当前状态文本，用来显示当前社交网络ID和当前用户
        String currentUserText = "Network ID: " + mainController.getNetworkId();
        currentUserText = currentUserText + "        Current User: ";
        currentUserText = currentUserText + mainController.getCurrentUsername();
        currentUserText = currentUserText + " (ID ";
        currentUserText = currentUserText + mainController.getCurrentUserId();
        currentUserText = currentUserText + ")";

        // 创建当前状态标签，用来显示当前社交网络ID和当前用户
        JLabel currentUserLabel = new JLabel(currentUserText);
        currentUserLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // 把当前状态标签放进顶部区域
        topPanel.add(currentUserLabel);

        // 把顶部区域放进主容器的上方
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ---------------------------主要内容区域组件----------------------

        // 创建主要内容区域，用来左右摆放当前用户视角和社交网络管理视角
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 16, 0));

        // 创建当前用户视角区域
        JPanel currentUserPanel = createCurrentUserPanel();

        // 创建社交网络管理视角区域
        JPanel networkManagerPanel = new JPanel(new BorderLayout(0, 12));
        TitledBorder networkManagerBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Network Manager",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 15));
        networkManagerPanel.setBorder(BorderFactory.createCompoundBorder(
                networkManagerBorder,
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        // 把当前用户视角区域放在左边
        contentPanel.add(currentUserPanel);

        // 把社交网络管理视角区域放在右边
        contentPanel.add(networkManagerPanel);

        // 把主要内容区域放进主容器中间
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // ---------------------------中部区域组件----------------------

        // 创建中间区域，用来放搜索框和好友列表
        JPanel centerPanel = new JPanel(new BorderLayout());
        networkManagerPanel.add(centerPanel, BorderLayout.CENTER);
        JPanel userNetworkPanel = new JPanel(new GridBagLayout());

        // 添加布局约束器
        GridBagConstraints gridBagConstraintsOfCenter = new GridBagConstraints();
        gridBagConstraintsOfCenter.insets = new Insets(0, 0, 5, 0);
        gridBagConstraintsOfCenter.fill = GridBagConstraints.BOTH;

        // 添加搜索框的文字标签
        JLabel searchLabel = new JLabel("Search / Filter:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // 把搜索框的文字标签放在0,0的位置，不进行拉伸
        gridBagConstraintsOfCenter.gridx = 0;
        gridBagConstraintsOfCenter.gridy = 0;
        gridBagConstraintsOfCenter.weightx = 0;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 1;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.NONE;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.WEST;
        userNetworkPanel.add(searchLabel, gridBagConstraintsOfCenter);

        // 添加搜索框
        JTextField searchTextField = new JTextField();
        searchTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        // 把搜索框放在1,0的位置，进行水平拉伸
        gridBagConstraintsOfCenter.gridx = 1;
        gridBagConstraintsOfCenter.gridy = 0;
        gridBagConstraintsOfCenter.weightx = 1;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 1;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.WEST;
        userNetworkPanel.add(searchTextField, gridBagConstraintsOfCenter);

        // 添加搜索类型下拉框
        JComboBox<String> searchTypeComboBox = new JComboBox<>();
        searchTypeComboBox.addItem("Name");
        searchTypeComboBox.addItem("ID");
        searchTypeComboBox.addItem("Workplace");
        searchTypeComboBox.addItem("Hometown");
        searchTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        // 把搜索类型下拉框放在2,0的位置，不进行水平拉伸
        gridBagConstraintsOfCenter.gridx = 2;
        gridBagConstraintsOfCenter.gridy = 0;
        gridBagConstraintsOfCenter.weightx = 0;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 1;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.WEST;
        userNetworkPanel.add(searchTypeComboBox, gridBagConstraintsOfCenter);

        // 添加搜索按钮
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.setFocusPainted(false);

        // 把搜索按钮放在3,0的位置，不进行水平拉伸
        gridBagConstraintsOfCenter.gridx = 3;
        gridBagConstraintsOfCenter.gridy = 0;
        gridBagConstraintsOfCenter.weightx = 0;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 1;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.WEST;
        userNetworkPanel.add(searchButton, gridBagConstraintsOfCenter);

        // 添加重置按钮
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 14));
        resetButton.setFocusPainted(false);

        // 把重置按钮放在4,0的位置，不进行水平拉伸
        gridBagConstraintsOfCenter.gridx = 4;
        gridBagConstraintsOfCenter.gridy = 0;
        gridBagConstraintsOfCenter.weightx = 0;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 1;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.WEST;
        userNetworkPanel.add(resetButton, gridBagConstraintsOfCenter);

        //添加用户列表标签，我不想水平拉伸但是想要居中摆放
        JLabel usersLabel = new JLabel("All Users");
        usersLabel.setFont(new Font("Arial", Font.BOLD, 16));

        gridBagConstraintsOfCenter.gridx = 0;
        gridBagConstraintsOfCenter.gridy = 1;
        gridBagConstraintsOfCenter.weightx = 1;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 5;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.NONE;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.CENTER;
        userNetworkPanel.add(usersLabel, gridBagConstraintsOfCenter);

        // 添加用户按钮列表容器
        userButtonPanel = new JPanel();
        // 用户按钮列表使用BoxLayout，从上到下依次摆放每一个用户按钮
        userButtonPanel.setLayout(new BoxLayout(userButtonPanel, BoxLayout.Y_AXIS));

        // 根据控制器中保存的社交网络，刷新用户按钮列表
        refreshUserButtonList(mainController.getAllUsersList());

        // 给搜索按钮绑定点击事件
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String keyword = searchTextField.getText();
                String filterType = (String) searchTypeComboBox.getSelectedItem();

                ArrayList<User> filteredUsers = mainController.filterUsers(keyword, filterType);
                refreshUserButtonList(filteredUsers);
            }
        });

        // 给重置按钮绑定点击事件
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchTextField.setText("");
                searchTypeComboBox.setSelectedItem("Name");
                refreshUserButtonList(mainController.getAllUsersList());
            }
        });

        // 在右边添加一个滚动条
        JScrollPane userListScrollPane = new JScrollPane(userButtonPanel);

        gridBagConstraintsOfCenter.gridx = 0;
        gridBagConstraintsOfCenter.gridy = 2;
        gridBagConstraintsOfCenter.weightx = 1;
        gridBagConstraintsOfCenter.weighty = 1;
        gridBagConstraintsOfCenter.gridwidth = 5;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.BOTH;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.CENTER;
        userNetworkPanel.add(userListScrollPane, gridBagConstraintsOfCenter);

        // 把容器添加进中部容器中
        centerPanel.add(userNetworkPanel, BorderLayout.CENTER);

        // ---------------------------底部区域组件------------------------

        // 创建底部区域，用来放各种操作按钮
        JPanel bottomPanel = new JPanel();
        // 底部区域使用BoxLayout，从上到下摆放按钮区域
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        // 给底部区域添加一点上边距，让按钮不要紧贴中间列表
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        networkManagerPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 创建底部按钮区域，用GridLayout保证左右按钮一样长
        JPanel bottomButtonPanel = new JPanel(new GridLayout(3, 2, 8, 8));

        // 添加各种操作按钮
        JButton addUserButton = new JButton("Add User");
        addUserButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addUserButton.setFocusPainted(false);

        // 给添加用户按钮绑定点击事件
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddUserWindow();
            }
        });

        JButton removeUserButton = new JButton("Remove User");
        removeUserButton.setFont(new Font("Arial", Font.PLAIN, 14));
        removeUserButton.setFocusPainted(false);

        // 给删除用户按钮绑定点击事件
        removeUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRemoveUserWindow();
            }
        });

        JButton addFriendButton = new JButton("Add Friend");
        addFriendButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addFriendButton.setFocusPainted(false);

        // 给添加好友按钮绑定点击事件
        addFriendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddFriendWindow();
            }
        });

        JButton removeFriendButton = new JButton("Remove Friend");
        removeFriendButton.setFont(new Font("Arial", Font.PLAIN, 14));
        removeFriendButton.setFocusPainted(false);

        // 给删除好友按钮绑定点击事件
        removeFriendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRemoveFriendWindow();
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
        saveButton.setFocusPainted(false);

        // 给保存按钮绑定点击事件
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isSaved = mainController.saveCurrentNetwork();

                if (isSaved) {
                    JOptionPane.showMessageDialog(MainUI.this, "Network saved successfully.");
                } else {
                    JOptionPane.showMessageDialog(MainUI.this, "Failed to save network.");
                }
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.setFocusPainted(false);

        // 给退出登录按钮绑定点击事件
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        MainUI.this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    // 退出登录之后关闭主窗口
                    MainUI.this.dispose();

                    // 重新打开登录窗口
                    new LoginUI();
                }
            }
        });

        // 把添加用户按钮放进底部按钮区域
        bottomButtonPanel.add(addUserButton);

        // 把删除用户按钮放进底部按钮区域
        bottomButtonPanel.add(removeUserButton);

        // 把添加好友按钮放进底部按钮区域
        bottomButtonPanel.add(addFriendButton);

        // 把删除好友按钮放进底部按钮区域
        bottomButtonPanel.add(removeFriendButton);

        // 把保存按钮放进底部按钮区域
        bottomButtonPanel.add(saveButton);

        // 把退出登录按钮放进底部按钮区域
        bottomButtonPanel.add(logoutButton);

        // 让主要操作按钮区域尽量横向占满底部区域
        int bottomButtonPanelHeight = bottomButtonPanel.getPreferredSize().height;
        Dimension bottomButtonPanelSize = new Dimension(
                Integer.MAX_VALUE,
                bottomButtonPanelHeight);
        bottomButtonPanel.setMaximumSize(bottomButtonPanelSize);

        // 把主要操作按钮放进底部区域
        bottomPanel.add(bottomButtonPanel);

        this.add(mainPanel);

        // 将窗口设置为可见，这一步一定是在最后完成。
        this.setVisible(true);
    }

    // 创建当前用户视角区域
    private JPanel createCurrentUserPanel() {
        JPanel currentUserPanel = new JPanel(new BorderLayout(0, 12));
        TitledBorder currentUserBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Current User View",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 15));
        currentUserPanel.setBorder(BorderFactory.createCompoundBorder(
                currentUserBorder,
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));

        // 创建当前用户上方区域，用来摆放标题和基础信息
        JPanel currentUserTopPanel = new JPanel(new BorderLayout(0, 8));

        // 创建当前用户信息区域
        JPanel currentUserInformationPanel = new JPanel(new GridLayout(5, 1, 0, 6));

        User currentUser = mainController.getUserById(mainController.getCurrentUserId());

        // 添加当前用户ID信息
        JLabel userIdLabel = new JLabel("User ID: " + currentUser.getUserId());
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentUserInformationPanel.add(userIdLabel);

        // 添加当前用户名信息
        JLabel usernameLabel = new JLabel("Username: " + currentUser.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentUserInformationPanel.add(usernameLabel);

        // 添加当前用户家乡信息
        JLabel homeTownLabel = new JLabel("Home Town: " + currentUser.getHomeTown());
        homeTownLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentUserInformationPanel.add(homeTownLabel);

        // 添加当前用户工作地点信息
        JLabel workPlaceLabel = new JLabel("Work Place: " + currentUser.getWorkPlace());
        workPlaceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentUserInformationPanel.add(workPlaceLabel);

        // 添加当前用户好友数量信息
        JLabel friendsCountLabel = new JLabel("Friends Count: " + currentUser.getFriends().size());
        friendsCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentUserInformationPanel.add(friendsCountLabel);

        // 把当前用户信息区域放进当前用户上方区域
        currentUserTopPanel.add(currentUserInformationPanel, BorderLayout.CENTER);

        // 把当前用户上方区域放进当前用户视角区域
        currentUserPanel.add(currentUserTopPanel, BorderLayout.NORTH);

        // 创建当前用户好友区域
        currentFriendPanel = new JPanel();
        currentFriendPanel.setLayout(new BoxLayout(currentFriendPanel, BoxLayout.Y_AXIS));

        ArrayList<User> friendsList = mainController.getUserFriendsList(currentUser.getUserId());

        // 根据当前用户的好友列表，刷新好友按钮列表
        refreshCurrentFriendList(friendsList);

        // 给当前用户好友列表添加滚动条
        JScrollPane currentFriendScrollPane = new JScrollPane(currentFriendPanel);

        // 创建当前用户好友列表外层容器
        JPanel currentFriendContainer = new JPanel(new BorderLayout(0, 8));

        // 创建好友列表上方区域，用来摆放标题和筛选工具
        JPanel friendTopPanel = new JPanel(new BorderLayout(0, 6));

        JLabel currentFriendLabel = new JLabel("My Friends");
        currentFriendLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendTopPanel.add(currentFriendLabel, BorderLayout.NORTH);

        // 创建好友筛选区域
        JPanel friendFilterPanel = new JPanel(new GridBagLayout());

        // 创建好友筛选区域的布局约束对象
        GridBagConstraints friendFilterConstraints = new GridBagConstraints();
        friendFilterConstraints.insets = new Insets(0, 0, 5, 5);
        friendFilterConstraints.fill = GridBagConstraints.HORIZONTAL;

        // 添加好友筛选标签
        JLabel friendFilterLabel = new JLabel("Filter:");
        friendFilterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        friendFilterConstraints.gridx = 0;
        friendFilterConstraints.gridy = 0;
        friendFilterConstraints.weightx = 0;
        friendFilterConstraints.weighty = 0;
        friendFilterConstraints.gridwidth = 1;
        friendFilterPanel.add(friendFilterLabel, friendFilterConstraints);

        // 添加好友筛选输入框
        JTextField friendFilterTextField = new JTextField();
        friendFilterTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        friendFilterConstraints.gridx = 1;
        friendFilterConstraints.gridy = 0;
        friendFilterConstraints.weightx = 1;
        friendFilterConstraints.weighty = 0;
        friendFilterConstraints.gridwidth = 1;
        friendFilterPanel.add(friendFilterTextField, friendFilterConstraints);

        // 添加好友筛选类型下拉框
        JComboBox<String> friendFilterComboBox = new JComboBox<>();
        friendFilterComboBox.addItem("Name");
        friendFilterComboBox.addItem("ID");
        friendFilterComboBox.addItem("Workplace");
        friendFilterComboBox.addItem("Hometown");
        friendFilterComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        friendFilterConstraints.gridx = 2;
        friendFilterConstraints.gridy = 0;
        friendFilterConstraints.weightx = 0;
        friendFilterConstraints.weighty = 0;
        friendFilterConstraints.gridwidth = 1;
        friendFilterPanel.add(friendFilterComboBox, friendFilterConstraints);

        // 添加好友筛选搜索按钮
        JButton friendSearchButton = new JButton("Search");
        friendSearchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        friendSearchButton.setFocusPainted(false);
        friendFilterConstraints.gridx = 3;
        friendFilterConstraints.gridy = 0;
        friendFilterConstraints.weightx = 0;
        friendFilterConstraints.weighty = 0;
        friendFilterConstraints.gridwidth = 1;
        friendFilterPanel.add(friendSearchButton, friendFilterConstraints);

        // 添加好友筛选重置按钮
        JButton friendResetButton = new JButton("Reset");
        friendResetButton.setFont(new Font("Arial", Font.PLAIN, 14));
        friendResetButton.setFocusPainted(false);
        friendFilterConstraints.gridx = 4;
        friendFilterConstraints.gridy = 0;
        friendFilterConstraints.weightx = 0;
        friendFilterConstraints.weighty = 0;
        friendFilterConstraints.gridwidth = 1;
        friendFilterPanel.add(friendResetButton, friendFilterConstraints);

        // 把好友筛选区域放进好友列表上方区域
        friendTopPanel.add(friendFilterPanel, BorderLayout.CENTER);

        // 给好友筛选搜索按钮绑定点击事件
        friendSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String keyword = friendFilterTextField.getText();
                String filterType = (String) friendFilterComboBox.getSelectedItem();

                ArrayList<User> filteredFriends = mainController.filterCurrentUserFriends(
                        keyword,
                        filterType);
                refreshCurrentFriendList(filteredFriends);
            }
        });

        // 给好友筛选重置按钮绑定点击事件
        friendResetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                friendFilterTextField.setText("");
                friendFilterComboBox.setSelectedItem("Name");
                refreshCurrentFriendList(mainController.getUserFriendsList(mainController.getCurrentUserId()));
            }
        });

        // 把好友列表上方区域放进当前用户好友列表外层容器
        currentFriendContainer.add(friendTopPanel, BorderLayout.NORTH);

        currentFriendContainer.add(currentFriendScrollPane, BorderLayout.CENTER);

        // 把当前用户好友列表放进当前用户视角区域
        currentUserPanel.add(currentFriendContainer, BorderLayout.CENTER);

        return currentUserPanel;
    }

    // 刷新当前用户好友按钮列表
    private void refreshCurrentFriendList(ArrayList<User> friends) {
        currentFriendPanel.removeAll();

        // 把当前用户的每一个好友做成按钮
        for (User friend : friends) {
            int friendId = friend.getUserId();
            String friendUsername = friend.getUsername();

            String friendButtonText = "ID: " + friendId;
            friendButtonText = friendButtonText + "    Name: " + friendUsername;

            JButton friendButton = new JButton(friendButtonText);
            friendButton.setFont(new Font("Arial", Font.PLAIN, 13));
            friendButton.setFocusPainted(false);

            // 给好友按钮绑定点击事件
            friendButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    User selectedFriend = mainController.getUserById(friendId);
                    showUserInformationWindow(selectedFriend);
                }
            });

            // 让好友按钮尽量横向占满好友列表容器
            int friendButtonHeight = friendButton.getPreferredSize().height;
            Dimension friendButtonSize = new Dimension(
                    Integer.MAX_VALUE,
                    friendButtonHeight);
            friendButton.setMaximumSize(friendButtonSize);

            // 把好友按钮放进当前用户好友列表容器
            currentFriendPanel.add(friendButton);

            // 在每一个好友按钮之间添加一点固定空隙
            currentFriendPanel.add(Box.createVerticalStrut(6));
        }

        if (friends.isEmpty()) {
            JLabel emptyFriendLabel = new JLabel("No friends found.");
            emptyFriendLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            currentFriendPanel.add(emptyFriendLabel);
        }

        // 重新计算和重新绘制当前用户好友列表
        currentFriendPanel.revalidate();
        currentFriendPanel.repaint();
    }

    // 刷新用户按钮列表
    private void refreshUserButtonList(ArrayList<User> users) {
        userButtonPanel.removeAll();

        // 根据传入的用户列表，把每一个用户的信息做成按钮
        for (User user : users) {
            int userId = user.getUserId();
            String username = user.getUsername();

            String userButtonText = "ID: " + userId;
            userButtonText = userButtonText + "    Name: " + username;

            JButton userButton = new JButton(userButtonText);
            userButton.setFont(new Font("Arial", Font.PLAIN, 13));
            userButton.setFocusPainted(false);

            // 给每一个用户按钮绑定点击事件
            userButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    User selectedUser = mainController.getUserById(userId);
                    showUserInformationWindow(selectedUser);
                }
            });

            // 让每一个用户按钮尽量横向占满用户按钮列表容器
            int userButtonHeight = userButton.getPreferredSize().height;
            Dimension userButtonSize = new Dimension(
                    Integer.MAX_VALUE,
                    userButtonHeight);
            userButton.setMaximumSize(userButtonSize);

            // 把用户按钮放进用户按钮列表容器
            userButtonPanel.add(userButton);

            // 在每一个用户按钮之间添加一点固定空隙
            userButtonPanel.add(Box.createVerticalStrut(6));
        }

        if (users.isEmpty()) {
            JLabel emptyUserLabel = new JLabel("No users found.");
            emptyUserLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            userButtonPanel.add(emptyUserLabel);
        }

        // 重新计算和重新绘制用户按钮列表
        userButtonPanel.revalidate();
        userButtonPanel.repaint();
    }

    // 创建添加用户窗口
    private void showAddUserWindow() {
        // 创建添加用户窗口
        JFrame addUserFrame = new JFrame("Add User");
        addUserFrame.setSize(420, 330);
        addUserFrame.setLocationRelativeTo(this);
        addUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 创建添加用户窗口的主容器
        JPanel addUserPanel = new JPanel(new GridBagLayout());
        addUserPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        // 创建添加用户窗口的布局约束对象
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // 添加用户名标签
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        addUserPanel.add(usernameLabel, constraints);

        // 添加用户名输入框
        JTextField usernameTextField = new JTextField();
        usernameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addUserPanel.add(usernameTextField, constraints);

        // 添加密码标签
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        addUserPanel.add(passwordLabel, constraints);

        // 添加密码输入框
        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addUserPanel.add(passwordTextField, constraints);

        // 添加家乡标签
        JLabel homeTownLabel = new JLabel("Home Town:");
        homeTownLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        addUserPanel.add(homeTownLabel, constraints);

        // 添加家乡输入框
        JTextField homeTownTextField = new JTextField();
        homeTownTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addUserPanel.add(homeTownTextField, constraints);

        // 添加工作地点标签
        JLabel workPlaceLabel = new JLabel("Work Place:");
        workPlaceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        addUserPanel.add(workPlaceLabel, constraints);

        // 添加工作地点输入框
        JTextField workPlaceTextField = new JTextField();
        workPlaceTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addUserPanel.add(workPlaceTextField, constraints);

        // 创建确认添加按钮
        JButton confirmButton = new JButton("Add User");
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addUserPanel.add(confirmButton, constraints);

        // 创建取消按钮
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setFocusPainted(false);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addUserPanel.add(cancelButton, constraints);

        // 给确认添加按钮绑定点击事件
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = new String(passwordTextField.getPassword());
                String homeTown = homeTownTextField.getText();
                String workPlace = workPlaceTextField.getText();

                User newUser = mainController.createNewUser(
                        username,
                        password,
                        homeTown,
                        workPlace);

                if (newUser == null) {
                    JOptionPane.showMessageDialog(addUserFrame, "User information cannot be empty.");
                    return;
                }

                JOptionPane.showMessageDialog(addUserFrame,
                        "User added successfully.\n"
                                + "User ID: " + newUser.getUserId());

                // 添加成功之后关闭添加用户窗口
                addUserFrame.dispose();

                // 添加成功之后刷新主窗口，让新用户显示在列表中
                MainUI.this.dispose();
                new MainUI(mainController);
            }
        });

        // 给取消按钮绑定点击事件
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUserFrame.dispose();
            }
        });

        addUserFrame.add(addUserPanel);
        addUserFrame.setVisible(true);
    }

    // 创建删除用户窗口
    private void showRemoveUserWindow() {
        // 创建删除用户窗口
        JFrame removeUserFrame = new JFrame("Remove User");
        removeUserFrame.setSize(380, 190);
        removeUserFrame.setLocationRelativeTo(this);
        removeUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 创建删除用户窗口的主容器
        JPanel removeUserPanel = new JPanel(new GridBagLayout());
        removeUserPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        // 创建删除用户窗口的布局约束对象
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // 添加用户ID标签
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        removeUserPanel.add(userIdLabel, constraints);

        // 添加用户ID输入框
        JTextField userIdTextField = new JTextField();
        userIdTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        removeUserPanel.add(userIdTextField, constraints);

        // 创建确认删除按钮
        JButton confirmButton = new JButton("Remove User");
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        removeUserPanel.add(confirmButton, constraints);

        // 创建取消按钮
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setFocusPainted(false);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        removeUserPanel.add(cancelButton, constraints);

        // 给确认删除按钮绑定点击事件
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int targetUserId = Integer.parseInt(userIdTextField.getText());
                    boolean isRemoved = mainController.removeUserFromNetwork(targetUserId);

                    if (isRemoved) {
                        JOptionPane.showMessageDialog(removeUserFrame, "User removed successfully.");
                        removeUserFrame.dispose();

                        // 删除成功之后刷新主窗口，让用户列表同步更新
                        MainUI.this.dispose();
                        new MainUI(mainController);
                    } else {
                        JOptionPane.showMessageDialog(removeUserFrame, "Failed to remove user.");
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(removeUserFrame, "User ID must be a number.");
                }
            }
        });

        // 给取消按钮绑定点击事件
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeUserFrame.dispose();
            }
        });

        removeUserFrame.add(removeUserPanel);
        removeUserFrame.setVisible(true);
    }

    // 创建添加好友窗口
    private void showAddFriendWindow() {
        // 创建添加好友窗口
        JFrame addFriendFrame = new JFrame("Add Friend");
        addFriendFrame.setSize(360, 190);
        addFriendFrame.setLocationRelativeTo(this);
        addFriendFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 创建添加好友窗口的主容器
        JPanel addFriendPanel = new JPanel(new GridBagLayout());
        addFriendPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        // 创建添加好友窗口的布局约束对象
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // 添加用户ID标签
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        addFriendPanel.add(userIdLabel, constraints);

        // 添加用户ID输入框
        JTextField userIdTextField = new JTextField();
        userIdTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addFriendPanel.add(userIdTextField, constraints);

        // 创建确认添加按钮
        JButton confirmButton = new JButton("Add Friend");
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addFriendPanel.add(confirmButton, constraints);

        // 创建取消按钮
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setFocusPainted(false);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addFriendPanel.add(cancelButton, constraints);

        // 给确认添加按钮绑定点击事件
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int targetUserId = Integer.parseInt(userIdTextField.getText());
                    boolean isAdded = mainController.addFriendToCurrentUser(targetUserId);

                    if (isAdded) {
                        JOptionPane.showMessageDialog(addFriendFrame, "Friend added successfully.");
                        addFriendFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(addFriendFrame, "Failed to add friend.");
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(addFriendFrame, "User ID must be a number.");
                }
            }
        });

        // 给取消按钮绑定点击事件
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addFriendFrame.dispose();
            }
        });

        addFriendFrame.add(addFriendPanel);
        addFriendFrame.setVisible(true);
    }

    // 创建删除好友窗口
    private void showRemoveFriendWindow() {
        // 创建删除好友窗口
        JFrame removeFriendFrame = new JFrame("Remove Friend");
        removeFriendFrame.setSize(380, 190);
        removeFriendFrame.setLocationRelativeTo(this);
        removeFriendFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 创建删除好友窗口的主容器
        JPanel removeFriendPanel = new JPanel(new GridBagLayout());
        removeFriendPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        // 创建删除好友窗口的布局约束对象
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // 添加用户ID标签
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        removeFriendPanel.add(userIdLabel, constraints);

        // 添加用户ID输入框
        JTextField userIdTextField = new JTextField();
        userIdTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        removeFriendPanel.add(userIdTextField, constraints);

        // 创建确认删除按钮
        JButton confirmButton = new JButton("Remove Friend");
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        removeFriendPanel.add(confirmButton, constraints);

        // 创建取消按钮
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setFocusPainted(false);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        removeFriendPanel.add(cancelButton, constraints);

        // 给确认删除按钮绑定点击事件
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int targetUserId = Integer.parseInt(userIdTextField.getText());
                    boolean isRemoved = mainController.removeFriendFromCurrentUser(targetUserId);

                    if (isRemoved) {
                        JOptionPane.showMessageDialog(removeFriendFrame, "Friend removed successfully.");
                        removeFriendFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(removeFriendFrame, "Failed to remove friend.");
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(removeFriendFrame, "User ID must be a number.");
                }
            }
        });

        // 给取消按钮绑定点击事件
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeFriendFrame.dispose();
            }
        });

        removeFriendFrame.add(removeFriendPanel);
        removeFriendFrame.setVisible(true);
    }

    // 创建用户信息窗口，显示用户的全部基础信息和好友信息
    private void showUserInformationWindow(User user) {
        if (user == null) {
            JOptionPane.showMessageDialog(this, "This user does not exist.");
            return;
        }

        // 创建用户信息窗口
        JFrame userInformationFrame = new JFrame("User Information");
        userInformationFrame.setSize(420, 460);
        userInformationFrame.setLocationRelativeTo(this);
        userInformationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 创建用户信息窗口的主容器
        JPanel userInformationPanel = new JPanel(new GridBagLayout());
        userInformationPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        // 创建用户信息窗口的布局约束对象
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // 添加用户ID信息
        JLabel userIdLabel = new JLabel("User ID: " + user.getUserId());
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        userInformationPanel.add(userIdLabel, constraints);

        // 添加用户名信息
        JLabel usernameLabel = new JLabel("Username: " + user.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        userInformationPanel.add(usernameLabel, constraints);

        // 添加家乡信息
        JLabel homeTownLabel = new JLabel("Home Town: " + user.getHomeTown());
        homeTownLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 1;
        userInformationPanel.add(homeTownLabel, constraints);

        // 添加工作地点信息
        JLabel workPlaceLabel = new JLabel("Work Place: " + user.getWorkPlace());
        workPlaceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 1;
        userInformationPanel.add(workPlaceLabel, constraints);

        // 添加好友数量信息
        JLabel friendsCountLabel = new JLabel("Friends Count: " + user.getFriends().size());
        friendsCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weightx = 1;
        userInformationPanel.add(friendsCountLabel, constraints);

        // 添加好友列表标题
        JLabel friendsLabel = new JLabel("Friends:");
        friendsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        userInformationPanel.add(friendsLabel, constraints);

        // 创建好友列表容器
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BoxLayout(friendPanel, BoxLayout.Y_AXIS));

        // 从控制器中获取这个用户的好友对象列表
        ArrayList<User> friendsList = mainController.getUserFriendsList(user.getUserId());

        // 把每一个好友的信息和添加好友按钮放进好友列表中
        for (User friend : friendsList) {
            int friendId = friend.getUserId();
            String friendUsername = friend.getUsername();

            String friendText = "ID: " + friendId;
            friendText = friendText + "    Name: " + friendUsername;

            // 创建单个好友信息行
            JPanel friendRowPanel = new JPanel(new BorderLayout(8, 0));

            JLabel friendLabel = new JLabel(friendText);
            friendLabel.setFont(new Font("Arial", Font.PLAIN, 13));

            // 创建添加好友按钮
            JButton addFriendButton = new JButton("Add Friend");
            addFriendButton.setFont(new Font("Arial", Font.PLAIN, 12));
            addFriendButton.setFocusPainted(false);

            // 给添加好友按钮绑定点击事件
            addFriendButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mainController.addFriendToCurrentUser(friendId);
                }
            });

            // 把好友信息放在好友信息行的左边
            friendRowPanel.add(friendLabel, BorderLayout.CENTER);

            // 把添加好友按钮放在好友信息行的右边
            friendRowPanel.add(addFriendButton, BorderLayout.EAST);

            // 让好友信息行尽量横向占满好友列表容器
            int friendRowPanelHeight = friendRowPanel.getPreferredSize().height;
            Dimension friendRowPanelSize = new Dimension(
                    Integer.MAX_VALUE,
                    friendRowPanelHeight);
            friendRowPanel.setMaximumSize(friendRowPanelSize);

            // 把好友信息行放进好友列表容器
            friendPanel.add(friendRowPanel);

            // 在每一个好友信息行之间添加一点固定空隙
            friendPanel.add(Box.createVerticalStrut(6));
        }

        if (friendsList.isEmpty()) {
            JLabel emptyFriendLabel = new JLabel("No friends yet.");
            emptyFriendLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            friendPanel.add(emptyFriendLabel);
        }

        // 给好友列表添加滚动条
        JScrollPane friendListScrollPane = new JScrollPane(friendPanel);

        // 把好友列表放进用户信息窗口
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        userInformationPanel.add(friendListScrollPane, constraints);

        // 创建关闭按钮
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.setFocusPainted(false);

        // 给关闭按钮绑定点击事件
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userInformationFrame.dispose();
            }
        });

        // 把关闭按钮放进用户信息窗口
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        userInformationPanel.add(closeButton, constraints);

        userInformationFrame.add(userInformationPanel);
        userInformationFrame.setVisible(true);
    }
}
