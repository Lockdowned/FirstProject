import FromFile.GetFromTextStatisticFile;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.TreeMap;

public class StatisticText {

    private String currentTextStat;
    private TreeMap<Double, String> topRateUser;
    private long elapsedTime;
    private double lastResultSymbolPerMin;


    public StatisticText(){
        topRateUser = new TreeMap<>();

    }


    /**
     *
     * @param printText найденный текст
     * @return zero если введенный текст не соответствует найденному
     */
    public double analyzeSymbolPerMin(String printText, int idText){
        System.out.println("Write this text: \n" + printText);
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(System.in);
        String writtenText = in.next();
        elapsedTime = System.currentTimeMillis() - startTime;
        if (validateText(printText, writtenText)){
            char[] charsWrittenText = writtenText.toCharArray();
            double symbolPerMin = (double)charsWrittenText.length / ((double)elapsedTime / 60000);
            DecimalFormat decimalFormat = new DecimalFormat("###.###");
            lastResultSymbolPerMin = Double.parseDouble(decimalFormat.format(symbolPerMin));
            System.out.println("Your current result of characters per minute: " + lastResultSymbolPerMin);
        }
        fillTopRateUser(idText);

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
        if (currentTextStat == null){
            return;
        }
        Double[] toForeEach = topRateUser.keySet().toArray(new Double[0]);
        for (Double foreEach : toForeEach) {
            System.out.println("Name: " + topRateUser.get(foreEach)
                    + "Score: " + foreEach + " characters / minute\n");
        }
    }

    private void fillTopRateUser(int idText){
        GetFromTextStatisticFile get = new GetFromTextStatisticFile();
        currentTextStat = get.takeStatOnId(idText);
        if (currentTextStat == null){
            System.out.println("Stats about this text not found");
            return;
        }
        int startCount = 0;
        int endCount;
        int perMinStartCount = 0;
        int perMinEndCount;
        String nameHelper = "Name: ";
        String perMinHelper = "PerMin: ";
        String foundName;
        double foundPerMin;
        while (true){
            startCount = currentTextStat.indexOf(nameHelper, startCount);
            endCount = currentTextStat.indexOf("#", startCount);
            if (startCount == -1) break;
            foundName = currentTextStat.substring(startCount + nameHelper.length(), endCount);
            perMinStartCount = currentTextStat.indexOf(perMinHelper, perMinStartCount);
            perMinEndCount = currentTextStat.indexOf("#", perMinStartCount);
            foundPerMin = Double.parseDouble(currentTextStat
                    .substring(perMinStartCount + perMinHelper.length(), perMinEndCount));
            topRateUser.put(foundPerMin, foundName);
        }



    }




}
