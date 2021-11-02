import dao.PatientManagementDAO;
import pojo.Patient;

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
                case "A" -> {
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
        } while (!option.equals("F"));
    }

    public void lanChanger(String screen, int patientId) throws Exception {
            String option = lanMenu();
            System.out.println("\n");
            //Start new LogIn screen
            switch (option.toUpperCase()) {
                case "A" -> {
                    Locale locale_nl_NL = new Locale("nl", "NL");
                    if (screen.equals("admin")) {
                        Admin admin = new Admin();
                        admin.menu(locale_nl_NL);
                    }else if (screen.equals("patient")){
                        PatientManagementDAO dao = new PatientManagementDAO();
                        Patient patient = dao.getPatientByid(patientId);
                        patientScreen pS = new patientScreen();
                        pS.menu(patient, locale_nl_NL);
                    }
                }
                case "B" -> {
                    Locale locale_en_US = new Locale("en", "US");
                    if (screen.equals("admin")) {
                        Admin admin = new Admin();
                        admin.menu(locale_en_US);
                    } else if (screen.equals("patient")){
                        PatientManagementDAO dao = new PatientManagementDAO();
                        Patient patient = dao.getPatientByid(patientId);
                        patientScreen pS = new patientScreen();
                        pS.menu(patient, locale_en_US);
                    }
                }
                case "F" -> {
                    System.out.println("Goodbye!");

                    System.exit(0);
                }
                default -> System.out.println("Invalid Option! Please try again!!");
            }
    }
}
