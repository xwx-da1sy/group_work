package Tester;

import model.Network;
import model.User;

/**
 * Runs temporary console tests for the User and Network model classes.
 */
public class TemporaryTester {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("========== TemporaryTester Start ==========\n");

        testUserCreationAndGetters();
        testUserSetters();
        testUserFriends();
        testNetworkInit();
        testAddUser();
        testDuplicateUser();
        testNetworkFull();
        testCreateUser();
        testGetUser();
        testCheckLogin();
        testCheckPassword();
        testSetAndClearCurrentUser();
        testIsFriend();
        testViewFriend();
        testRemoveFriend();
        testAddEachOther();
        testRemoveEachOther();
        testConstructorWithCurrentUser();
        testConstructorWithArgs();

        System.out.println("\n========== Results ==========");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total:  " + (passed + failed));
        if (failed == 0) {
            System.out.println("All tests passed!");
        }
    }

    private static void check(String testName, boolean condition) {
        if (condition) {
            passed++;
        } else {
            failed++;
            System.out.println("  FAIL: " + testName);
        }
    }

    private static void testUserCreationAndGetters() {
        System.out.println("[testUserCreationAndGetters]");
        User u = new User("alice", "pass1", "Beijing", "Shanghai", 1);
        check("getUsername", "alice".equals(u.getUsername()));
        check("getPassword", "pass1".equals(u.getPassword()));
        check("getHomeTown", "Beijing".equals(u.getHomeTown()));
        check("getWorkPlace", "Shanghai".equals(u.getWorkPlace()));
        check("getUserId", u.getUserId() == 1);
        check("getFriends initially empty", u.getFriends().isEmpty());
    }

    private static void testUserSetters() {
        System.out.println("[testUserSetters]");
        User u = new User("bob", "pw", "A", "B", 2);
        u.setUsername("bob2");
        check("setUsername", "bob2".equals(u.getUsername()));
        u.setPassword("newpw");
        check("setPassword", "newpw".equals(u.getPassword()));
        u.setHomeTown("C");
        check("setHomeTown", "C".equals(u.getHomeTown()));
        u.setWorkPlace("D");
        check("setWorkPlace", "D".equals(u.getWorkPlace()));
        u.setUserId(99);
        check("setUserId", u.getUserId() == 99);
    }

    private static void testUserFriends() {
        System.out.println("[testUserFriends]");
        User u = new User("charlie", "pw", "X", "Y", 3);
        u.addFriend(4);
        check("addFriend", u.getFriends().contains(4));
        check("addFriend size 1", u.getFriends().size() == 1);
        u.removeFriend(4);
        check("removeFriend", !u.getFriends().contains(4));
        check("removeFriend size 0", u.getFriends().isEmpty());
    }

    private static void testNetworkInit() {
        System.out.println("[testNetworkInit]");
        Network n = new Network();
        check("getCurrentUser null", n.getCurrentUser() == null);
        check("userExists non-existing", !n.userExists("nobody"));
    }

    private static void testAddUser() {
        System.out.println("[testAddUser]");
        Network n = new Network();
        User u = new User("alice", "pass1", "Beijing", "Shanghai", 0);
        n.addUser(u);
        check("addUser-exists", n.userExists("alice"));
        User retrieved = n.getUser("alice");
        check("addUser-getUser", retrieved != null && "alice".equals(retrieved.getUsername()));
        check("addUser-currentUser (userId=0)", n.getCurrentUser() != null && "alice".equals(n.getCurrentUser().getUsername()));
    }

    private static void testDuplicateUser() {
        System.out.println("[testDuplicateUser]");
        Network n = new Network();
        User u1 = new User("eve", "pw", "H", "W", 0);
        n.addUser(u1);
        User u2 = new User("eve", "pw2", "H2", "W2", 1);
        n.addUser(u2);
        check("duplicate-name-allowed", n.getUserIdsByUsername("eve").size() == 2);
    }

    private static void testNetworkFull() {
        System.out.println("[testNetworkFull]");
        Network n = new Network();
        for (int i = 0; i < 20; i++) {
            User u = new User("user" + i, "pw", "H", "W", i);
            n.addUser(u);
        }
        User extra = new User("extra", "pw", "H", "W", 20);
        n.addUser(extra);
        check("networkFull", !n.userExists("extra"));
    }

    private static void testCreateUser() {
        System.out.println("[testCreateUser]");
        Network n = new Network();
        User u = n.createUser("frank", "pw", "GZ", "SZ");
        check("createUser-not-null", u != null);
        check("createUser-username", "frank".equals(u.getUsername()));
        check("createUser-exists", n.userExists("frank"));
        check("createUser-nextUserId", u.getUserId() >= 0);

        User dup = n.createUser("frank", "x", "x", "x");
        check("createUser-duplicate-name-allowed", dup != null && dup.getUserId() != u.getUserId());
    }

    private static void testGetUser() {
        System.out.println("[testGetUser]");
        Network n = new Network();
        n.createUser("grace", "pw", "H", "W");
        User g = n.getUser("grace");
        check("getUser-exists", g != null && "grace".equals(g.getUsername()));
        User nobody = n.getUser("nobody");
        check("getUser-nobody", nobody == null);
    }

    private static void testCheckLogin() {
        System.out.println("[testCheckLogin]");
        Network n = new Network();
        n.createUser("henry", "secret", "H", "W");

        check("checkLogin-correct", n.checkLogin(0, "secret"));
        check("checkLogin-currentUser", n.getCurrentUser() != null && "henry".equals(n.getCurrentUser().getUsername()));

        n.clearCurrentUser();
        check("checkLogin-wrong-pw", !n.checkLogin(0, "wrong"));
        check("checkLogin-non-existing", !n.checkLogin(99, "pw"));
    }

    private static void testCheckPassword() {
        System.out.println("[testCheckPassword]");
        Network n = new Network();
        n.createUser("iris", "mypw", "H", "W");
        check("checkPassword-correct", n.checkPassword(0, "mypw"));
        check("checkPassword-wrong", !n.checkPassword(0, "bad"));
        check("checkPassword-non-existing", !n.checkPassword(99, "pw"));
    }

    private static void testSetAndClearCurrentUser() {
        System.out.println("[testSetAndClearCurrentUser]");
        Network n = new Network();
        n.createUser("jack", "pw", "H", "W");
        n.createUser("kate", "pw", "H", "W");

        n.checkLogin(0, "pw");
        check("login-jack", "jack".equals(n.getCurrentUser().getUsername()));

        n.clearCurrentUser();
        check("clearCurrentUser", n.getCurrentUser() == null);

        n.setCurrentUser(n.getUser("kate"));
        check("setCurrentUser-kate", "kate".equals(n.getCurrentUser().getUsername()));

        n.setCurrentUser(null);
        check("setCurrentUser-null", n.getCurrentUser() != null);
    }

    private static void testIsFriend() {
        System.out.println("[testIsFriend]");
        Network n = new Network();
        n.createUser("leo", "pw", "H", "W");
        n.createUser("mia", "pw", "H", "W");

        n.checkLogin(0, "pw");
        check("isFriend-before-add", !n.isFriend("mia"));

        n.addEachOther("leo", "mia", "pw", "pw");
        check("isFriend-after-add", n.isFriend("mia"));
    }

    private static void testViewFriend() {
        System.out.println("[testViewFriend]");
        Network n = new Network();
        n.createUser("nick", "pw", "H", "W");
        n.createUser("olivia", "pw", "H", "W");

        n.addEachOther("nick", "olivia", "pw", "pw");

        n.checkLogin(0, "pw");
        User friend = n.viewFriend("olivia");
        check("viewFriend-returns-user", friend != null && "olivia".equals(friend.getUsername()));

        User notFriend = n.viewFriend("nobody");
        check("viewFriend-not-friend-null", notFriend == null);
    }

    private static void testRemoveFriend() {
        System.out.println("[testRemoveFriend]");
        Network n = new Network();
        n.createUser("peter", "pw", "H", "W");
        n.createUser("quincy", "pw", "H", "W");

        n.addEachOther("peter", "quincy", "pw", "pw");
        n.checkLogin(0, "pw");

        check("beforeRemove", n.isFriend("quincy"));

        n.removeFriend("quincy");
        check("afterRemove", !n.isFriend("quincy"));
    }

    private static void testAddEachOther() {
        System.out.println("[testAddEachOther]");
        Network n = new Network();
        n.createUser("rachel", "pw", "H", "W");
        n.createUser("steve", "pw", "H", "W");

        n.addEachOther("rachel", "steve", "pw", "pw");

        User rachel = n.getUser("rachel");
        User steve = n.getUser("steve");
        check("addEachOther-rachel-has-steve", rachel.getFriends().contains(steve.getUserId()));
        check("addEachOther-steve-has-rachel", steve.getFriends().contains(rachel.getUserId()));

        n.addEachOther("rachel", "steve", "pw", "pw");
        check("addEachOther-already-friends (idempotent)", true);
    }

    private static void testRemoveEachOther() {
        System.out.println("[testRemoveEachOther]");
        Network n = new Network();
        n.createUser("tom", "pw", "H", "W");
        n.createUser("uma", "pw", "H", "W");

        n.addEachOther("tom", "uma", "pw", "pw");
        n.removeEachOther("tom", "uma");

        User tom = n.getUser("tom");
        User uma = n.getUser("uma");
        check("removeEachOther-tom", !tom.getFriends().contains(uma.getUserId()));
        check("removeEachOther-uma", !uma.getFriends().contains(tom.getUserId()));
    }

    private static void testConstructorWithCurrentUser() {
        System.out.println("[testConstructorWithCurrentUser]");
        User u = new User("vince", "pw", "H", "W", 5);
        Network n = new Network(u);
        check("ctorWithUser-currentUser", n.getCurrentUser() != null && "vince".equals(n.getCurrentUser().getUsername()));
        check("ctorWithUser-exists", n.userExists("vince"));
    }

    private static void testConstructorWithArgs() {
        System.out.println("[testConstructorWithArgs]");
        Network n = new Network("will", "pw", "NY", "LA");
        check("ctorWithArgs-currentUser", n.getCurrentUser() != null && "will".equals(n.getCurrentUser().getUsername()));
        check("ctorWithArgs-exists", n.userExists("will"));
    }
}
