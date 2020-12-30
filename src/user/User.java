package user;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class User implements IUser{

    private String name;
    private String userInfo;
    private long loginTime;
    private final UserSaver info;
    private final StringBuilder stringBuilder;
    private final Map<Double, String> sortedMapResult;
    DecimalFormat decimalFormat;

    public User(){
        info = new UserSaver();
        stringBuilder = new StringBuilder();
        sortedMapResult = new TreeMap<>(Collections.reverseOrder());
        decimalFormat = new DecimalFormat("###.##");
    }

    @Override
    public boolean check(String userName){  //Use only in login
        String toInfo;
        name = userName;
        if ((toInfo = info.findInfo(userName)) == null){
            return false;
        }
        userInfo = toInfo;
        return true;
    }

    @Override
    public void startLoginTime(){
        loginTime = System.currentTimeMillis();
    }

    @Override
    public void setAllUserParameters(int textId, double scorePerMin) {
        if (scorePerMin == 0) {
            return;
        }
        String idHelper = "IdText: " + textId;
        sortedMapResult.put(scorePerMin, idHelper);
    }

    @Override
    public void showStats(){
        fillSortedMap();
        String showTime = "\nAll the time spent in the application: "
                + timeInMinutes(timeInMillis()) + "minutes";
        if (sortedMapResult.isEmpty()){
            System.out.println("Statistics for this user are empty" + showTime);
            return;
        }
        int counter = 0;
        Double[] toForeEach = sortedMapResult.keySet().toArray(new Double[0]);
        for (Double foreEach : toForeEach) {
            stringBuilder.append(sortedMapResult.get(foreEach)).
                    append(" - Сharacters per minute: ").append(foreEach).append(" # ");
            counter++;
            if (counter % 4 == 0){
                stringBuilder.append("\n");
            }
            if (counter == 12){
                break;
            }
        }
        System.out.println("Your current username: " + name +
                "\nPrevious result: \n" + stringBuilder.toString() + showTime);
        stringBuilder.setLength(0);
    }

    @Override
    public void fillSortedMap(){
        int startCount = 0;
        int endCount;
        String idHelper;
        double scoreHelper;
        if (userInfo == null){
            return;
        }
        while ((startCount = userInfo.indexOf("IdText: ", startCount)) != -1){
            endCount = userInfo.indexOf("#", startCount);
            idHelper = userInfo.substring(startCount, endCount);
            startCount = userInfo.indexOf("PerMin: ", startCount);
            endCount = userInfo.indexOf("#", startCount);
            scoreHelper = Double.parseDouble(userInfo.substring(startCount + 8, endCount));
            if (!sortedMapResult.containsKey(scoreHelper)){
                sortedMapResult.put(scoreHelper, idHelper);
            }
        }
    }

    /**
     * counts the time spent in the application
     * @return time spent in the application in milliseconds
     */
    private long timeInMillis(){
        int helper;
        int helperEnd;
        String timeHelper ="ElapsedTime: ";
        long savedTime;
        if (userInfo != null){
            if ((helper = userInfo.indexOf(timeHelper)) != -1){
                helperEnd = userInfo.indexOf("\n", helper);
                savedTime = Long.parseUnsignedLong(userInfo.substring(helper + timeHelper.length(), helperEnd));
                return System.currentTimeMillis() - loginTime + savedTime;
            }
        }
        return System.currentTimeMillis() - loginTime;
    }

    /**
     * converts milliseconds to minutes
     * @param timeInMillis actual time spent by the user in the application in milliseconds
     * @return formatted and rounded time spent in the application in minutes
     */
    private double timeInMinutes(long timeInMillis){
        return Double.parseDouble(decimalFormat.format((double)timeInMillis / 60000));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void saveBeforeLogout(){
        info.saveToFile(name, sortedMapResult, timeInMillis());
    }
}
