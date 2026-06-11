package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Loads and saves social network data in text files.
 *
 * <p>The text file format is divided into four sections:
 * NETWORK_ID, CURRENT_USER, USERS, and FRIENDSHIPS.</p>
 */
public class NetworkFileManager {

    /** The file path currently used by this file manager. */
    private String filePath;

    /** The project folder name used when the program runs from the workspace root. */
    private static final String PROJECT_FOLDER = "GroupProject";

    /** The folder used to store network text files. */
    private static final String DATA_FOLDER = "data";

    /** The prefix used by every network data file. */
    private static final String NETWORK_FILE_PREFIX = "network-";

    /** The file extension used by every network data file. */
    private static final String FILE_EXTENSION = ".txt";

    /** The default file path used before a network ID is selected. */
    private static final String DEFAULT_FILE_PATH = buildNetworkFilePath("0");

    /** The section name used for the network ID. */
    private static final String NETWORK_ID_SECTION = "NETWORK_ID";

    /** The section name used for the current user ID. */
    private static final String CURRENT_USER_SECTION = "CURRENT_USER";

    /** The section name used for user records. */
    private static final String USERS_SECTION = "USERS";

    /** The section name used for friendship records. */
    private static final String FRIENDSHIPS_SECTION = "FRIENDSHIPS";

    /** The separator used when writing fields to the text file. */
    private static final String WRITE_SEPARATOR = "|";

    /** The separator used when reading fields from the text file. */
    private static final String READ_SEPARATOR = "\\|";

    /**
     * Creates a file manager with the default file path.
     */
    public NetworkFileManager() {
        filePath = DEFAULT_FILE_PATH;
    }

    /**
     * Creates a file manager with a specified file path.
     *
     * @param filePath the path to use
     */
    public NetworkFileManager(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Gets the current file path.
     *
     * @return the current file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the current file path.
     *
     * @param filePath the new file path
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Builds a network file path from a network ID.
     *
     * @param networkId the network ID
     * @return the path of the matching network file
     */
    public static String buildNetworkFilePath(String networkId) {
        return getDataFolderPath() + File.separator + NETWORK_FILE_PREFIX + networkId + FILE_EXTENSION;
    }

    /**
     * Gets the data folder path used by the current running environment.
     *
     * @return the data folder path
     */
    private static String getDataFolderPath() {
        File projectDataFolder = new File(PROJECT_FOLDER + File.separator + DATA_FOLDER);

        if (projectDataFolder.exists()) {
            return projectDataFolder.getPath();
        }

        return DATA_FOLDER;
    }

    /**
     * Loads a network by network ID.
     *
     * @param networkId the network ID
     * @return the loaded network
     */
    public Network loadNetwork(String networkId) {
        filePath = buildNetworkFilePath(networkId);
        return loadNetwork();
    }

    /**
     * Loads a network from the current file path.
     *
     * @return the loaded network
     */
    public Network loadNetwork() {
        Network network = new Network();

        String networkId = null;

        int currentUserId = -1;

        String line = null;

        String currentSection = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            line = reader.readLine();

            if (NETWORK_ID_SECTION.equals(line)) {
                networkId = reader.readLine();
                network.setNetworkId(networkId);
            }

            line = reader.readLine();
            if (CURRENT_USER_SECTION.equals(line)) {
                currentUserId = Integer.parseInt(reader.readLine());
            }

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (USERS_SECTION.equals(line)) {
                    currentSection = USERS_SECTION;
                    continue;
                }

                if (FRIENDSHIPS_SECTION.equals(line)) {
                    currentSection = FRIENDSHIPS_SECTION;
                    continue;
                }

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

                if (FRIENDSHIPS_SECTION.equals(currentSection)) {
                    String[] friendshipInformation = line.split(READ_SEPARATOR);

                    int userId1 = Integer.parseInt(friendshipInformation[0]);
                    int userId2 = Integer.parseInt(friendshipInformation[1]);

                    network.addEachOther(userId1, userId2);
                }
            }

            if (currentUserId != -1) {
                network.setCurrentUserById(currentUserId);
            }
        } catch (IOException e) {
            System.out.println("Failed to read network file.");
        }

        return network;
    }

    /**
     * Saves a network to the file path generated from its network ID.
     *
     * @param network the network to save
     * @return true if saving succeeds, otherwise false
     */
    public boolean saveNetwork(Network network) {
        filePath = buildNetworkFilePath(network.getNetworkId());
        createParentFolder();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println(NETWORK_ID_SECTION);
            writer.println(network.getNetworkId());

            writer.println();
            writer.println(CURRENT_USER_SECTION);
            if (network.getCurrentUser() != null) {
                writer.println(network.getCurrentUser().getUserId());
            }

            writer.println();
            writer.println(USERS_SECTION);
            for (User user : network.getAllUsers()) {
                writer.println(user.getUserId()
                        + WRITE_SEPARATOR + user.getUsername()
                        + WRITE_SEPARATOR + user.getPassword()
                        + WRITE_SEPARATOR + user.getHomeTown()
                        + WRITE_SEPARATOR + user.getWorkPlace());
            }

            writer.println();
            writer.println(FRIENDSHIPS_SECTION);
            for (User user : network.getAllUsers()) {
                for (int friendId : user.getFriends()) {
                    if (user.getUserId() < friendId) {
                        writer.println(user.getUserId() + WRITE_SEPARATOR + friendId);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to save network file.");
            return false;
        }

        return true;
    }

    /**
     * Saves a network with a specified network ID.
     *
     * @param network   the network to save
     * @param networkId the network ID to use
     * @return true if saving succeeds, otherwise false
     */
    public boolean saveNetwork(Network network, String networkId) {
        network.setNetworkId(networkId);
        filePath = buildNetworkFilePath(networkId);
        return saveNetwork(network);
    }

    /**
     * Creates the parent folder for the current file path if it does not exist.
     */
    private void createParentFolder() {
        File file = new File(filePath);
        File parentFolder = file.getParentFile();

        if (parentFolder != null && !parentFolder.exists()) {
            parentFolder.mkdirs();
        }
    }
}
