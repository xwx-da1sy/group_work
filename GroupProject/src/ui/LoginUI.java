package ui;

import controller.BeginController;
import model.Network;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    JPanel panel;
    private BeginController beginController;

    // 创建登录窗口
    public LoginUI() {
        beginController = new BeginController();

        this.setTitle("Social Network Login");
        this.setSize(420, 310);
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
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // 将第二行的标签放进panel里面去
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0;
        panel.add(userIdLabel, constraints);

        // 创建第二行的文本框
        JTextField userIdTextField = new JTextField();
        userIdTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        // 把用户ID文本框放进panel里面去
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        panel.add(userIdTextField, constraints);

        // 创建第三行的标签
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // 将第三行的标签放进panel里面去
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0;
        panel.add(passwordLabel, constraints);

        // 创建第三行的文本框
        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        // 把这个文本框放进panel里面去
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 1;
        panel.add(passwordTextField, constraints);

        // 创建第四行的按钮
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        // 把确认按钮添加到里面去
        constraints.gridx = 0;
        constraints.gridy = 3;
        // 这个组件横向占一列
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        panel.add(loginButton, constraints);

        // 创建取消按钮
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(cancelButton, constraints);

        // 创建注册新社交网络的按钮
        JButton registerNetworkButton = new JButton("Register New Network");
        registerNetworkButton.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 4;
        // 这个按钮横向占两列
        constraints.gridwidth = 2;
        constraints.weightx = 1;
        panel.add(registerNetworkButton, constraints);

        // 给登录按钮绑定点击事件
        loginButton.addActionListener(e -> {
            try {
                // 从社交网络ID文本框中获取社交网络ID输入
                String networkId = networkIdTextField.getText();
                // 从用户ID文本框中获取用户ID输入
                int userId = Integer.parseInt(userIdTextField.getText());
                // 从密码文本框中获取密码输入
                String password = new String(passwordTextField.getPassword());

                Network network = beginController.handleLogin(networkId, userId, password);

                if (network == null) {
                    JOptionPane.showMessageDialog(this, "Login failed.");
                    return;
                }

                JOptionPane.showMessageDialog(this, "Login successfully.");

                // 登录成功之后打开主窗口
                new MainUI();

                // 关闭登录界面，彻底摧毁窗口释放占用的资源
                this.dispose();
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "User ID must be a number.");
            }
        });

        // 给取消按钮绑定点击事件
        cancelButton.addActionListener(e -> this.dispose());

        // 给注册新社交网络按钮绑定点击事件
        registerNetworkButton.addActionListener(e -> registerNewNetwork());

        this.add(panel);

        // 将窗口设置为可见，这一步一定是在最后完成。
        this.setVisible(true);
    }

    // 注册一个新的社交网络
    private void registerNewNetwork() {
        // 创建一个注册新社交网络和新用户的界面
        JFrame registerNetworkFrame = new JFrame("New Network");

        // 设置这个登录界面的一些基础设置，大小你去自己设定，放在屏幕的中央，关闭时自动关闭等等
        registerNetworkFrame.setSize(460, 380);
        registerNetworkFrame.setLocationRelativeTo(this);
        registerNetworkFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 给新的界面添加一个容器使用GridBagLayout布局
        JPanel networkPanel = new JPanel(new GridBagLayout());

        // 调用setBorder方法设置边框大小
        networkPanel.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        // 创建布局约束对象，用来控制组件放在第几行第几列，和GridBagLayout配套使用
        GridBagConstraints constraints = new GridBagConstraints();
        // 给每一个组件的周围添加空隙，Inserts的意思就是组件的外边距，四个参数也对应上下左右
        constraints.insets = new Insets(8, 6, 8, 6);
        // 控制自己的格子怎么进行拉伸，意思就是在水平方向填满自己的格子，不要在垂直方向拉高
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // 社交网络ID有关组件摆放
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

        // 当前用户名称有关组件摆放
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

        // 当前用户密码有关组件摆放
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

        // 让用户再次输入一次密码的组件摆放（先不去处理逻辑和事件，把按钮摆上去就行了）
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

        // 当前用户家乡有关组件摆放
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

        // 当前用户工作地点有关组件摆放
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

        // 确认按钮有关组件摆放
        JButton confirmButton = new JButton("Register");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        networkPanel.add(confirmButton, constraints);

        // 取消按钮有关组件摆放
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        networkPanel.add(cancelButton, constraints);

        // 给确认按钮绑定点击事件
        confirmButton.addActionListener(e -> {
            // 从用户名文本框中获取用户名输入
            String username = usernameTextField.getText();
            // 从密码文本框中获取密码输入
            String password = new String(passwordTextField.getPassword());
            // 从确认密码文本框中获取确认密码输入
            String confirmPassword = new String(confirmPasswordTextField.getPassword());
            // 从家乡文本框中获取家乡输入
            String homeTown = homeTownTextField.getText();
            // 获取工作地点
            String workPlace = workPlaceTextField.getText();

            // 如果两次输入的密码不统一，则弹出来一个弹框表示两次密码不一致
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(registerNetworkFrame, "The two passwords are different.");
                return;
            }

            // 一切正常，在控制器中创建社交网络对象
            Network newNetwork = beginController.registerNewNetwork(username, password, homeTown, workPlace);

            // 如果创建失败，弹出来一个弹框表示用户信息不能为空
            if (newNetwork == null) {
                JOptionPane.showMessageDialog(registerNetworkFrame, "User information cannot be empty.");
                return;
            }

            // 弹出来一个文本框展示社交网络的有关信息
            JOptionPane.showMessageDialog(registerNetworkFrame,
                    "New network registered successfully.\n"
                            + "Network ID: " + newNetwork.getNetworkId() + "\n"
                            + "First User ID: 0\n"
                            + "File: " + beginController.getFilePath());

            // 注册成功之后打开主窗口
            new MainUI();

            // 关闭注册新社交网络的界面，彻底摧毁窗口释放占用的资源
            registerNetworkFrame.dispose();

            // 关闭登录界面，彻底摧毁窗口释放占用的资源
            LoginUI.this.dispose();
        });

        // 给取消按钮绑定点击事件
        cancelButton.addActionListener(e -> registerNetworkFrame.dispose());

        registerNetworkFrame.add(networkPanel);
        registerNetworkFrame.setVisible(true);
    }
}
