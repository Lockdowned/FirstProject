package Text;

public class TextGeneral {

    private String currentText;
    private int idText;
    private final GetFromTextFile getFromTextFile;

    public TextGeneral(){
        getFromTextFile = new GetFromTextFile();
    }

    public void random(){
        currentText = getFromTextFile.takeRandomText();
        idText = getFromTextFile.getCurrentId();
    }

    public String getCurrentText() {
        return currentText;
    }

    public int getIdText() {
        return idText;
    }
}
