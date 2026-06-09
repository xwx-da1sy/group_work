package ui;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {

    // 创建主窗口
    public MainUI() {
        this.setTitle("Social Network");
        this.setSize(450, 680);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建主容器，最外层使用BorderLayout布局管理器
        JPanel mainPanel = new JPanel(new BorderLayout());
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

        // 创建当前状态标签，用来显示当前社交网络ID和当前用户
        JLabel currentUserLabel = new JLabel("Network ID: Unknown        Current User: Unknown");
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

        //添加朋友列表标签，我不想水平拉伸但是想要居中摆放
        JLabel friendsLabel = new JLabel("Friends");
        friendsLabel.setFont(new Font("Arial", Font.BOLD, 16));

        gridBagConstraintsOfCenter.gridx = 0;
        gridBagConstraintsOfCenter.gridy = 1;
        gridBagConstraintsOfCenter.weightx = 1;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 2;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.NONE;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.CENTER;
        userNetworkPanel.add(friendsLabel, gridBagConstraintsOfCenter);

        // 添加好友列表
        DefaultListModel<String> friendListModel = new DefaultListModel<>();
        friendListModel.addElement("No friends loaded yet.");

        JList<String> friendList = new JList<>(friendListModel);
        friendList.setFont(new Font("Arial", Font.PLAIN, 14));

        // 在右边添加一个滚动条
        JScrollPane friendListScrollPane = new JScrollPane(friendList);

        gridBagConstraintsOfCenter.gridx = 0;
        gridBagConstraintsOfCenter.gridy = 2;
        gridBagConstraintsOfCenter.weightx = 1;
        gridBagConstraintsOfCenter.weighty = 1;
        gridBagConstraintsOfCenter.gridwidth = 2;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.BOTH;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.CENTER;
        userNetworkPanel.add(friendListScrollPane, gridBagConstraintsOfCenter);

        // 把容器添加进中部容器中
        centerPanel.add(userNetworkPanel, BorderLayout.CENTER);

        // ---------------------------底部区域组件------------------------

        // 创建底部区域，用来放各种操作按钮
        JPanel bottomPanel = new JPanel(new BorderLayout());
        // 给底部区域添加一点上边距，让按钮不要紧贴中间列表
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 创建底部按钮区域，用GridLayout保证左右按钮一样长
        JPanel bottomButtonPanel = new JPanel(new GridLayout(2, 2, 8, 8));

        // 添加各种操作按钮
        JButton addUserButton = new JButton("Add User");
        addUserButton.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton addFriendButton = new JButton("Add Friend");
        addFriendButton.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton removeFriendButton = new JButton("Remove Friend");
        removeFriendButton.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));

        // 把添加用户按钮放进底部按钮区域
        bottomButtonPanel.add(addUserButton);

        // 把添加好友按钮放进底部按钮区域
        bottomButtonPanel.add(addFriendButton);

        // 把删除好友按钮放进底部按钮区域
        bottomButtonPanel.add(removeFriendButton);

        // 把保存按钮放进底部按钮区域
        bottomButtonPanel.add(saveButton);

        // 把四个主要操作按钮放进底部区域的中间
        bottomPanel.add(bottomButtonPanel, BorderLayout.CENTER);

        // 创建退出登录按钮区域，用来单独放退出登录按钮
        JPanel logoutPanel = new JPanel(new BorderLayout());
        // 给退出登录按钮区域添加一点上边距，避免和上面的按钮贴在一起
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        // 把退出登录按钮放进退出登录按钮区域
        logoutPanel.add(logoutButton, BorderLayout.CENTER);

        // 把退出登录按钮放在底部区域的下方
        bottomPanel.add(logoutPanel, BorderLayout.SOUTH);

        this.add(mainPanel);

        // 将窗口设置为可见，这一步一定是在最后完成。
        this.setVisible(true);
    }
}
