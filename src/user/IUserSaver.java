package user;

import java.util.Map;

public interface IUserSaver {

    /**
     * fills in the current information from the file about all users
     * and returns information about a specific
     * @param userName current Username whom we are looking
     * @return statistics on texts if we find a user, arr null
     */
    String findInfo(String userName);

    /**
     * overwrites with more up-to-date user stats in file
     * @param name Username current user
     * @param sortedMapResult updated statistics on the user with all texts written by him
     * @param timeInMillis updated time spent by the user in the application
     */
    void saveToFile(String name, Map<Double, String> sortedMapResult, long timeInMillis);
}
