package FromFile;

public class GetFromUserFile extends AbstractGetFrom {

    public GetFromUserFile(){

    }

    private String allUsers;
    private String filePath = "C:\\Users\\olegk\\JavaProject\\BlindPrint\\resources\\UserFile.txt";

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
