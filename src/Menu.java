import Text.TextGeneral;
import statistic.StatisticText;
import user.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu implements IMenu{

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

    @Override
    public void logIn(){
        System.out.println("Please write Username");
        String currentName = in.next();
        if (loginUser.check(currentName)){
            System.out.println("Glad to see you again - " + loginUser.getName());

        }else {
            System.out.println("Do you want use new profile?");
            if (questionYesOrNo()){
                System.out.println("Was create new user: " + loginUser.getName());
            }else {
                logIn();
            }
        }
        loginUser.startLoginTime();
        loginUser.fillSortedMap();
    }

    @Override
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

    /**
     * searches for random text,
     * if it finds it offers to search again,
     * if it does not find it writes a message
     */
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

    /**
     * re-authorize the user,
     * then it goes to the main menu
     */
    private void reLogIn(){
        // user.User to UserSaver
        loginUser.saveBeforeLogout();
        loginUser = new User();
        logIn();
        mainMenu();
    }

    /**
     * analyzes the current text and shows the previous statistics
     */
    private void analyzeAndShow(){
        loginUser.setAllUserParameters(text.getIdText(),
                statisticText.analyzeSymbolPerMin(text.getCurrentText(), text.getIdText(), loginUser.getName()));
        statisticText.showTopRate();
    }

    /**
     * analyzes the current text and shows the previous statistics
     */
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
     * needed to get another random text
     * from a specific subgroup
     */
    private void otherText(int sectionText){
    }

    /**
     * used when the application ends.
     * causes the necessary statistics to be saved to files
     */
    private void terminate(){
        statisticText.saveBeforeClose();
        loginUser.saveBeforeLogout();
    }

    /**
     * asks yes or no
     * @return true if answer Yes(1)
     */
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

    /**
     * shows the current id and text,
     * asks if you are ready to type
     * @return true if Yes - start write
     *         false if No - other text
     */
    private boolean readyToWrite(){
        System.out.println("Text with id: " + text.getIdText());
        System.out.println(text.getCurrentText());
        System.out.println("Are you ready to write?\n" +
                "Yes - start write, No - other text");
        return questionYesOrNo();
    }
}
