package fileCommon;

public interface IAbstractGetFrom {

    /**
     * returns all information from a specific file
     * @param filePath path where the file should be located
     * @return the entire content of a particular file
     */
    String getFileToString(String filePath);
}
