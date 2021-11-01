import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class LanguagePicker {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public String lanMenu() throws IOException {
        System.out.println("      Select Language    ");
        System.out.println("<><><><><><><><><><><><>");
        System.out.println("A. Dutch   || Nederlands ");
        System.out.println("B. English || Engels   ");
        System.out.println("------------------------");
        System.out.println("F. Exit");
        System.out.println("<><><><><><><><><><><><>");
        System.out.println("Enter an option");
        System.out.println("<><><><><><><><><><><><>");
        String option = br.readLine();
        return option;
    }
    public void lanSelect() throws Exception {
        Login login = new Login();
        String option = "";
        do {
            option = lanMenu();
            System.out.println("\n");
            //Start new LogIn screen
            switch (option.toUpperCase()) {
                case "A" ->  {
                    Locale locale_nl_NL = new Locale("nl", "NL");
                    login.menu(locale_nl_NL);
                }
                case "B" -> {
                    Locale locale_en_US = new Locale("en", "US");
                    login.menu(locale_en_US);
                }
                case "F" -> {
                    System.out.println("Goodbye!");

                    System.exit(0);
                }
                default -> System.out.println("Invalid Option! Please try again!!");
            }
        }while(!option.equals("F"));
    }
    public void lanChanger(String screen){
    }
}
