import FromFile.GetFromUserFile;

public class User {

    User(){

    }


    private String name;



    private String userInfo;
    private long logInTime;
    private long allTime;

    /**
     *
     * @param userName имя пользователя
     * @return true если пользователь существует
     *         или false если пользователь не найден
     */
    public boolean check(String userName){
        GetFromUserFile info = new GetFromUserFile();
        String toInfo;
        name = userName;
        if ((toInfo = info.findInfo(userName)) == null){
            return false;
        }
        userInfo = toInfo;

        return true;
    }



    public void allTimeUserSession(){

    }

    public void setAllUserParameters(){

    }



    public void startLogInTime(){
        logInTime = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
