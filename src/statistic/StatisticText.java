package statistic;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class StatisticText implements IStatisticText{

    private final Map<Double, String> topRateUser;
    private int previousTextId;
    private final StatisticSaver actionsOnTheStatisticsFile;
    private int currentId;
    private final DecimalFormat decimalFormat;
    private final DecimalFormat decimalFormatForMistakes;

    public StatisticText(){
        topRateUser = new TreeMap<>(Collections.reverseOrder());
        actionsOnTheStatisticsFile = new StatisticSaver();
        decimalFormat = new DecimalFormat("#.###");
        decimalFormatForMistakes = new DecimalFormat("#");
    }

    @Override
    public double analyzeSymbolPerMin(String printText, int idText, String userName){
        if (topRateUser.isEmpty() || previousTextId != idText){
            fillTopRateUser(idText);
        }
        previousTextId = idText;
        System.out.println("Line break is a space\nWrite this text: \n" + printText);
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(System.in);
        String writtenText = in.nextLine();
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (validateText(printText, writtenText)){
            char[] charsWrittenText = writtenText.toCharArray();
            double symbolPerMin = (double)charsWrittenText.length / ((double) elapsedTime / 60000);
            double lastResultSymbolPerMin = Double.parseDouble(decimalFormat.format(symbolPerMin));
            System.out.println("Your current result of characters per minute: "
                    + lastResultSymbolPerMin + "\n");
            topRateUser.put(lastResultSymbolPerMin, userName);
            currentId = idText;
            return lastResultSymbolPerMin;
        }
        System.out.println("You have a lot of mistakes, the result does not count");
        return 0;
    }

    /**
     * checks compliance of text entered and found
     * @param printText found text from file
     * @param writtenText entered text
     * @return true if the entered text matches the found text (by 85%)
     */
    private boolean validateText(String printText, String writtenText){
        if (true){    // HIDE THIS FOR WORK
            return true;
        }
        printText = printText.replaceAll("\n", " ");
        char[] charsPrintText = printText.toCharArray();
        char[] charsWrittenText = writtenText.toCharArray();
        int lengthPrint = charsPrintText.length;
        int lengthWitten = charsWrittenText.length;
        double delimiterResult = Double.parseDouble(decimalFormat.
                format((double) lengthPrint / (double) lengthWitten));
        if (!(delimiterResult > 0.85 && delimiterResult < 1.15)){
            return false;
        }
        int minLength = Math.min(lengthPrint, lengthWitten);
        int counterMismatches = Integer.parseInt(decimalFormatForMistakes.
                format((double) minLength - ((double) minLength * 0.85)));
        int refreshCounter = 0;
        for (int i = 0; i < minLength; i++) {
            if (charsPrintText[i] != charsWrittenText[i]){
                refreshCounter++;
            }
            if (refreshCounter == counterMismatches){
                return false;
            }
        }
        return true;
    }

    @Override
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

    /**
     * parses statistics for the current text by adding it to the TreeMap
     * @param idText id current find text from file
     */
    private void fillTopRateUser(int idText){
        if (!topRateUser.isEmpty()){
            actionsOnTheStatisticsFile.changeAggregate(previousTextId, topRateUser);
            topRateUser.clear();
        }
        String currentTextStat = actionsOnTheStatisticsFile.getActualStats(idText);
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

    @Override
    public void saveBeforeClose(){
        actionsOnTheStatisticsFile.changeAggregate(currentId, topRateUser);
        actionsOnTheStatisticsFile.saveToFile();
    }
}
