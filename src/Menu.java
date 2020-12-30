import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {


    private User loginUser;
    private TextGeneral text;
    private StatisticText statisticText;
    private Scanner in;


    public Menu() {
        loginUser = new User();
        text = new TextGeneral();
        statisticText = new StatisticText();
        in = new Scanner(System.in);
    }


    public void logIn(){
        System.out.println("Please write Username");
        String currentName = in.next();
        if (loginUser.check(currentName)){
            System.out.println("Glad to see you again - " + loginUser.getName());

        }else {
            System.out.println("Do you want use new profile?");
            if (questionYesOrNo()){
                System.out.println("Was create new User: " + loginUser.getName());
            }else {
                logIn();
            }
        }
        loginUser.startLoginTime();
        loginUser.fillSortedMap();

    }

    public void mainMenu(){
        System.out.println("You are in the main menu\n" +
                "Write 1 to get random text, write 2 to relogin");
        int answer = 0;
        do {
            try {
                answer = in.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Can write only number");
                in = new Scanner(System.in);
            }
            switch (answer){
                case 1: randomText();
                    break;
                case 2: reLogIn();
                    break;
            }

            if (answer == 0 || answer > 2){
                System.out.println("Write 1 to get random text, write 2 to relogin");
            }
        }while (answer == 0 || answer > 2);

        endMenu();


    }



    private void randomText(){
        text.random();
        if (text.getCurrentText() == null){
            System.out.println("Text not found");
            mainMenu();
        }
        while (!readyToWrite()){
            text.random();
        }
        analyzeAndShow();
    }

    private void reLogIn(){
        // User to UserSaver
        loginUser.saveBeforeLogout();
        loginUser = new User();
        logIn();
        mainMenu();
    }

    private void analyzeAndShow(){
        loginUser.setAllUserParameters(text.getIdText(),
                statisticText.analyzeSymbolPerMin(text.getCurrentText(), text.getIdText(), loginUser.getName()));
        statisticText.showTopRate();
    }


    private void endMenu(){
        String choseMessage = "\nWrite:\n1 - to retry current text, 2 - to try other text,\n" +
                "3 - to see statistics about your user,\n" +
                "4 - to the main menu, 5 - to close app";
        int answer = 0;
        do {
            System.out.println(choseMessage);
            try {
                answer = in.nextInt();
            }catch (InputMismatchException e) {
                System.out.println("Can write only number");
                in = new Scanner(System.in);
            }
            switch (answer){
                case 1:
                    while (!readyToWrite()){
                        text.random();   // later otherText
                    }
                    analyzeAndShow();
                    endMenu();
                    break;
                case 2:
                    text.random();  // later otherText
                    while (!readyToWrite()){
                        text.random();  // later otherText
                    }
                    analyzeAndShow();
                    endMenu();
                    break;
                case 3:
                    loginUser.showStats();
                    endMenu();
                    break;
                case 4: mainMenu();
                    return;
                case 5: terminate();
                    return;
            }
        } while (answer == 0 || answer > 5);


    }



    /**
     * метод нужен для получение другого случайного текста
     * из конкретной подгруппы
     */
    private void otherText(int sectionText){

    }


    /**
     * save all aggregations information in file
     * and then close app
     */
    private void terminate(){
        statisticText.saveBeforeClose();
        loginUser.saveBeforeLogout();

    }





    private boolean questionYesOrNo(){
        System.out.println("Yes write 1, No write 2.");
        int answer = 0;
        try {
            answer = in.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Can write only number");
            in = new Scanner(System.in);
        }
        if(answer == 1){
            return true;
        }else if (answer == 2){
            return false;
        }else {
           return questionYesOrNo();
        }
    }

    private boolean readyToWrite(){
        System.out.println("Text with id: " + text.getIdText());
        System.out.println(text.getCurrentText());
        System.out.println("Are you ready to write?\n" +
                "Yes - start write, No - other text");
        return questionYesOrNo();
    }



}
