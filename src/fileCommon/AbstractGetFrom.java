package fileCommon;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractGetFrom implements IAbstractGetFrom{

    @Override
    public String getFileToString(String filePath){
        createFolderAndFile(filePath);
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
                fileString.append(temporal).append("\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                direction.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileString.toString();
    }

    /**
     * if file doesn't exist create it
     * @param filePath path where the file should be located
     */
    private void createFolderAndFile(String filePath){ // пока что создаёт только файлы: StatisticText и UserFile
        Path path = Paths.get(filePath);
        if (!Files.exists(path)){
            File createFile = new File(path.getParent().toString(),
                    path.getFileName().toString());
            try {
                createFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
