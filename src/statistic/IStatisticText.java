package statistic;

public interface IStatisticText {

    /**
     * causes the filling of the internal Map,
     * calls text validation if passed count char in minute
     * @param printText found text from file
     * @param idText id current find text from file
     * @param userName username of the currently logged in
     * @return analyze value or 0 if validation failed
     */
    double analyzeSymbolPerMin(String printText, int idText, String userName);

    /**
     * shows the top 10 (or less) results for this text
     */
    void showTopRate();

    /**
     * updates the parameters and saves them
     */
    void saveBeforeClose();
}
