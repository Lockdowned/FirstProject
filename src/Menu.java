import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {


    private User logInUser;
    private TextGeneral text;
    private StatisticText statisticText;


    public Menu() {
        logInUser = new User();
        text = new TextGeneral();
        statisticText = new StatisticText();
    }


    public User logIn(){
        Scanner in = new Scanner(System.in);
        System.out.println("Please write Username");
        String currentName = in.next();
        if (logInUser.check(currentName)){
            System.out.println("Glad to see you again - " + logInUser.getName());

        }else {
            System.out.println("Do you want use new profile?");
            if (questionYesOrNo()){
                System.out.println("Was create new User: " + logInUser.getName());
            }else {
                logIn();
            }
        }
        logInUser.startLogInTime();
        return logInUser;
    }

    public void mainMenu(){
        System.out.println("You are in the main menu\n" +
                "Write 1 to get random text, write 2 to relogin");
        int answer;
        do {
            Scanner in = new Scanner(System.in);
            answer = 0;
            try {
                answer = in.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Can write only number");
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


    }




    private void randomText(){
        text.random();
        if (text.getCurrentText() == null){
            System.out.println("Text not found");
            mainMenu();
        }
        boolean readyToWrite = false;
        while (!readyToWrite){
            System.out.println("Text with id: " + text.getIdText());
            System.out.println(text.getCurrentText());
            System.out.println("Are you ready to write?\n" +
                    "Yes - start write, No - other text");
            readyToWrite = questionYesOrNo();
            if (!readyToWrite){
                text.random();
            }
        }
        statisticText.analyzeSymbolPerMin(text.getCurrentText(), text.getIdText());


    }

    private void reLogIn(){
        logInUser = new User();
        logIn();
        mainMenu();
    }



    private boolean questionYesOrNo(){
        System.out.println("Yes write 1, No write 2.");
        int answer = 0;
        Scanner In = new Scanner(System.in);
        try {
            answer = In.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Can write only number");
        }
        if(answer == 1){
            return true;
        }else if (answer == 2){
            return false;
        }else {
           return questionYesOrNo();
        }
    }

}
