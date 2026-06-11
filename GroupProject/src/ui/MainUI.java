package ui;

import controller.MainController;
import model.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Displays the main social network window after login.
 */
public class MainUI extends JFrame {

    /** The controller used by the main window. */
    private MainController mainController;

    /** The panel that holds all user buttons in the network manager view. */
    private JPanel userButtonPanel;

    /** The panel that holds the current user's friend buttons. */
    private JPanel currentFriendPanel;

    /**
     * Creates and displays the main social network window.
     *
     * @param mainController the controller that owns the current network
     */
    public MainUI(MainController mainController) {
        this.mainController = mainController;

        this.setTitle("Social Network");
        this.setSize(960, 720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 12));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JPanel topPanel = new JPanel(new GridLayout(2, 1));

        JLabel titleLabel = new JLabel("Social Network");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        topPanel.add(titleLabel);

        String currentUserText = "Network ID: " + mainController.getNetworkId();
        currentUserText = currentUserText + "        Current User: ";
        currentUserText = currentUserText + mainController.getCurrentUsername();
        currentUserText = currentUserText + " (ID ";
        currentUserText = currentUserText + mainController.getCurrentUserId();
        currentUserText = currentUserText + ")";

        JLabel currentUserLabel = new JLabel(currentUserText);
        currentUserLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        topPanel.add(currentUserLabel);

