package ui;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    JPanel  panel;

    // 创建登录窗口
    public LoginUI() {
        this.setTitle("Social Network Login");
        this.setSize(520, 360);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        // 采用最为简单的棋盘布局，7行，2列
        panel = new JPanel(new GridLayout(7, 2));

        panel.add(new JLabel("Network Name:"));
        panel.add(new JTextField());

        panel.add(new JLabel("Username:"));
        panel.add(new JTextField());

        panel.add(new JLabel("Password:"));
        panel.add(new JPasswordField());

        panel.add(new JLabel("HomeTown:"));
        panel.add(new JTextField());

        panel.add(new JLabel("WorkPlace:"));
        panel.add(new JTextField());

        panel.add(new JButton("Load Network"));
        panel.add(new JButton("Create Network"));

        panel.add(new JButton("Register User"));
        panel.add(new JButton("Login"));

        this.add(panel);
    }
}
