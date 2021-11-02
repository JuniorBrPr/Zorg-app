import dao.PatientManagementDAO;
import pojo.Patient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

public class patientScreen {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PatientManagementDAO dao = new PatientManagementDAO();
    Patient user;
    Admin adminFunc = new Admin();
    void menu(Patient patient, Locale locale) throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
        int patientId = patient.getPatientId();
        //Calling the patient like this, updates the welcome greeting if the nickname gets changed by the user
        user = dao.getPatientByid(patientId);
        String option;
        do {
            //Patient options screen
            System.out.println(resourceBundle.getString("welcom")+" " + dao.getPatientByid(patientId).getNickname());
            System.out.println("<><><><><><><><><><><><>");
            System.out.println("A. "+resourceBundle.getString("viewOwnCreds"));
            System.out.println("B. "+resourceBundle.getString("changeNick"));
            System.out.println("C. "+resourceBundle.getString("viewMedData"));
            System.out.println("D. "+resourceBundle.getString("viewWGraph"));
            System.out.println("<><><><><><><><><><><><>");
            System.out.println("Y. "+resourceBundle.getString("changeLanguage"));
            System.out.println("Z. "+resourceBundle.getString("logout"));
            System.out.println("F. "+resourceBundle.getString("exit"));
            option = br.readLine();
            System.out.println("\n");
            switch (option.toUpperCase()) {
                case "A" ->
                        //By calling it this way, the nickname automatically updates after a change
                        adminFunc.displayPatient(dao.getPatientByid(patientId), locale);
                case "B" -> //updateNickname(dao.getPatientById(patientId));
                        adminFunc.updateNickname(dao.getPatientByid(patientId), user, locale);
                case "C" -> adminFunc.viewMedications(dao.getPatientByid(patientId), locale);
                case "D" -> {
                    adminFunc.weightGraph(patientId, locale);
                    adminFunc.viewWeightData(dao.getPatientByid(patientId), locale);
                }
                case "Y" -> {
                    LanguagePicker lanPick = new LanguagePicker();
                    lanPick.lanChanger("patient", patientId);
                }
                case "Z" -> option = "Z";
                case "F" -> {
                    System.out.println(resourceBundle.getString("gbye"));
                    System.exit(0);
                }
                default -> System.out.println(resourceBundle.getString("invalidOpt"));
            }
        } while (!option.equals("Z"));
        System.out.println("\n");
    }
}
