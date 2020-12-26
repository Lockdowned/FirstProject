package fromFile;

import java.io.*;

public abstract class AbstractGetFrom {
    /**
     *
     * @param filePath абсолютный путь к файлу где храняться пользователи
     * @return text с информацией о всех пользователях
     *         null если мы не нашли файл или он пуст
     */
    public String getFileToString(String filePath){
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
                fileString.append(temporal);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return fileString.toString();
    }
}
