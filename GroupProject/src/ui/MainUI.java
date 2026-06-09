package ui;

import javax.swing.*;

public class MainUI extends JFrame {

    // 创建主窗口
    public MainUI() {
        this.setTitle("Social Network");
        this.setSize(720, 520);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 将窗口设置为可见，这一步一定是在最后完成。
        this.setVisible(true);
    }
}
