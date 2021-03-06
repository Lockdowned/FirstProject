package Text;

import fileCommon.AbstractGetFrom;

import java.util.ArrayList;
import java.util.Random;

public class GetFromTextFile extends AbstractGetFrom implements IGetFromTextFile {

    private String filePath = "resources/TextFile.txt";
    private final String allTexts;
    private final ArrayList<Integer> arrId;
    private int currentId = 0;
    private final Random random;
    boolean firstAccessChecker = false;

    public GetFromTextFile(){
        allTexts = getFileToString(filePath);
        arrId = new ArrayList<>();
        fillInArrayId();
        random = new Random();
    }

    public String takeRandomText(){
        if (arrId.isEmpty()){
            System.out.println("File with text is empty, please fill him");
            return null;
        }
        String randomText;
        int randomId;
        randomId = random.nextInt(arrId.size());
        if (arrId.size() == 1 && firstAccessChecker) {
            System.out.println("There is only one text");
        }else if (arrId.size() > 1){
            while (randomId == currentId - 1){
                randomId = random.nextInt(arrId.size());
            }
        }
        firstAccessChecker = true;
        currentId = arrId.get(randomId);
        int helperStart = allTexts.indexOf(currentId + "##");
        int helperEnd = allTexts.indexOf("####", helperStart);
        randomText = allTexts.substring(helperStart +
                String.valueOf(Math.abs(currentId)).length() + 3, helperEnd - 1);
        return randomText;
    }

    /**
     * fill in List find id from file
     */
    private void fillInArrayId(){
        int startId = 0;
        int endId;
        int findId;
        String searchMark = "TextId: ";
        while ((startId = allTexts.indexOf(searchMark, startId)) != -1){
            endId = allTexts.indexOf("##", startId);
            findId = Integer.parseInt(allTexts.substring(startId + searchMark.length(), endId));
            startId += searchMark.length();
            arrId.add(findId);
        }
    }

    public void resetScanFile(){
    }

    public int getCurrentId() {
        return currentId;
    }
}
