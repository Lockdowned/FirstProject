import fileActions.StatisticSaver;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class StatisticText {

    private String currentTextStat;
    private Map<Double, String> topRateUser;
    private long elapsedTime;
    private double lastResultSymbolPerMin;
    private int previousTextId;
    private StatisticSaver actionsOnTheStatisticsFile;
    private int currentId;
    DecimalFormat decimalFormat;


    public StatisticText(){
        topRateUser = new TreeMap<>(Collections.reverseOrder());
        actionsOnTheStatisticsFile = StatisticSaver.getInstance();
        decimalFormat = new DecimalFormat("###.###");

    }


    /**
     *
     * @param printText найденный текст
     * @return zero если введенный текст не соответствует найденному
     */
    public double analyzeSymbolPerMin(String printText, int idText, String userName){
        if (topRateUser.isEmpty() || previousTextId != idText){
            fillTopRateUser(idText);
        }
        previousTextId = idText;
        System.out.println("Write this text: \n" + printText);
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(System.in);
        String writtenText = in.next();
        elapsedTime = System.currentTimeMillis() - startTime;
        if (validateText(printText, writtenText)){
            char[] charsWrittenText = writtenText.toCharArray();
            double symbolPerMin = (double)charsWrittenText.length / ((double)elapsedTime / 60000);
            lastResultSymbolPerMin = Double.parseDouble(decimalFormat.format(symbolPerMin));
            System.out.println("Your current result of characters per minute: " + lastResultSymbolPerMin + "\n");
            topRateUser.put(lastResultSymbolPerMin, userName);
            currentId = idText;
            return lastResultSymbolPerMin;
        }

        return 0;
    }

    /**
     * метод для проверки соответстия введенного текста и найденного
     * @param printText найденный текст
     * @param writtenText текст который мы написали
     * @return true если введённый текст соответствует найденному,
     *         позже если соответствует критериеям
     *         false в инных случаях
     */
    private boolean validateText(String printText, String writtenText){
        return true;
    }



    public void showTopRate(){
        if (topRateUser.isEmpty()){
            System.out.println("Stats about this text not found");
            return;
        }
        Double[] toForeEach = topRateUser.keySet().toArray(new Double[0]);
        byte counter = 0;
        System.out.println("Top 10 result(or less):");
        for (Double foreEach : toForeEach) {
            System.out.println("Name: " + topRateUser.get(foreEach)
                    + " Score: " + foreEach + " characters / minute");
            counter++;
            if (counter == 10) break;
        }
    }

    private void fillTopRateUser(int idText){
        if (!topRateUser.isEmpty()){
            actionsOnTheStatisticsFile.changeAggregate(previousTextId, topRateUser);
            topRateUser.clear();
        }
        currentTextStat = actionsOnTheStatisticsFile.getActualStats(idText);
        if (currentTextStat == null){
            currentTextStat = actionsOnTheStatisticsFile.takeStatOnId(idText);
        }
        if (currentTextStat == null){
            return;
        }
        int startCount = 0;
        int endCount;
        String nameHelper = "Name: ";
        String perMinHelper = "PerMin: ";
        String foundName;
        double foundPerMin;
        while (true){
            startCount = currentTextStat.indexOf(nameHelper, startCount);
            if (startCount == -1) break;
            endCount = currentTextStat.indexOf("#", startCount);
            foundName = currentTextStat.substring(startCount + nameHelper.length(), endCount);
            startCount = currentTextStat.indexOf(perMinHelper, endCount);
            endCount = currentTextStat.indexOf("#", startCount);
            foundPerMin = Double.parseDouble(currentTextStat
                    .substring(startCount + perMinHelper.length(), endCount));
            topRateUser.put(foundPerMin, foundName);
        }

    }

    public void saveBeforeClose(){
        actionsOnTheStatisticsFile.changeAggregate(currentId, topRateUser);
        actionsOnTheStatisticsFile.saveToFile();
    }



}
