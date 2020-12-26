package fromFile;

public class GetFromUserFile extends AbstractGetFrom {

    private String allUsers;
    private String filePath = "resources/UserFile.txt";

    public GetFromUserFile(){

    }

    /**
     *
     * @param userName имя пользователя которое ищем
     * @return text с информацией о конкретном пользователе
     *         или null если ничего не найдено
     */
    public String findInfo(String userName){
        allUsers = getFileToString(filePath);
        String userInfo;
        int starInfo;
        int endInfo;
        if (allUsers == null || (starInfo = allUsers.indexOf(userName)) == -1){
            return null;
        }
        endInfo = allUsers.indexOf("####", starInfo + userName.length());
        userInfo = allUsers.substring(starInfo, endInfo);
        return userInfo;
    }





    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
