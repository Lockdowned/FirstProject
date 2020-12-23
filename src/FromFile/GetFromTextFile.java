package FromFile;

import java.util.ArrayList;
import java.util.Random;

public class GetFromTextFile extends AbstractGetFrom {

    private String filePath = "C:\\Users\\olegk\\JavaProject\\BlindPrint\\resources\\TextFile.txt";
    private String allTexts;
    private ArrayList<Integer> arrId;
    private int currentId;
    private Random random;


    public GetFromTextFile(){
        allTexts = getFileToString(filePath);
        arrId = new ArrayList<>();
        fillInArrayId();
        random = new Random();

    }

    public String takeRandomText(){
        if (arrId.size() == 0){
            System.out.println("File with text is empty, please fill him");
            currentId = 0;
            return null;
        }
        int randomId = random.nextInt(arrId.size());
        currentId = arrId.get(randomId);
        int helperStart = allTexts.indexOf(currentId + "##");
        int helperEnd = allTexts.indexOf("####", helperStart);
        String randomText = allTexts.substring(helperStart +
                String.valueOf(Math.abs(currentId)).length() + 2, helperEnd);
        return randomText;
    }

    /**
     * fill in arrId find id
     */
    private void fillInArrayId(){
        int startId = 0;
        int endId;
        int findId;
        String searchMark = "TextId : ";
        while ((startId = allTexts.indexOf(searchMark, startId)) != -1){
            endId = allTexts.indexOf("##", startId);
            findId = Integer.parseInt(allTexts.substring(startId + searchMark.length(), endId));
            startId += searchMark.length() + 5;
            arrId.add(findId);
        }

    }

    public String resetScanFile(){
        return null;
    }

    public int getCurrentId() {
        return currentId;
    }



}
