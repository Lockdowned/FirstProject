package statistic;

import java.util.Map;

public interface IStatisticSaver {

    /**
     * fills the local map with updated information for a specific text
     * @param idText id previous text after change or id current text if closing app
     * @param topRateUser actual Map with information about current text
     */
    void changeAggregate(int idText, Map<Double, String> topRateUser);

    /**
     * checks the local map with all updated texts
     * for the presence of the current id in it
     * @param idText id which are checked for
     * @return content of specific text if find, if no returns null
     */
    String getActualStats(int idText);

    /**
     * saves updated statistics about texts to a file
     */
    void saveToFile();

    /**
     * parses the necessary user statistics for the current text (id)
     * @param idText id text of which is taken statistics on users
     * @return necessary statistics on users
     */
    String takeStatOnId(int idText);
}
