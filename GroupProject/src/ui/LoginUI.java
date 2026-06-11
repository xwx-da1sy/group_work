package ui;

import controller.BeginController;
import controller.MainController;
import model.Network;

import javax.swing.*;
import java.awt.*;

/**
 * Displays the login window and the new network registration window.
 */
public class LoginUI extends JFrame {

    /** The main panel used by the login window. */
    JPanel panel;

    /** The controller used by the login and registration window. */
    private BeginController beginController;

    /**
     * Creates and displays the login window.
     */
    public LoginUI() {
        beginController = new BeginController();

        this.setTitle("Social Network Login");
        this.setSize(420, 310);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 6, 8, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel networkIdLabel = new JLabel("Network ID:");
        networkIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        panel.add(networkIdLabel, constraints);

        JTextField networkIdTextField = new JTextField();
        networkIdTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        panel.add(networkIdTextField, constraints);

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0;
        panel.add(userIdLabel, constraints);

        JTextField userIdTextField = new JTextField();
        userIdTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        panel.add(userIdTextField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0;
        panel.add(passwordLabel, constraints);

        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 1;
        panel.add(passwordTextField, constraints);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        panel.add(loginButton, constraints);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(cancelButton, constraints);

        JButton registerNetworkButton = new JButton("Register New Network");
        registerNetworkButton.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.weightx = 1;
        panel.add(registerNetworkButton, constraints);

        loginButton.addActionListener(e -> {
            try {
                String networkId = networkIdTextField.getText();
                int userId = Integer.parseInt(userIdTextField.getText());
                String password = new String(passwordTextField.getPassword());

                Network network = beginController.handleLogin(networkId, userId, password);

                if (network == null) {
                    JOptionPane.showMessageDialog(this, "Login failed.");
                    return;
                }

                JOptionPane.showMessageDialog(this, "Login successfully.");

                MainController mainController = new MainController(network);
                new MainUI(mainController);

                this.dispose();
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "User ID must be a number.");
            }
        });

        cancelButton.addActionListener(e -> this.dispose());

        registerNetworkButton.addActionListener(e -> registerNewNetwork());

        this.add(panel);

        this.setVisible(true);
    }

    /**
     * Opens a window for registering a new social network and its first user.
     */
    private void registerNewNetwork() {
        JFrame registerNetworkFrame = new JFrame("New Network");

        registerNetworkFrame.setSize(460, 380);
        registerNetworkFrame.setLocationRelativeTo(this);
        registerNetworkFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel networkPanel = new JPanel(new GridBagLayout());

        networkPanel.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 6, 8, 6);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel networkIdLabel = new JLabel("Network ID:");
        networkIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        networkPanel.add(networkIdLabel, constraints);

        JTextField networkIdTextField = new JTextField("Automatically Generated");
        networkIdTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        networkIdTextField.setEditable(false);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        networkPanel.add(networkIdTextField, constraints);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        networkPanel.add(usernameLabel, constraints);

        JTextField usernameTextField = new JTextField();
        usernameTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        networkPanel.add(usernameTextField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        networkPanel.add(passwordLabel, constraints);

        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        networkPanel.add(passwordTextField, constraints);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        networkPanel.add(confirmPasswordLabel, constraints);

        JPasswordField confirmPasswordTextField = new JPasswordField();
        confirmPasswordTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        networkPanel.add(confirmPasswordTextField, constraints);

        JLabel homeTownLabel = new JLabel("Home Town:");
        homeTownLabel.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        networkPanel.add(homeTownLabel, constraints);

        JTextField homeTownTextField = new JTextField();
        homeTownTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        networkPanel.add(homeTownTextField, constraints);

        JLabel workPlaceLabel = new JLabel("Work Place:");
        workPlaceLabel.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        networkPanel.add(workPlaceLabel, constraints);

        JTextField workPlaceTextField = new JTextField();
        workPlaceTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        networkPanel.add(workPlaceTextField, constraints);

        JButton confirmButton = new JButton("Register");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        networkPanel.add(confirmButton, constraints);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        networkPanel.add(cancelButton, constraints);

        confirmButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = new String(passwordTextField.getPassword());
            String confirmPassword = new String(confirmPasswordTextField.getPassword());
            String homeTown = homeTownTextField.getText();
            String workPlace = workPlaceTextField.getText();

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(registerNetworkFrame, "The two passwords are different.");
                return;
            }

            Network newNetwork = beginController.registerNewNetwork(username, password, homeTown, workPlace);

            if (newNetwork == null) {
                JOptionPane.showMessageDialog(registerNetworkFrame, "User information cannot be empty.");
                return;
            }

            JOptionPane.showMessageDialog(registerNetworkFrame,
                    "New network registered successfully.\n"
                            + "Network ID: " + newNetwork.getNetworkId() + "\n"
                            + "First User ID: 0\n"
                            + "File: " + beginController.getFilePath());

            MainController mainController = new MainController(newNetwork);
            new MainUI(mainController);

            registerNetworkFrame.dispose();

            LoginUI.this.dispose();
        });

        cancelButton.addActionListener(e -> registerNetworkFrame.dispose());

        registerNetworkFrame.add(networkPanel);
        registerNetworkFrame.setVisible(true);
    }
}
