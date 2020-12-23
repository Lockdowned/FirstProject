package FromFile;

public class GetFromTextStatisticFile extends AbstractGetFrom{

    private String allStatistic;
    private String filePath = "C:\\Users\\olegk\\JavaProject\\BlindPrint\\resources\\StatisticText.txt";


    public GetFromTextStatisticFile(){

    }

    public String takeStatOnId(int idText){
        allStatistic = getFileToString(filePath);
        if (allStatistic.isEmpty()){
            return null;
        }
        int startCount = allStatistic.indexOf(idText + "##");
        int endCount = allStatistic.indexOf("####", startCount);
        return allStatistic.substring(startCount, endCount);
    }

}