        mainPanel.add(topPanel, BorderLayout.NORTH);


        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 16, 0));

        JPanel currentUserPanel = createCurrentUserPanel();

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

        contentPanel.add(currentUserPanel);

        contentPanel.add(networkManagerPanel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);


        JPanel centerPanel = new JPanel(new BorderLayout());
        networkManagerPanel.add(centerPanel, BorderLayout.CENTER);
        JPanel userNetworkPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gridBagConstraintsOfCenter = new GridBagConstraints();
        gridBagConstraintsOfCenter.insets = new Insets(0, 0, 5, 0);
        gridBagConstraintsOfCenter.fill = GridBagConstraints.BOTH;

        JLabel searchLabel = new JLabel("Search / Filter:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));

        gridBagConstraintsOfCenter.gridx = 0;
        gridBagConstraintsOfCenter.gridy = 0;
        gridBagConstraintsOfCenter.weightx = 0;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 1;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.NONE;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.WEST;
        userNetworkPanel.add(searchLabel, gridBagConstraintsOfCenter);

        JTextField searchTextField = new JTextField();
        searchTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        gridBagConstraintsOfCenter.gridx = 1;
        gridBagConstraintsOfCenter.gridy = 0;
        gridBagConstraintsOfCenter.weightx = 1;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 1;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.WEST;
        userNetworkPanel.add(searchTextField, gridBagConstraintsOfCenter);

        JComboBox<String> searchTypeComboBox = new JComboBox<>();
        searchTypeComboBox.addItem("Name");
        searchTypeComboBox.addItem("ID");
        searchTypeComboBox.addItem("Workplace");
        searchTypeComboBox.addItem("Hometown");
        searchTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        gridBagConstraintsOfCenter.gridx = 2;
        gridBagConstraintsOfCenter.gridy = 0;
        gridBagConstraintsOfCenter.weightx = 0;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 1;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.WEST;
        userNetworkPanel.add(searchTypeComboBox, gridBagConstraintsOfCenter);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.setFocusPainted(false);

        gridBagConstraintsOfCenter.gridx = 3;
        gridBagConstraintsOfCenter.gridy = 0;
        gridBagConstraintsOfCenter.weightx = 0;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 1;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.WEST;
        userNetworkPanel.add(searchButton, gridBagConstraintsOfCenter);

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 14));
        resetButton.setFocusPainted(false);

        gridBagConstraintsOfCenter.gridx = 4;
        gridBagConstraintsOfCenter.gridy = 0;
        gridBagConstraintsOfCenter.weightx = 0;
        gridBagConstraintsOfCenter.weighty = 0;
        gridBagConstraintsOfCenter.gridwidth = 1;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.WEST;
        userNetworkPanel.add(resetButton, gridBagConstraintsOfCenter);

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

        userButtonPanel = new JPanel();
        userButtonPanel.setLayout(new BoxLayout(userButtonPanel, BoxLayout.Y_AXIS));

        refreshUserButtonList(mainController.getAllUsersList());

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String keyword = searchTextField.getText();
                String filterType = (String) searchTypeComboBox.getSelectedItem();

                ArrayList<User> filteredUsers = mainController.filterUsers(keyword, filterType);
                refreshUserButtonList(filteredUsers);
            }
        });

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchTextField.setText("");
                searchTypeComboBox.setSelectedItem("Name");
                refreshUserButtonList(mainController.getAllUsersList());
            }
        });

        JScrollPane userListScrollPane = new JScrollPane(userButtonPanel);

        gridBagConstraintsOfCenter.gridx = 0;
        gridBagConstraintsOfCenter.gridy = 2;
        gridBagConstraintsOfCenter.weightx = 1;
        gridBagConstraintsOfCenter.weighty = 1;
        gridBagConstraintsOfCenter.gridwidth = 5;
        gridBagConstraintsOfCenter.fill = GridBagConstraints.BOTH;
        gridBagConstraintsOfCenter.anchor = GridBagConstraints.CENTER;
        userNetworkPanel.add(userListScrollPane, gridBagConstraintsOfCenter);

        centerPanel.add(userNetworkPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        networkManagerPanel.add(bottomPanel, BorderLayout.SOUTH);

        JPanel bottomButtonPanel = new JPanel(new GridLayout(3, 2, 8, 8));

        JButton addUserButton = new JButton("Add User");
        addUserButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addUserButton.setFocusPainted(false);

        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddUserWindow();
            }
        });

        JButton removeUserButton = new JButton("Remove User");
        removeUserButton.setFont(new Font("Arial", Font.PLAIN, 14));
        removeUserButton.setFocusPainted(false);

        removeUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRemoveUserWindow();
            }
        });

        JButton addFriendButton = new JButton("Add Friend");
        addFriendButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addFriendButton.setFocusPainted(false);

        addFriendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddFriendWindow();
            }
        });

        JButton removeFriendButton = new JButton("Remove Friend");
        removeFriendButton.setFont(new Font("Arial", Font.PLAIN, 14));
        removeFriendButton.setFocusPainted(false);

        removeFriendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRemoveFriendWindow();
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
        saveButton.setFocusPainted(false);

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

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        MainUI.this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    MainUI.this.dispose();

                    new LoginUI();
                }
            }
        });

        bottomButtonPanel.add(addUserButton);

        bottomButtonPanel.add(removeUserButton);

        bottomButtonPanel.add(addFriendButton);

        bottomButtonPanel.add(removeFriendButton);

        bottomButtonPanel.add(saveButton);

        bottomButtonPanel.add(logoutButton);

        int bottomButtonPanelHeight = bottomButtonPanel.getPreferredSize().height;
        Dimension bottomButtonPanelSize = new Dimension(
                Integer.MAX_VALUE,
                bottomButtonPanelHeight);
        bottomButtonPanel.setMaximumSize(bottomButtonPanelSize);

        bottomPanel.add(bottomButtonPanel);

        this.add(mainPanel);

        this.setVisible(true);
    }

    /**
     * Creates the current user view on the left side of the main window.
     *
     * @return the current user panel
     */
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

        JPanel currentUserTopPanel = new JPanel(new BorderLayout(0, 8));

        JPanel currentUserInformationPanel = new JPanel(new GridLayout(5, 1, 0, 6));

        User currentUser = mainController.getUserById(mainController.getCurrentUserId());

        JLabel userIdLabel = new JLabel("User ID: " + currentUser.getUserId());
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentUserInformationPanel.add(userIdLabel);

        JLabel usernameLabel = new JLabel("Username: " + currentUser.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentUserInformationPanel.add(usernameLabel);

        JLabel homeTownLabel = new JLabel("Home Town: " + currentUser.getHomeTown());
        homeTownLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentUserInformationPanel.add(homeTownLabel);

        JLabel workPlaceLabel = new JLabel("Work Place: " + currentUser.getWorkPlace());
        workPlaceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentUserInformationPanel.add(workPlaceLabel);

        JLabel friendsCountLabel = new JLabel("Friends Count: " + currentUser.getFriends().size());
        friendsCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentUserInformationPanel.add(friendsCountLabel);

        currentUserTopPanel.add(currentUserInformationPanel, BorderLayout.CENTER);

        currentUserPanel.add(currentUserTopPanel, BorderLayout.NORTH);

        currentFriendPanel = new JPanel();
        currentFriendPanel.setLayout(new BoxLayout(currentFriendPanel, BoxLayout.Y_AXIS));

        ArrayList<User> friendsList = mainController.getUserFriendsList(currentUser.getUserId());

        refreshCurrentFriendList(friendsList);

        JScrollPane currentFriendScrollPane = new JScrollPane(currentFriendPanel);

        JPanel currentFriendContainer = new JPanel(new BorderLayout(0, 8));

        JPanel friendTopPanel = new JPanel(new BorderLayout(0, 6));

        JLabel currentFriendLabel = new JLabel("My Friends");
        currentFriendLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendTopPanel.add(currentFriendLabel, BorderLayout.NORTH);

        JPanel friendFilterPanel = new JPanel(new GridBagLayout());

        GridBagConstraints friendFilterConstraints = new GridBagConstraints();
        friendFilterConstraints.insets = new Insets(0, 0, 5, 5);
        friendFilterConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel friendFilterLabel = new JLabel("Filter:");
        friendFilterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        friendFilterConstraints.gridx = 0;
        friendFilterConstraints.gridy = 0;
        friendFilterConstraints.weightx = 0;
        friendFilterConstraints.weighty = 0;
        friendFilterConstraints.gridwidth = 1;
        friendFilterPanel.add(friendFilterLabel, friendFilterConstraints);

        JTextField friendFilterTextField = new JTextField();
        friendFilterTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        friendFilterConstraints.gridx = 1;
        friendFilterConstraints.gridy = 0;
        friendFilterConstraints.weightx = 1;
        friendFilterConstraints.weighty = 0;
        friendFilterConstraints.gridwidth = 1;
        friendFilterPanel.add(friendFilterTextField, friendFilterConstraints);

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

        JButton friendSearchButton = new JButton("Search");
        friendSearchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        friendSearchButton.setFocusPainted(false);
        friendFilterConstraints.gridx = 3;
        friendFilterConstraints.gridy = 0;
        friendFilterConstraints.weightx = 0;
        friendFilterConstraints.weighty = 0;
        friendFilterConstraints.gridwidth = 1;
        friendFilterPanel.add(friendSearchButton, friendFilterConstraints);

        JButton friendResetButton = new JButton("Reset");
        friendResetButton.setFont(new Font("Arial", Font.PLAIN, 14));
        friendResetButton.setFocusPainted(false);
        friendFilterConstraints.gridx = 4;
        friendFilterConstraints.gridy = 0;
        friendFilterConstraints.weightx = 0;
        friendFilterConstraints.weighty = 0;
        friendFilterConstraints.gridwidth = 1;
        friendFilterPanel.add(friendResetButton, friendFilterConstraints);

        friendTopPanel.add(friendFilterPanel, BorderLayout.CENTER);

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

        friendResetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                friendFilterTextField.setText("");
                friendFilterComboBox.setSelectedItem("Name");
                refreshCurrentFriendList(mainController.getUserFriendsList(mainController.getCurrentUserId()));
            }
        });

        currentFriendContainer.add(friendTopPanel, BorderLayout.NORTH);

        currentFriendContainer.add(currentFriendScrollPane, BorderLayout.CENTER);

        currentUserPanel.add(currentFriendContainer, BorderLayout.CENTER);

        JPanel friendRecommendationPanel = createFriendRecommendationPanel();

        currentUserPanel.add(friendRecommendationPanel, BorderLayout.SOUTH);

        return currentUserPanel;
    }

    /**
     * Creates the friend recommendation panel for the current user.
     *
     * @return the friend recommendation panel
     */
    private JPanel createFriendRecommendationPanel() {
        JPanel friendRecommendationContainer = new JPanel(new BorderLayout(0, 8));

        JLabel friendRecommendationLabel = new JLabel("Friend Recommendations");
        friendRecommendationLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendRecommendationContainer.add(friendRecommendationLabel, BorderLayout.NORTH);

        JPanel friendRecommendationListPanel = new JPanel();
        friendRecommendationListPanel.setLayout(new BoxLayout(friendRecommendationListPanel, BoxLayout.Y_AXIS));

        ArrayList<User> recommendedFriends = mainController.getFriendRecommendationsForCurrentUser();

        for (User recommendedFriend : recommendedFriends) {
            int recommendedFriendId = recommendedFriend.getUserId();
            String recommendedFriendUsername = recommendedFriend.getUsername();

            String recommendedFriendText = "ID: " + recommendedFriendId;
            recommendedFriendText = recommendedFriendText + "    Name: " + recommendedFriendUsername;

            JPanel recommendationRowPanel = new JPanel(new BorderLayout(8, 0));

            JLabel recommendationLabel = new JLabel(recommendedFriendText);
            recommendationLabel.setFont(new Font("Arial", Font.PLAIN, 13));

            JButton addFriendButton = new JButton("Add Friend");
            addFriendButton.setFont(new Font("Arial", Font.PLAIN, 12));
            addFriendButton.setFocusPainted(false);

            addFriendButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean isAdded = mainController.addFriendToCurrentUser(recommendedFriendId);

                    if (isAdded) {
                        JOptionPane.showMessageDialog(MainUI.this, "Friend added successfully.");

                        MainUI.this.dispose();
                        new MainUI(mainController);
                    } else {
                        JOptionPane.showMessageDialog(MainUI.this, "Failed to add friend.");
                    }
                }
            });

            recommendationRowPanel.add(recommendationLabel, BorderLayout.CENTER);

            recommendationRowPanel.add(addFriendButton, BorderLayout.EAST);

            int recommendationRowHeight = recommendationRowPanel.getPreferredSize().height;
            Dimension recommendationRowSize = new Dimension(
                    Integer.MAX_VALUE,
                    recommendationRowHeight);
            recommendationRowPanel.setMaximumSize(recommendationRowSize);

            friendRecommendationListPanel.add(recommendationRowPanel);

            friendRecommendationListPanel.add(Box.createVerticalStrut(6));
        }

        if (recommendedFriends.isEmpty()) {
            JLabel emptyRecommendationLabel = new JLabel("No recommendations yet.");
            emptyRecommendationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            friendRecommendationListPanel.add(emptyRecommendationLabel);
        }

        JScrollPane friendRecommendationScrollPane = new JScrollPane(friendRecommendationListPanel);
        friendRecommendationScrollPane.setPreferredSize(new Dimension(100, 120));

        friendRecommendationContainer.add(friendRecommendationScrollPane, BorderLayout.CENTER);

        return friendRecommendationContainer;
    }

    /**
     * Refreshes the current user's friend button list.
     *
     * @param friends the friends to display
     */
    private void refreshCurrentFriendList(ArrayList<User> friends) {
        currentFriendPanel.removeAll();

        for (User friend : friends) {
            int friendId = friend.getUserId();
            String friendUsername = friend.getUsername();

            String friendButtonText = "ID: " + friendId;
            friendButtonText = friendButtonText + "    Name: " + friendUsername;

            JButton friendButton = new JButton(friendButtonText);
            friendButton.setFont(new Font("Arial", Font.PLAIN, 13));
            friendButton.setFocusPainted(false);

            friendButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    User selectedFriend = mainController.getUserById(friendId);
                    showUserInformationWindow(selectedFriend);
                }
            });

            int friendButtonHeight = friendButton.getPreferredSize().height;
            Dimension friendButtonSize = new Dimension(
                    Integer.MAX_VALUE,
                    friendButtonHeight);
            friendButton.setMaximumSize(friendButtonSize);

            currentFriendPanel.add(friendButton);

            currentFriendPanel.add(Box.createVerticalStrut(6));
        }

        if (friends.isEmpty()) {
            JLabel emptyFriendLabel = new JLabel("No friends found.");
            emptyFriendLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            currentFriendPanel.add(emptyFriendLabel);
        }

        currentFriendPanel.revalidate();
        currentFriendPanel.repaint();
    }

    /**
     * Refreshes the all-user button list in the network manager view.
     *
     * @param users the users to display
     */
    private void refreshUserButtonList(ArrayList<User> users) {
        userButtonPanel.removeAll();

        for (User user : users) {
            int userId = user.getUserId();
            String username = user.getUsername();

            String userButtonText = "ID: " + userId;
            userButtonText = userButtonText + "    Name: " + username;

            JButton userButton = new JButton(userButtonText);
            userButton.setFont(new Font("Arial", Font.PLAIN, 13));
            userButton.setFocusPainted(false);

            userButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    User selectedUser = mainController.getUserById(userId);
                    showUserInformationWindow(selectedUser);
                }
            });

            int userButtonHeight = userButton.getPreferredSize().height;
            Dimension userButtonSize = new Dimension(
                    Integer.MAX_VALUE,
                    userButtonHeight);
            userButton.setMaximumSize(userButtonSize);

            userButtonPanel.add(userButton);

            userButtonPanel.add(Box.createVerticalStrut(6));
        }

        if (users.isEmpty()) {
            JLabel emptyUserLabel = new JLabel("No users found.");
            emptyUserLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            userButtonPanel.add(emptyUserLabel);
        }

        userButtonPanel.revalidate();
        userButtonPanel.repaint();
    }

    /**
     * Opens the add-user window.
     */
    private void showAddUserWindow() {
        JFrame addUserFrame = new JFrame("Add User");
        addUserFrame.setSize(420, 330);
        addUserFrame.setLocationRelativeTo(this);
        addUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel addUserPanel = new JPanel(new GridBagLayout());
        addUserPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        addUserPanel.add(usernameLabel, constraints);

        JTextField usernameTextField = new JTextField();
        usernameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addUserPanel.add(usernameTextField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        addUserPanel.add(passwordLabel, constraints);

        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addUserPanel.add(passwordTextField, constraints);

        JLabel homeTownLabel = new JLabel("Home Town:");
        homeTownLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        addUserPanel.add(homeTownLabel, constraints);

        JTextField homeTownTextField = new JTextField();
        homeTownTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addUserPanel.add(homeTownTextField, constraints);

        JLabel workPlaceLabel = new JLabel("Work Place:");
        workPlaceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        addUserPanel.add(workPlaceLabel, constraints);

        JTextField workPlaceTextField = new JTextField();
        workPlaceTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addUserPanel.add(workPlaceTextField, constraints);

        JButton confirmButton = new JButton("Add User");
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addUserPanel.add(confirmButton, constraints);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setFocusPainted(false);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addUserPanel.add(cancelButton, constraints);

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

                addUserFrame.dispose();

                MainUI.this.dispose();
                new MainUI(mainController);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUserFrame.dispose();
            }
        });

        addUserFrame.add(addUserPanel);
        addUserFrame.setVisible(true);
    }

    /**
     * Opens the remove-user window.
     */
    private void showRemoveUserWindow() {
        JFrame removeUserFrame = new JFrame("Remove User");
        removeUserFrame.setSize(380, 190);
        removeUserFrame.setLocationRelativeTo(this);
        removeUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel removeUserPanel = new JPanel(new GridBagLayout());
        removeUserPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        removeUserPanel.add(userIdLabel, constraints);

        JTextField userIdTextField = new JTextField();
        userIdTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        removeUserPanel.add(userIdTextField, constraints);

        JButton confirmButton = new JButton("Remove User");
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        removeUserPanel.add(confirmButton, constraints);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setFocusPainted(false);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        removeUserPanel.add(cancelButton, constraints);

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int targetUserId = Integer.parseInt(userIdTextField.getText());
                    boolean isRemoved = mainController.removeUserFromNetwork(targetUserId);

                    if (isRemoved) {
                        JOptionPane.showMessageDialog(removeUserFrame, "User removed successfully.");
                        removeUserFrame.dispose();

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

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeUserFrame.dispose();
            }
        });

        removeUserFrame.add(removeUserPanel);
        removeUserFrame.setVisible(true);
    }

    /**
     * Opens the add-friend window.
     */
    private void showAddFriendWindow() {
        JFrame addFriendFrame = new JFrame("Add Friend");
        addFriendFrame.setSize(360, 190);
        addFriendFrame.setLocationRelativeTo(this);
        addFriendFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel addFriendPanel = new JPanel(new GridBagLayout());
        addFriendPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        addFriendPanel.add(userIdLabel, constraints);

        JTextField userIdTextField = new JTextField();
        userIdTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addFriendPanel.add(userIdTextField, constraints);

        JButton confirmButton = new JButton("Add Friend");
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addFriendPanel.add(confirmButton, constraints);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setFocusPainted(false);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        addFriendPanel.add(cancelButton, constraints);

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

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addFriendFrame.dispose();
            }
        });

        addFriendFrame.add(addFriendPanel);
        addFriendFrame.setVisible(true);
    }

    /**
     * Opens the remove-friend window.
     */
    private void showRemoveFriendWindow() {
        JFrame removeFriendFrame = new JFrame("Remove Friend");
        removeFriendFrame.setSize(380, 190);
        removeFriendFrame.setLocationRelativeTo(this);
        removeFriendFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel removeFriendPanel = new JPanel(new GridBagLayout());
        removeFriendPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        removeFriendPanel.add(userIdLabel, constraints);

        JTextField userIdTextField = new JTextField();
        userIdTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        removeFriendPanel.add(userIdTextField, constraints);

        JButton confirmButton = new JButton("Remove Friend");
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        removeFriendPanel.add(confirmButton, constraints);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setFocusPainted(false);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        removeFriendPanel.add(cancelButton, constraints);

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

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeFriendFrame.dispose();
            }
        });

        removeFriendFrame.add(removeFriendPanel);
        removeFriendFrame.setVisible(true);
    }

    /**
     * Opens a user information window for the selected user.
     *
     * @param user the selected user
     */
    private void showUserInformationWindow(User user) {
        if (user == null) {
            JOptionPane.showMessageDialog(this, "This user does not exist.");
            return;
        }

        JFrame userInformationFrame = new JFrame("User Information");
        userInformationFrame.setSize(460, 620);
        userInformationFrame.setLocationRelativeTo(this);
        userInformationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel userInformationPanel = new JPanel(new GridBagLayout());
        userInformationPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel userIdLabel = new JLabel("User ID: " + user.getUserId());
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        userInformationPanel.add(userIdLabel, constraints);

        JLabel usernameLabel = new JLabel("Username: " + user.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        userInformationPanel.add(usernameLabel, constraints);

        JLabel homeTownLabel = new JLabel("Home Town: " + user.getHomeTown());
        homeTownLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 1;
        userInformationPanel.add(homeTownLabel, constraints);

        JLabel workPlaceLabel = new JLabel("Work Place: " + user.getWorkPlace());
        workPlaceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 1;
        userInformationPanel.add(workPlaceLabel, constraints);

        JLabel friendsCountLabel = new JLabel("Friends Count: " + user.getFriends().size());
        friendsCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weightx = 1;
        userInformationPanel.add(friendsCountLabel, constraints);

        JLabel friendsLabel = new JLabel("Friends:");
        friendsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        userInformationPanel.add(friendsLabel, constraints);

        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BoxLayout(friendPanel, BoxLayout.Y_AXIS));

        ArrayList<User> friendsList = mainController.getUserFriendsList(user.getUserId());

        for (User friend : friendsList) {
            int friendId = friend.getUserId();
            String friendUsername = friend.getUsername();

            String friendText = "ID: " + friendId;
            friendText = friendText + "    Name: " + friendUsername;

            JPanel friendRowPanel = new JPanel(new BorderLayout(8, 0));

            JLabel friendLabel = new JLabel(friendText);
            friendLabel.setFont(new Font("Arial", Font.PLAIN, 13));

            JButton addFriendButton = new JButton("Add Friend");
            addFriendButton.setFont(new Font("Arial", Font.PLAIN, 12));
            addFriendButton.setFocusPainted(false);

            addFriendButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mainController.addFriendToCurrentUser(friendId);
                }
            });

            friendRowPanel.add(friendLabel, BorderLayout.CENTER);

            friendRowPanel.add(addFriendButton, BorderLayout.EAST);

            int friendRowPanelHeight = friendRowPanel.getPreferredSize().height;
            Dimension friendRowPanelSize = new Dimension(
                    Integer.MAX_VALUE,
                    friendRowPanelHeight);
            friendRowPanel.setMaximumSize(friendRowPanelSize);

            friendPanel.add(friendRowPanel);

            friendPanel.add(Box.createVerticalStrut(6));
        }

        if (friendsList.isEmpty()) {
            JLabel emptyFriendLabel = new JLabel("No friends yet.");
            emptyFriendLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            friendPanel.add(emptyFriendLabel);
        }

        JScrollPane friendListScrollPane = new JScrollPane(friendPanel);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        userInformationPanel.add(friendListScrollPane, constraints);

        int closeButtonGridY = 7;

        if (user.getUserId() != mainController.getCurrentUserId()) {
            JLabel commonFriendsLabel = new JLabel("Common Friends with Current User:");
            commonFriendsLabel.setFont(new Font("Arial", Font.BOLD, 14));
            constraints.gridx = 0;
            constraints.gridy = 7;
            constraints.weightx = 1;
            constraints.weighty = 0;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            userInformationPanel.add(commonFriendsLabel, constraints);

            JPanel commonFriendPanel = new JPanel();
            commonFriendPanel.setLayout(new BoxLayout(commonFriendPanel, BoxLayout.Y_AXIS));

            ArrayList<User> commonFriends = mainController.getCommonFriendsWithCurrentUser(user.getUserId());

            for (User commonFriend : commonFriends) {
                int commonFriendId = commonFriend.getUserId();
                String commonFriendUsername = commonFriend.getUsername();

                String commonFriendText = "ID: " + commonFriendId;
                commonFriendText = commonFriendText + "    Name: " + commonFriendUsername;

                JLabel commonFriendInformationLabel = new JLabel(commonFriendText);
                commonFriendInformationLabel.setFont(new Font("Arial", Font.PLAIN, 13));

                int commonFriendLabelHeight = commonFriendInformationLabel.getPreferredSize().height;
                Dimension commonFriendLabelSize = new Dimension(
                        Integer.MAX_VALUE,
                        commonFriendLabelHeight);
                commonFriendInformationLabel.setMaximumSize(commonFriendLabelSize);

                commonFriendPanel.add(commonFriendInformationLabel);

                commonFriendPanel.add(Box.createVerticalStrut(6));
            }

            if (commonFriends.isEmpty()) {
                JLabel emptyCommonFriendLabel = new JLabel("No common friends.");
                emptyCommonFriendLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                commonFriendPanel.add(emptyCommonFriendLabel);
            }

            JScrollPane commonFriendScrollPane = new JScrollPane(commonFriendPanel);

            constraints.gridx = 0;
            constraints.gridy = 8;
            constraints.weightx = 1;
            constraints.weighty = 1;
            constraints.fill = GridBagConstraints.BOTH;
            userInformationPanel.add(commonFriendScrollPane, constraints);

            closeButtonGridY = 9;
        }

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.setFocusPainted(false);

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userInformationFrame.dispose();
            }
        });

        constraints.gridx = 0;
        constraints.gridy = closeButtonGridY;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        userInformationPanel.add(closeButton, constraints);

        userInformationFrame.add(userInformationPanel);
        userInformationFrame.setVisible(true);
    }
}
