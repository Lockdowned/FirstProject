package resultSaver;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StatisticSaver implements Aggregation{


    private static StatisticSaver instance;
    private Map<Integer, String> aggregateInfo;
    private StringBuilder stringBuilder;

    private StatisticSaver(){
        aggregateInfo = new HashMap<>();
        stringBuilder = new StringBuilder();
    }


    public void changeAggregate(int idText, Map<Double, String> topRateUser) {
        stringBuilder.setLength(0);
        Double[] toForeEach = topRateUser.keySet().toArray(new Double[0]);
        for (Double foreEach : toForeEach) {
            stringBuilder.append("Name: ").append(topRateUser.get(foreEach)).append("#");
            stringBuilder.append("PerMin: ").append(foreEach).append("#");
        }
        aggregateInfo.put(idText, stringBuilder.toString());

    }


    public String getActualStats(int idText){
        if (!aggregateInfo.containsKey(idText)){
            return null;
        }
        return aggregateInfo.get(idText);
    }

    @Override
    public void saveToFile() {
        String filePath = "resources/StatisticText.txt";
        BufferedReader direction = null;
        try {
            direction = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder fileString = new StringBuilder();
        String temporal;
        try {
            while ((temporal = (direction.readLine())) != null){
                fileString.append(temporal);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        String sourceText = fileString.toString();
        Integer[] toForeEach = aggregateInfo.keySet().toArray(new Integer[0]);
        String findIdHelper;
        for (Integer foreEach : toForeEach) {
            findIdHelper = "IdText: " + foreEach;
            sourceText = sourceText.replaceFirst(findIdHelper + ".*",
                    "\n" + findIdHelper + "##" + aggregateInfo.get(foreEach) + "\n####");
        }
        Writer fileWrite;
        try {
            fileWrite = new FileWriter(filePath);
            fileWrite.write(sourceText);
            fileWrite.flush();
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    @Override
    public Map<Integer, String> getFromAggregateInfo() {
        return null;
    }

    public static StatisticSaver getInstance(){
        if (instance == null){
            return instance = new StatisticSaver();
        }
        return instance;
    }
}
