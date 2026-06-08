package ui;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    JPanel panel;

    // 创建登录窗口
    public LoginUI() {
        this.setTitle("Social Network Login");
        this.setSize(420, 260);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建主面板，GridBagLayout可以让组件保持比较自然的大小
        panel = new JPanel(new GridBagLayout());
        // setBorder的意思是设置panel的边框
        // BorderFactory.createEmptyBorder(...)的意思是创建一个空白边框对象
        // 四个参数的含义就是上下左右分别预留多少个像素
        panel.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        // 创建布局约束对象，用来控制组件放在第几行第几列，和GridBagLayout配套使用
        GridBagConstraints constraints = new GridBagConstraints();
        // 给每一个组件的周围添加空隙，Inserts的意思就是组件的外边距，四个参数也对应上下左右
        constraints.insets = new Insets(8, 6, 8, 6);
        // 控制自己的格子怎么进行拉伸，意思就是在水平方向填满自己的格子，不要在垂直方向拉高
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // JLabel就是一段显示文字这里创建它们的对象
        JLabel networkIdLabel = new JLabel("Network ID:");
        // 设置字体
        networkIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // 将第一行文字放进panel里面去
        // 放在第0列
        constraints.gridx = 0;
        // 方在第0行
        constraints.gridy = 0;
        // 这个组件不参与横向多余空间分配，它不需要被拉的很宽
        constraints.weightx = 0;
        // 把 networkIdLabel 放进 panel，并按照上面这些 constraints 规则摆放
        panel.add(networkIdLabel, constraints);

        // JTextField就是一个输入框
        JTextField networkIdTextField = new JTextField();
        networkIdTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        // 将第一行的文本输入框放进panel里面去
        // 将第一个输入框放在第1列
        constraints.gridx = 1;
        // 放在第0行
        constraints.gridy = 0;
        // 输入框进行水平方向的拉伸
        constraints.weightx = 1;
        panel.add(networkIdTextField, constraints);

        // 创建第二行的标签
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // 将第二行的标签放进panel里面去
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0;
        panel.add(passwordLabel, constraints);

        // 创建第四行的文本框
        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        // 把这个文本框放进panel里面去
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        panel.add(passwordTextField, constraints);

        // 创建第五行的按钮
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        // 把确认按钮添加到里面去
        constraints.gridx = 0;
        constraints.gridy = 2;
        // 这个组件横向占一列
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        panel.add(loginButton, constraints);

        // 创建取消按钮
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(cancelButton, constraints);

        this.add(panel);

        // 将窗口设置为可见，这一步一定是在最后完成。
        this.setVisible(true);
    }
}
