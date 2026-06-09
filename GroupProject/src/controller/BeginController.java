package controller;

import model.Network;
import model.NetworkFileManager;

import java.io.File;

public class BeginController {

    private Network network;
    private NetworkFileManager fileManager;
    private String networkId;

    public BeginController() {
        fileManager = new NetworkFileManager();
        network = null;
        networkId = null;
    }

    // 注册一个新的社交网络，同时创建这个社交网络中的第一个用户
    public Network registerNewNetwork(String username, String password, String homeTown, String workPlace) {
        if (username == null || username.isEmpty()
                || password == null || password.isEmpty()
                || homeTown == null || homeTown.isEmpty()
                || workPlace == null || workPlace.isEmpty()) {
            System.out.println("User information cannot be empty.");
            return null;
        }

        network = new Network(username, password, homeTown, workPlace);
        networkId = network.getNetworkId();
        fileManager.saveNetwork(network);

        return network;
    }

    // 用户选择去加载已有的社交网络
    public Network loadExistingNetwork(String networkId) {

        Network loadedNetwork = null;

        // 程序通过社交网络ID去查找data文件夹中对应的文本文档
        if (networkId == null || networkId.isEmpty()) {
            System.out.println("Network ID cannot be empty.");
            return null;
        }

        String filePath = NetworkFileManager.buildNetworkFilePath(networkId);
        File networkFile = new File(filePath);

        if (!networkFile.exists()) {
            System.out.println("This network does not exist.");
            return null;
        }

        // 通过文件管理器去读取这个文本文档
        loadedNetwork = fileManager.loadNetwork(networkId);

        // 检查哪一个信息是否有误，遇见有误信息返回空
        if (loadedNetwork == null) {
            System.out.println("Failed to load network.");
            return null;
        }

        // 如果信息都正确，调用已有方法，恢复或加载所有的有关信息，返回这个社交网络对象
        network = loadedNetwork;
        this.networkId = loadedNetwork.getNetworkId();

        return loadedNetwork;
    }

    // 加载社交网络之后检查用户ID是否存在
    public boolean checkUserIdExists(Network network, int userId) {
        if (network == null) {
            System.out.println("No network has been loaded.");
            return false;
        }

        return network.checkUserIdExists(userId);
    }

    // 核对密码
    public boolean checkPassword(Network network, int userId, String password) {
        if (network == null) {
            System.out.println("No network has been loaded.");
            return false;
        }

        return network.checkPassword(userId, password);
    }

    // 处理用户登录之后的逻辑
    public Network handleLogin(String networkId, int userId, String password) {
        Network loadedNetwork = loadExistingNetwork(networkId);

        if (loadedNetwork == null) {
            return null;
        }

        if (!checkUserIdExists(loadedNetwork, userId)) {
            return null;
        }

        if (!checkPassword(loadedNetwork, userId, password)) {
            return null;
        }

        loadedNetwork.setCurrentUserById(userId);
        fileManager.saveNetwork(loadedNetwork);

        return loadedNetwork;
    }

    // 获取当前控制器正在管理的社交网络
    public Network getNetwork() {
        return network;
    }

    // 获取当前社交网络的 ID
    public String getNetworkId() {
        return networkId;
    }

    // 获取当前保存文件的路径
    public String getFilePath() {
        return fileManager.getFilePath();
    }
}
