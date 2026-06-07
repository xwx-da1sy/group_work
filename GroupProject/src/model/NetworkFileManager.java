package model;

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

    // 从文件中读取社交网络
    public Network loadNetwork() {
        return null;
    }

    // 把社交网络保存到文件中
    public void saveNetwork(Network network) {

    }
}
