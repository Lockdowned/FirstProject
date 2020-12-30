package Text;

public interface IGetFromTextFile {

    /**
     * gives new text
     * @return random text or null if nothing was found
     */
    String takeRandomText();

    /**
     * again scan file
     */
    void resetScanFile();

    /**
     * @return id current text
     */
    int getCurrentId();
}
