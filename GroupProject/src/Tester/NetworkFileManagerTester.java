package Tester;

import model.Network;
import model.NetworkFileManager;

public class NetworkFileManagerTester {

    public static void main(String[] args) {
        Network network = new Network();
        network.createUser("eva", "eva123", "Dundee", "University of Dundee");
        network.createUser("frank", "frank123", "Glasgow", "Tech Company");
        network.addEachOther("eva", "frank", null, null);

        NetworkFileManager fileManager = new NetworkFileManager();
        fileManager.saveNetwork(network);

        Network loadedNetwork = fileManager.loadNetwork(network.getNetworkId());

        System.out.println("Saved file: " + fileManager.getFilePath());
        System.out.println("Network ID: " + loadedNetwork.getNetworkId());
        System.out.println("Current user: " + loadedNetwork.getCurrentUser().getUsername());
        System.out.println("Total users: " + loadedNetwork.getTotalUsers());
        System.out.println("Eva friends: " + loadedNetwork.getFriendsList("eva").size());
    }
}
