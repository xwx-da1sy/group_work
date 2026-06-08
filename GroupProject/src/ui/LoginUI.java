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

        // 采用最为简单的棋盘布局，7行，2列
        panel = new JPanel(new GridLayout(7, 2));

        // JLabel就是一段显示文字这里创建它们的对象
        JLabel networkNameLabel = new JLabel("Network Name:");
        // JTextField就是一个输入框
        JTextField networkNameTextField = new JTextField();
        // 将第一行的文字和第一行的文本输入框放进panel中去
        panel.add(networkNameLabel);
        panel.add(networkNameTextField);

        this.add(panel);

        // 将窗口设置为可见，这一步一定是在最后完成。
        this.setVisible(true);
    }
}
