package statistic;

import fileCommon.AbstractGetFrom;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticSaver extends AbstractGetFrom implements IStatisticSaver {

    private Map<Integer, String> aggregateInfo;
    private StringBuilder stringBuilder;
    private List<String> arrForFreshId;
    private String sourceText;
    private String filePath = "resources/StatisticText.txt";

    public StatisticSaver(){
        aggregateInfo = new HashMap<>();
        stringBuilder = new StringBuilder();
        arrForFreshId = new ArrayList<>();
    }

    @Override
    public void changeAggregate(int idText, Map<Double, String> topRateUser) {
        stringBuilder.setLength(0);
        Double[] toForeEach = topRateUser.keySet().toArray(new Double[0]);
        for (Double foreEach : toForeEach) {
            stringBuilder.append(" Name: ").append(topRateUser.get(foreEach)).append("#");
            stringBuilder.append(" PerMin: ").append(foreEach).append("#");
        }
        aggregateInfo.put(idText, stringBuilder.toString());
    }

    @Override
    public String getActualStats(int idText){
        if (!aggregateInfo.containsKey(idText)){
            return null;
        }
        return aggregateInfo.get(idText);
    }

    @Override
    public void saveToFile() {
        Integer[] toForeEach = aggregateInfo.keySet().toArray(new Integer[0]);
        String findIdHelper;
        for (int foreEach : toForeEach) {
            findIdHelper = "IdText: " + foreEach;
            if (!sourceText.contains(findIdHelper)){
                arrForFreshId.add(findIdHelper + "##" +
                        aggregateInfo.get(foreEach) + " \n####\n");
            }
            sourceText = sourceText.replaceFirst(findIdHelper + ".*\n####",
                    findIdHelper + "##" + aggregateInfo.get(foreEach) + " \n####");
        }
        if (!arrForFreshId.isEmpty()){
            stringBuilder.setLength(0);
            for (String s : arrForFreshId) {
                stringBuilder.append(s);
            }
            sourceText += stringBuilder.toString();
        }
        Writer fileWrite;
        try {
            fileWrite = new FileWriter(filePath);
            fileWrite.write(sourceText);
            fileWrite.flush();
            fileWrite.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String takeStatOnId(int idText){
        if (sourceText == null){
            sourceText = getFileToString(filePath);
        }
        if (sourceText.isEmpty()){
            return null;
        }
        int startCount = sourceText.indexOf(idText + "##");
        if (startCount == -1){
            return "";
        }
        int endCount = sourceText.indexOf("####", startCount);
        return sourceText.substring(startCount + String.valueOf(idText).length() + 2, endCount);
    }
}
