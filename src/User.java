import fileActions.UserSaver;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class User {

    private String name;

    private String userInfo;
    private long loginTime;
    private long allTime;
    private UserSaver info;
    private StringBuilder stringBuilder;
    private StringBuilder stringBuilderForShow;
    private Map<Double, String> sortedMapResult;
    DecimalFormat decimalFormat;

    public User(){
        info = new UserSaver();
        stringBuilder = new StringBuilder();
        stringBuilderForShow = new StringBuilder();
        sortedMapResult = new TreeMap<>(Collections.reverseOrder());
        decimalFormat = new DecimalFormat("###.##");
    }


    /**
     *
     * @param userName имя пользователя
     * @return true если пользователь существует
     *         или false если пользователь не найден
     */
    public boolean check(String userName){  //Use only in login
        String toInfo;
        name = userName;
        if ((toInfo = info.findInfo(userName)) == null){
            return false;
        }
        userInfo = toInfo;
        return true;
    }



    public void startLoginTime(){
        loginTime = System.currentTimeMillis();
    }

    public void setAllUserParameters(int textId, double scorePerMin) {
        if (scorePerMin == 0) {
            return;
        }
        String idHelper = "IdText: " + textId;
        sortedMapResult.put(scorePerMin, idHelper);
    }


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

    private double timeInMinutes(long timeInMillis){
        return Double.parseDouble(decimalFormat.format((double)timeInMillis / 60000));
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void saveBeforeLogout(){
        info.saveToFile(name, sortedMapResult, timeInMillis());

    }

}
