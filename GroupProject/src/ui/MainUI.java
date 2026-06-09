package ui;

import controller.MainController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI extends JFrame {

    private MainController mainController;

    // 创建主窗口
    public MainUI(MainController mainController) {
        this.mainController = mainController;

        this.setTitle("Social Network");
        this.setSize(520, 720);
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

        // ---------------------------中部区域组件----------------------

        // 创建中间区域，用来放搜索框和好友列表
        JPanel centerPanel = new JPanel(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);
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

        //添加用户列表标签，我不想水平拉伸但是想要居中摆放
        JLabel usersLabel = new JLabel("Users");
        usersLabel.setFont(new Font("Arial", Font.BOLD, 16));

        gridBagConstraintsOfCenter.gridx = 0;
        gridBagConstraintsOfCenter.gridy = 1;
        gridBagConstraintsOfCenter.weightx = 1;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 2;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.NONE;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.CENTER;
        userNetworkPanel.add(usersLabel, gridBagConstraintsOfCenter);

        // 添加用户按钮列表容器
        JPanel userButtonPanel = new JPanel();
        // 用户按钮列表使用BoxLayout，从上到下依次摆放每一个用户按钮
        userButtonPanel.setLayout(new BoxLayout(userButtonPanel, BoxLayout.Y_AXIS));

        // 根据控制器中保存的社交网络，把每一个用户的信息做成按钮
        for (User user : mainController.getAllUsersList()) {
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

        if (mainController.getAllUsersList().isEmpty()) {
            JLabel emptyUserLabel = new JLabel("No users loaded yet.");
            emptyUserLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            userButtonPanel.add(emptyUserLabel);
        }

        // 在右边添加一个滚动条
        JScrollPane userListScrollPane = new JScrollPane(userButtonPanel);

        gridBagConstraintsOfCenter.gridx = 0;
        gridBagConstraintsOfCenter.gridy = 2;
        gridBagConstraintsOfCenter.weightx = 1;
        gridBagConstraintsOfCenter.weighty = 1;
        gridBagConstraintsOfCenter.gridwidth = 2;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.BOTH;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.CENTER;
        userNetworkPanel.add(userListScrollPane, gridBagConstraintsOfCenter);

        // 把容器添加进中部容器中
        centerPanel.add(userNetworkPanel, BorderLayout.CENTER);

        // ---------------------------底部区域组件------------------------

        // 创建底部区域，用来放各种操作按钮
        JPanel bottomPanel = new JPanel();
        // 底部区域使用BoxLayout，从上到下依次摆放按钮区域和退出登录按钮
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        // 给底部区域添加一点上边距，让按钮不要紧贴中间列表
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 创建底部按钮区域，用GridLayout保证左右按钮一样长
        JPanel bottomButtonPanel = new JPanel(new GridLayout(2, 2, 8, 8));

        // 添加各种操作按钮
        JButton addUserButton = new JButton("Add User");
        addUserButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addUserButton.setFocusPainted(false);

        JButton addFriendButton = new JButton("Add Friend");
        addFriendButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addFriendButton.setFocusPainted(false);

        JButton removeFriendButton = new JButton("Remove Friend");
        removeFriendButton.setFont(new Font("Arial", Font.PLAIN, 14));
        removeFriendButton.setFocusPainted(false);

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
        saveButton.setFocusPainted(false);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.setFocusPainted(false);

        // 把添加用户按钮放进底部按钮区域
        bottomButtonPanel.add(addUserButton);

        // 把添加好友按钮放进底部按钮区域
        bottomButtonPanel.add(addFriendButton);

        // 把删除好友按钮放进底部按钮区域
        bottomButtonPanel.add(removeFriendButton);

        // 把保存按钮放进底部按钮区域
        bottomButtonPanel.add(saveButton);

        // 让四个主要操作按钮区域尽量横向占满底部区域
        int bottomButtonPanelHeight = bottomButtonPanel.getPreferredSize().height;
        Dimension bottomButtonPanelSize = new Dimension(
                Integer.MAX_VALUE,
                bottomButtonPanelHeight);
        bottomButtonPanel.setMaximumSize(bottomButtonPanelSize);

        // 把四个主要操作按钮放进底部区域
        bottomPanel.add(bottomButtonPanel);

        // 在主要操作按钮和退出登录按钮之间添加一小段固定空隙
        bottomPanel.add(Box.createVerticalStrut(8));

        // 让退出登录按钮尽量横向占满底部区域
        int logoutButtonHeight = logoutButton.getPreferredSize().height;
        Dimension logoutButtonSize = new Dimension(
                Integer.MAX_VALUE,
                logoutButtonHeight);
        logoutButton.setMaximumSize(logoutButtonSize);

        // 把退出登录按钮放进底部区域
        bottomPanel.add(logoutButton);

        this.add(mainPanel);

        // 将窗口设置为可见，这一步一定是在最后完成。
        this.setVisible(true);
    }

    // 创建用户信息窗口，显示用户的全部基础信息
    private void showUserInformationWindow(User user) {
        if (user == null) {
            JOptionPane.showMessageDialog(this, "This user does not exist.");
            return;
        }

        // 创建用户信息窗口
        JFrame userInformationFrame = new JFrame("User Information");
        userInformationFrame.setSize(360, 260);
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
        constraints.gridy = 5;
        constraints.weightx = 1;
        userInformationPanel.add(closeButton, constraints);

        userInformationFrame.add(userInformationPanel);
        userInformationFrame.setVisible(true);
    }
}
