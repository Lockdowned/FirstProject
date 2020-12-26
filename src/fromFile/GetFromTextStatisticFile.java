package fromFile;

public class GetFromTextStatisticFile extends AbstractGetFrom{

    private String allStatistic;
    private String filePath = "resources/StatisticText.txt";


    public GetFromTextStatisticFile(){   // NEED PRIVATE

    }

    public String takeStatOnId(int idText){
        allStatistic = getFileToString(filePath);
        if (allStatistic.isEmpty()){
            return null;
        }
        int startCount = allStatistic.indexOf(idText + "##");
        int endCount = allStatistic.indexOf("####", startCount);
        return allStatistic.substring(startCount + 3, endCount + 4);  // надо нормально регуляркой
    }

}
