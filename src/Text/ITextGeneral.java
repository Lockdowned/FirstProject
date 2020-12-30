package Text;

public interface ITextGeneral {

    /**
     * gets a random text and its id
     */
    void random();

    /**
     * @return current text
     */
    String getCurrentText();

    /**
     * @return current text id
     */
    int getIdText();
}
