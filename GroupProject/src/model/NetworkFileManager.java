package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// 这个类的作用是管理文件的读写，提供一些方法来保存和读取用户数据、好友关系等信息
// 文本大体格式如下：
//
//      CURRENT_USER
//      保存当前用户 username
//
//      USERS
//      username|password|userId|homeTown|workPlace
//
//      FRIENDSHIPS
//      username1|username2

public class NetworkFileManager {
    // 保存文件的路径，我们一般选择相对路径
    private String filePath;

    // 一些保存文本的格式，保存必须去遵循的一些规则
    private static final String DATA_FOLDER = "data";
    private static final String NETWORK_FILE_PREFIX = "network-";
    private static final String FILE_EXTENSION = ".txt";
    private static final String DEFAULT_FILE_PATH = "data/network-data.txt";
    private static final String CURRENT_USER_SECTION = "CURRENT_USER";
    private static final String USERS_SECTION = "USERS";
    private static final String FRIENDSHIPS_SECTION = "FRIENDSHIPS";
    private static final String WRITE_SEPARATOR = "|";
    private static final String READ_SEPARATOR = "\\|";

    // 使用默认文件路径创建文件管理器
    public NetworkFileManager() {
        filePath = DEFAULT_FILE_PATH;
    }

    // 使用指定文件路径创建文件管理器
    public NetworkFileManager(String filePath) {
        this.filePath = filePath;
    }

    // 获取当前文件管理器正在使用的文件路径
    public String getFilePath() {
        return filePath;
    }

    // 修改当前文件管理器正在使用的文件路径
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // 根据社交网络名字生成对应的文件路径
    public static String buildNetworkFilePath(String networkName) {
        String safeNetworkName = networkName.trim().replace(" ", "-");

        if (safeNetworkName.endsWith(FILE_EXTENSION)) {
            safeNetworkName = safeNetworkName.substring(0, safeNetworkName.length() - FILE_EXTENSION.length());
        }

        if (!safeNetworkName.startsWith(NETWORK_FILE_PREFIX)) {
            safeNetworkName = NETWORK_FILE_PREFIX + safeNetworkName;
        }

        return DATA_FOLDER + "/" + safeNetworkName + FILE_EXTENSION;
    }

    // 根据社交网络名字读取对应的社交网络文件
    public Network loadNetwork(String networkName) {
        filePath = buildNetworkFilePath(networkName);
        return loadNetwork();
    }

    // 从文件中读取社交网络
    public Network loadNetwork() {
        // 用来保存从文件中恢复出来的整个社交网络
        Network network = new Network();

        // 用来暂时保存文件中记录的当前用户 username
        String currentUsername = null;

        // 用来保存每一次从文件中读取到的一行文本
        String line = null;

        // 用来记录当前读取到了文件中的哪一部分
        String currentSection = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // 读取文件中的第一行
            line = reader.readLine();

            // 确认第一部分是当前用户信息
            if (CURRENT_USER_SECTION.equals(line)) {
                // 读取当前用户的 username
                currentUsername = reader.readLine();
            }

            // 继续一行一行读取文件，直到读完最后一行
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // 如果读到空行，就跳过这一行
                if (line.isEmpty()) {
                    continue;
                }

                // 如果读到 USERS，就说明接下来要读取用户信息
                if (USERS_SECTION.equals(line)) {
                    currentSection = USERS_SECTION;
                    continue;
                }

                // 如果读到 FRIENDSHIPS，就说明接下来要读取好友关系
                if (FRIENDSHIPS_SECTION.equals(line)) {
                    currentSection = FRIENDSHIPS_SECTION;
                    continue;
                }

                // 根据当前部分读取用户信息
                if (USERS_SECTION.equals(currentSection)) {
                    String[] userInformation = line.split(READ_SEPARATOR);

                    String username = userInformation[0];
                    String password = userInformation[1];
                    int userId = Integer.parseInt(userInformation[2]);
                    String homeTown = userInformation[3];
                    String workPlace = userInformation[4];

                    User user = new User(username, password, homeTown, workPlace, userId);
                    network.addUser(user);
                }

                // 根据当前部分读取好友关系
                if (FRIENDSHIPS_SECTION.equals(currentSection)) {
                    String[] friendshipInformation = line.split(READ_SEPARATOR);

                    String username1 = friendshipInformation[0];
                    String username2 = friendshipInformation[1];

                    network.addEachOther(username1, username2, null, null);
                }
            }

            // 根据文件中保存的 username 恢复当前用户
            if (currentUsername != null) {
                network.setCurrentUser(network.getUser(currentUsername));
            }
        } catch (IOException e) {
            System.out.println("Failed to read network file.");
        }

        return network;
    }

    // 把社交网络保存到文件中
    public void saveNetwork(Network network) {
        createParentFolder();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // 写入当前用户部分
            writer.println(CURRENT_USER_SECTION);
            if (network.getCurrentUser() != null) {
                writer.println(network.getCurrentUser().getUsername());
            }

            // 写入用户信息部分
            writer.println();
            writer.println(USERS_SECTION);
            for (User user : network.getAllUsers()) {
                writer.println(user.getUsername()
                        + WRITE_SEPARATOR + user.getPassword()
                        + WRITE_SEPARATOR + user.getUserId()
                        + WRITE_SEPARATOR + user.getHomeTown()
                        + WRITE_SEPARATOR + user.getWorkPlace());
            }

            // 写入好友关系部分
            writer.println();
            writer.println(FRIENDSHIPS_SECTION);
            for (User user : network.getAllUsers()) {
                for (String friendUsername : user.getFriends()) {
                    writer.println(user.getUsername() + WRITE_SEPARATOR + friendUsername);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to save network file.");
        }

    }

    // 根据社交网络名字把社交网络保存到对应的文件中
    public void saveNetwork(Network network, String networkName) {
        filePath = buildNetworkFilePath(networkName);
        saveNetwork(network);
    }

    // 如果保存路径中的文件夹不存在，就先创建文件夹
    private void createParentFolder() {
        File file = new File(filePath);
        File parentFolder = file.getParentFile();

        if (parentFolder != null && !parentFolder.exists()) {
            parentFolder.mkdirs();
        }
    }
}
