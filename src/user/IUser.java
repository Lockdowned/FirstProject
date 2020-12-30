package user;

public interface IUser {

    /**
     * looking for an existing user,
     * causes a local variable to be filled in UserSaver
     * @param userName entered username
     * @return true if userName find in file
     */
    boolean check(String userName);

    /**
     * puts the current time in a variable,
     * use after authorization
     */
    void startLoginTime();

    /**
     * adds new text statistics to the local map,
     * if scorePerMin = 0 adds nothing
     * @param textId id current text
     * @param scorePerMin number chars in minute after analyze
     */
    void setAllUserParameters(int textId, double scorePerMin);

    /**
     * shows the time spent by the user and
     * if they are there are the best 12 (or less) results by text
     */
    void showStats();

    /**
     * fills the local map
     */
    void fillSortedMap();

    /**
     * @return current Username
     */
    String getName();

    /**
     * transmits current variables to save when you exit or change user
     */
    void saveBeforeLogout();
}
