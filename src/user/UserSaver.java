package user;

import fileCommon.AbstractGetFrom;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class UserSaver extends AbstractGetFrom implements IUserSaver{

    private String allUsers;
    private String filePath = "resources/UserFile.txt";
    private final StringBuilder stringBuilder;
    private String checkAllUsers;

    public UserSaver(){
        stringBuilder = new StringBuilder();
    }

    @Override
    public String findInfo(String userName){
        String userHelper = "Name: " + userName;
        int starInfo;
        int endInfo;
        if (allUsers == null || !allUsers.equals(checkAllUsers)){
            allUsers = getFileToString(filePath);
            checkAllUsers = allUsers;
        }
        if (allUsers == null || !allUsers.contains(userHelper)){
            return null;
        }
        starInfo = allUsers.indexOf(userHelper);
        endInfo = allUsers.indexOf("####", starInfo + userName.length());
        return allUsers.substring(starInfo + userName.length(), endInfo + 4);
    }

    @Override
    public void saveToFile(String name, Map<Double, String> sortedMapResult, long timeInMillis){
        Double[] toForeEach = sortedMapResult.keySet().toArray(new Double[0]);
        String helperName = "Name: " + name;
        stringBuilder.append("Name: ").append(name).append("##\n");
        for (Double foreEach : toForeEach) {
            stringBuilder.append(sortedMapResult.get(foreEach)).append("# ").
                    append("PerMin: ").append(foreEach).append("# ");
        }
        stringBuilder.append("\n").append("ElapsedTime: ").append(timeInMillis).append("\n####");
        if (allUsers.contains(helperName)){
            allUsers = allUsers.replaceFirst(helperName + "(.*\n){3}.*",
                    stringBuilder.toString());
        }else {
            allUsers += stringBuilder.toString();
        }
        stringBuilder.setLength(0);
        Writer fileWrite;
        try {
            fileWrite = new FileWriter(filePath);
            fileWrite.write(allUsers);
            fileWrite.flush();
            fileWrite.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
