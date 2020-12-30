package fileActions;


import java.util.Map;

public interface Aggregation {

//    void changeAggregate(int idText, String info);

    void saveToFile();

    Map<Integer, String> getFromAggregateInfo();



}
