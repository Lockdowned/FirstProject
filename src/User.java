import fromFile.GetFromUserFile;

public class User {

    private String name;

    private String userInfo;
    private long loginTime;
    private long allTime;

    public User(){   // NEED PRIVATE

    }


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



    public void startLoginTime(){
        loginTime = System.currentTimeMillis();
    }

    public void setAllUserParameters(){

    }

    public void showStats(){

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
