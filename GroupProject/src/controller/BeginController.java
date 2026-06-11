package controller;

import model.Network;
import model.NetworkFileManager;

import java.io.File;

/**
 * Controls the login and registration flow before the main window opens.
 */
public class BeginController {

    /** The network currently loaded or created by the begin controller. */
    private Network network;

    /** The file manager used to load and save network data. */
    private NetworkFileManager fileManager;

    /** The ID of the current network. */
    private String networkId;

    /**
     * Creates a begin controller with an empty network state.
     */
    public BeginController() {
        fileManager = new NetworkFileManager();
        network = null;
        networkId = null;
    }

    /**
     * Registers a new social network and creates the first user in that network.
     *
     * @param username  the first user's username
     * @param password  the first user's password
     * @param homeTown  the first user's hometown
     * @param workPlace the first user's workplace
     * @return the created network, or null if the input is invalid
     */
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

    /**
     * Loads an existing network from the data folder by network ID.
     *
     * @param networkId the ID of the network to load
     * @return the loaded network, or null if the file cannot be loaded
     */
    public Network loadExistingNetwork(String networkId) {

        Network loadedNetwork = null;

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

        loadedNetwork = fileManager.loadNetwork(networkId);

        if (loadedNetwork == null) {
            System.out.println("Failed to load network.");
            return null;
        }

        network = loadedNetwork;
        this.networkId = loadedNetwork.getNetworkId();

        return loadedNetwork;
    }

    /**
     * Checks whether a user ID exists in a loaded network.
     *
     * @param network the network to check
     * @param userId  the user ID to find
     * @return true if the user ID exists, otherwise false
     */
    public boolean checkUserIdExists(Network network, int userId) {
        if (network == null) {
            System.out.println("No network has been loaded.");
            return false;
        }

        return network.checkUserIdExists(userId);
    }

    /**
     * Checks whether the password matches the selected user ID.
     *
     * @param network  the network to check
     * @param userId   the user ID used for login
     * @param password the password entered by the user
     * @return true if the password is correct, otherwise false
     */
    public boolean checkPassword(Network network, int userId, String password) {
        if (network == null) {
            System.out.println("No network has been loaded.");
            return false;
        }

        return network.checkPassword(userId, password);
    }

    /**
     * Handles the complete login process.
     *
     * @param networkId the network ID entered by the user
     * @param userId    the user ID entered by the user
     * @param password  the password entered by the user
     * @return the loaded network after successful login, or null if login fails
     */
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

    /**
     * Gets the network currently managed by this controller.
     *
     * @return the current network
     */
    public Network getNetwork() {
        return network;
    }

    /**
     * Gets the current network ID.
     *
     * @return the current network ID
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * Gets the file path currently used by the file manager.
     *
     * @return the current file path
     */
    public String getFilePath() {
        return fileManager.getFilePath();
    }
}
