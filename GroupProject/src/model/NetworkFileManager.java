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
//      NETWORK_ID
//      保存当前社交网络 ID
//
//      CURRENT_USER
//      保存当前用户 userId
//
//      USERS
//      userId|username|password|homeTown|workPlace
//
//      FRIENDSHIPS
//      userId1|userId2

public class NetworkFileManager {
    // 保存文件的路径，我们一般选择相对路径
    private String filePath;

    // 一些保存文本的格式，保存必须去遵循的一些规则
    private static final String PROJECT_FOLDER = "GroupProject";
    private static final String DATA_FOLDER = "data";
    private static final String NETWORK_FILE_PREFIX = "network-";
    private static final String FILE_EXTENSION = ".txt";
    private static final String DEFAULT_FILE_PATH = buildNetworkFilePath(0);
    private static final String NETWORK_ID_SECTION = "NETWORK_ID";
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

    // 根据社交网络 ID 生成对应的文件路径
    public static String buildNetworkFilePath(long networkId) {
        return getDataFolderPath() + File.separator + NETWORK_FILE_PREFIX + networkId + FILE_EXTENSION;
    }

    // 获取真正用来保存社交网络文件的data文件夹路径
    private static String getDataFolderPath() {
        File projectDataFolder = new File(PROJECT_FOLDER + File.separator + DATA_FOLDER);

        if (projectDataFolder.exists()) {
            return projectDataFolder.getPath();
        }

        return DATA_FOLDER;
    }

    // 根据社交网络 ID 读取对应的社交网络文件
    public Network loadNetwork(long networkId) {
        filePath = buildNetworkFilePath(networkId);
        return loadNetwork();
    }

    // 从文件中读取社交网络
    public Network loadNetwork() {
        // 用来保存从文件中恢复出来的整个社交网络
        Network network = new Network();

        // 用来暂时保存文件中记录的社交网络 ID
        long networkId = -1;

        // 用来暂时保存文件中记录的当前用户 ID
        int currentUserId = -1;

        // 用来保存每一次从文件中读取到的一行文本
        String line = null;

        // 用来记录当前读取到了文件中的哪一部分
        String currentSection = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // 读取文件中的第一行
            line = reader.readLine();

            // 确认第一部分是社交网络 ID 信息
            if (NETWORK_ID_SECTION.equals(line)) {
                // 读取当前社交网络的 ID
                networkId = Long.parseLong(reader.readLine());
                network.setNetworkId(networkId);
            }

            // 读取下一行，确认第二部分是当前用户信息
            line = reader.readLine();
            if (CURRENT_USER_SECTION.equals(line)) {
                // 读取当前用户的 userId
                currentUserId = Integer.parseInt(reader.readLine());
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

                    int userId = Integer.parseInt(userInformation[0]);
                    String username = userInformation[1];
                    String password = userInformation[2];
                    String homeTown = userInformation[3];
                    String workPlace = userInformation[4];

                    User user = new User(username, password, homeTown, workPlace, userId);
                    network.addUser(user);
                }

                // 根据当前部分读取好友关系
                if (FRIENDSHIPS_SECTION.equals(currentSection)) {
                    String[] friendshipInformation = line.split(READ_SEPARATOR);

                    int userId1 = Integer.parseInt(friendshipInformation[0]);
                    int userId2 = Integer.parseInt(friendshipInformation[1]);

                    network.addEachOther(userId1, userId2);
                }
            }

            // 根据文件中保存的 userId 恢复当前用户
            if (currentUserId != -1) {
                network.setCurrentUserById(currentUserId);
            }
        } catch (IOException e) {
            System.out.println("Failed to read network file.");
        }

        return network;
    }

    // 把社交网络保存到文件中
    public void saveNetwork(Network network) {
        filePath = buildNetworkFilePath(network.getNetworkId());
        createParentFolder();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // 写入社交网络 ID 部分
            writer.println(NETWORK_ID_SECTION);
            writer.println(network.getNetworkId());

            // 写入当前用户部分
            writer.println();
            writer.println(CURRENT_USER_SECTION);
            if (network.getCurrentUser() != null) {
                writer.println(network.getCurrentUser().getUserId());
            }

            // 写入用户信息部分
            writer.println();
            writer.println(USERS_SECTION);
            for (User user : network.getAllUsers()) {
                writer.println(user.getUserId()
                        + WRITE_SEPARATOR + user.getUsername()
                        + WRITE_SEPARATOR + user.getPassword()
                        + WRITE_SEPARATOR + user.getHomeTown()
                        + WRITE_SEPARATOR + user.getWorkPlace());
            }

            // 写入好友关系部分
            writer.println();
            writer.println(FRIENDSHIPS_SECTION);
            for (User user : network.getAllUsers()) {
                for (int friendId : user.getFriends()) {
                    // 双向好友关系只保存一次，避免 0|1 和 1|0 同时出现
                    if (user.getUserId() < friendId) {
                        writer.println(user.getUserId() + WRITE_SEPARATOR + friendId);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to save network file.");
        }

    }

    // 根据社交网络 ID 把社交网络保存到对应的文件中
    public void saveNetwork(Network network, long networkId) {
        network.setNetworkId(networkId);
        filePath = buildNetworkFilePath(networkId);
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
